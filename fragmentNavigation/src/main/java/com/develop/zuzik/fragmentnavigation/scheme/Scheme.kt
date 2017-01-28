package com.develop.zuzik.fragmentnavigation.scheme

import java.io.Serializable
import java.util.concurrent.CopyOnWriteArraySet

/**
 * User: zuzik
 * Date: 1/17/17
 */
//TODO: add test modelIsSerializable
class Scheme<Value>(state: Node<Value>) : Serializable {

    private var _state = state.copy()
    val state: Node<Value>
        get() = _state.copy()

    private val listeners = CopyOnWriteArraySet<SchemeListener<Value>>()

    fun addListener(listener: SchemeListener<Value>) {
        listeners.add(listener)
    }

    fun removeListener(listener: SchemeListener<Value>) {
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
                    if (currentChildTag == tag) {
                        currentChildTag = null
                    }
                    notifyStateChanged()
                }
    }

    fun goTo(tag: String?, path: List<String>) {
        _state
                .findNode(path)
                ?.let { if (tag == null || it.hasChild(tag)) it else null }
                ?.let {
                    it.currentChildTag = tag
                    notifyStateChanged()
                }
    }

    private fun notifyStateChanged() {
        listeners.forEach { it(state) }
    }
}