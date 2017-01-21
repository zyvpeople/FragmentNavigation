package com.develop.zuzik.fragmentnavigation.model.builder

import com.develop.zuzik.fragmentnavigation.model.Node
import com.develop.zuzik.fragmentnavigation.model.exception.ParentDoesNotHaveChildrenException
import com.develop.zuzik.fragmentnavigation.model.exception.ParentHasChildrenWithEqualTags

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
            throw ParentHasChildrenWithEqualTags(node.tag)
        }
        return node
    }

    fun child(tag: String, value: Value) {
        builders += ChildBuilder(tag, value)
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