package com.develop.zuzik.fragmentnavigation.push_strategy

import android.support.v4.app.Fragment
import com.develop.zuzik.fragmentnavigation.ContainerFragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
class AddHidePushStrategy : PushStrategy {

    override fun pushFragment(container: ContainerFragment, child: Fragment) {
        val fragmentsCount = container.childFragmentManager.backStackEntryCount
        val topFragment = if (fragmentsCount > 0) {
            container.childFragmentManager.fragments[fragmentsCount - 1]
        } else {
            null
        }

        val transaction = container
                .childFragmentManager
                .beginTransaction()

        if (topFragment != null) {
            transaction.hide(topFragment)
        }

        transaction
                .add(container.placeholderId(), child)
                .addToBackStack(null)
                .commit()
    }
}