package com.develop.zuzik.fragmentnavigation.transaction

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.transaction.action.AddTransactionAction
import com.develop.zuzik.fragmentnavigation.transaction.action.GoToTransactionAction
import com.develop.zuzik.fragmentnavigation.transaction.action.RemoveTransactionAction
import com.develop.zuzik.fragmentnavigation.transaction.action.TransactionAction

/**
 * User: zuzik
 * Date: 1/1/17
 */
class NavigationFragmentTransaction(private val onCommit: (List<TransactionAction>) -> Unit) : Transaction {

    private val actions = mutableListOf<TransactionAction>()

    override fun add(factory: FragmentFactory, tag: String): Transaction {
        actions += AddTransactionAction(factory, tag)
        return this
    }

    override fun remove(tag: String): Transaction {
        actions += RemoveTransactionAction(tag)
        return this
    }

    override fun goTo(tag: String): Transaction {
        actions += GoToTransactionAction(tag)
        return this
    }

    override fun commit() {
        onCommit(actions)
    }
}