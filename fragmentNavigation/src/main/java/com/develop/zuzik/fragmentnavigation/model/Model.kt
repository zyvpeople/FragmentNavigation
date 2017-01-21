package com.develop.zuzik.fragmentnavigation.model

import java.io.Serializable

/**
 * User: zuzik
 * Date: 1/17/17
 */
//TODO: add test modelIsSerializable
class Model<Value>(state: Node<Value>) : Serializable {

    private var _state = Node<Value>("", null, null, mutableListOf(state))
    val state: Node<Value>
        get() = _state.children.first().copy()

    private val listeners = mutableSetOf<ModelListener<Value>>()

    fun addListener(listener: ModelListener<Value>) {
        listeners.add(listener)
    }

    fun removeListener(listener: ModelListener<Value>) {
        listeners.remove(listener)
    }

    fun add(node: Node<Value>, path: List<String>) {
        _state
                .findNode(path)
                ?.let { if (!it.hasChild(node.tag)) it else null }
                ?.apply {
                    children.add(node.copy())
                    notifyStateChanged()
                }
    }

    fun remove(tag: String, path: List<String>) {
        _state
                .findNode(path)
                ?.let { if (it.hasChild(tag)) it else null }
                ?.apply {
                    children.removeAll { it.tag == tag }
                    notifyStateChanged()
                }
    }

    fun goTo(tag: String?, path: List<String>) {
        _state
                .findNode(path)
                ?.let { if (tag == null || it.hasChild(tag)) it else null }
                ?.let { child ->
                    val parent = if (path.size == 1) _state else _state.findNode(path.slice(0..path.size - 2))
                    if (parent != null) {
                        val indexOfChild = parent.children.indexOfFirst { it.tag == child.tag }
                        if (indexOfChild != -1) {
                            parent.children[indexOfChild] = child.copy(currentChildTag = tag)
                            notifyStateChanged()
                        }
                    }
                }
    }

    private fun notifyStateChanged() {
        listeners.forEach { it(state) }
    }
}