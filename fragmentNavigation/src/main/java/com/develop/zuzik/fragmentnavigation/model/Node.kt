package com.develop.zuzik.fragmentnavigation.model

import java.io.Serializable

/**
 * User: zuzik
 * Date: 1/17/17
 */
data class Node<Value>(val tag: String,
                       val value: Value?,
                       val currentChildTag: String?,
                       val children: MutableList<Node<Value>>) : Serializable {

    fun hasChild(childTag: String) = children.firstOrNull { it.tag == childTag } != null

    fun findNode(path: List<String>): Node<Value>? = findNode(this, path)

    private fun findNode(node: Node<Value>, path: List<String>): Node<Value>? =
            when (path.size) {
                0 -> null
                1 -> node
                        .children
                        .firstOrNull { it.tag == path[0] }
                else -> node
                        .children
                        .filter { it.tag == path[0] }
                        .map { findNode(it, path.slice(1..path.size - 1)) }
                        .firstOrNull()
            }
}