package com.develop.zuzik.fragmentnavigation.builder

import com.develop.zuzik.fragmentnavigation.scheme.Scheme
import com.develop.zuzik.fragmentnavigation.scheme.Node
import com.develop.zuzik.fragmentnavigation.exception.ParentDoesNotHaveChildWithTagException
import com.develop.zuzik.fragmentnavigation.exception.ParentDoesNotHaveChildrenException
import com.develop.zuzik.fragmentnavigation.exception.ParentHasChildrenWithEqualTagsException

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ParentBuilder<Value> internal constructor(private val tag: String,
                                                private val value: Value,
                                                private val currentNodeTag: String?) : Builder<Value> {

    private val builders = mutableListOf<Builder<Value>>()

    override fun build(): Node<Value> {
        val node = Node(tag, value, currentNodeTag, builders.map { it.build() }.toMutableList())
        if (node.children.isEmpty()) {
            throw ParentDoesNotHaveChildrenException(node.tag)
        }
        if (node.hasChildrenWithSameTag()) {
            throw ParentHasChildrenWithEqualTagsException(node.tag)
        }
        if (currentNodeTag != null && node.children.firstOrNull { it.tag == currentNodeTag } == null) {
            throw ParentDoesNotHaveChildWithTagException(node.tag, currentNodeTag)
        }
        return node
    }

    fun child(tag: String, value: Value) {
        builders += ChildBuilder(tag, value)
    }

    fun child(scheme: Scheme<Value>) {
        builders += ChildSchemeBuilder(scheme)
    }

    fun parent(tag: String, value: Value, currentNodeTag: String?, addChildren: ParentBuilder<Value>.() -> Unit) {
        val builder = ParentBuilder<Value>(tag, value, currentNodeTag)
        builder.addChildren()
        builders += builder
    }

    private fun Node<Value>.hasChildrenWithSameTag(): Boolean =
            children.size != children
                    .map { it.tag }
                    .toSet()
                    .size
}