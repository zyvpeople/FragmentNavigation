package com.develop.zuzik.fragmentnavigation.transaction

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 1/1/17
 */
class NullTransaction : Transaction {

    override fun add(factory: FragmentFactory, tag: String): Transaction {
        return this
    }

    override fun remove(tag: String): Transaction {
        return this
    }

    override fun goTo(tag: String): Transaction {
        return this
    }

    override fun commit() {
    }
}