package com.develop.zuzik.fragmentnavigation.builder

import com.develop.zuzik.fragmentnavigation.scheme.Scheme

/**
 * User: zuzik
 * Date: 1/21/17
 */
open class SchemeBuilder<Value> {

    fun child(tag: String, value: Value): Scheme<Value> = Scheme(ChildBuilder(tag, value).build())

    fun parent(tag: String, value: Value, currentNodeTag: String?, addChildren: ParentBuilder<Value>.() -> Unit): Scheme<Value> {
        val builder = ParentBuilder<Value>(tag, value, currentNodeTag)
        builder.addChildren()
        return Scheme(builder.build())
    }
}