package com.develop.zuzik.fragmentnavigation.model.builder

import com.develop.zuzik.fragmentnavigation.model.Model

/**
 * User: zuzik
 * Date: 1/21/17
 */
open class ModelBuilder<Value> {

    fun child(tag: String, value: Value): Model<Value> = Model(ChildBuilder(tag, value).build())

    fun parent(tag: String, value: Value, currentNodeTag: String?, addChildren: ParentBuilder<Value>.() -> Unit): Model<Value> {
        val builder = ParentBuilder<Value>(tag, value, currentNodeTag)
        builder.addChildren()
        return Model(builder.build())
    }
}