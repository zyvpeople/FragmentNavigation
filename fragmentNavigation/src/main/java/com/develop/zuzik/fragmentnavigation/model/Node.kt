package com.develop.zuzik.fragmentnavigation.model

import java.io.Serializable

/**
 * User: zuzik
 * Date: 1/17/17
 */
//TODO: add tests
//TODO: copy fields in constructor
//TODO: copy
class Node<Value>(tag: String,
                  value: Value,
                  currentChildTag: String?,
                  children: List<Node<Value>>) : Serializable {

    var tag = tag
        internal set
    var value = value
        internal set
    var currentChildTag = currentChildTag
        internal set
    var children = children.map { it.copy() }.toMutableList()
        internal set

    fun copy(): Node<Value> = Node(tag, value, currentChildTag, children)

    fun hasChild(childTag: String) = children.firstOrNull { it.tag == childTag } != null

    fun findNode(path: List<String>): Node<Value>? =
            when (path.size) {
                0 -> null
                else -> {
                    val isCurrentNode = path[0] == tag
                    if (isCurrentNode) {
                        when (path.size) {
                            1 -> this
                            2 -> children
                                    .firstOrNull { it.tag == path[1] }
                            else -> children
                                    .filter { it.tag == path[1] }
                                    .map { it.findNode(path.slice(1..path.size - 1)) }
                                    .firstOrNull()
                        }
                    } else {
                        null
                    }
                }
            }


    override fun equals(other: Any?) =
            if (other === this) {
                true
            } else if (other is Node<*>) {
                tag == other.tag
                        && value == other.value
                        && currentChildTag == other.currentChildTag
                        && children == other.children
            } else {
                false
            }
}