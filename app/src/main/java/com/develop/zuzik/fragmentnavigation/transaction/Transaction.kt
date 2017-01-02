package com.develop.zuzik.fragmentnavigation.transaction

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 1/1/17
 */
interface Transaction {

    fun add(factory: FragmentFactory, tag: String): Transaction
    fun remove(tag: String): Transaction
    fun goTo(tag: String): Transaction
    fun commit()
}