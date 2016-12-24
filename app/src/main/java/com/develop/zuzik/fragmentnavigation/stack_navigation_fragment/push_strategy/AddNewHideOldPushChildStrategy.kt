package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy

import android.support.v4.app.Fragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
class AddNewHideOldPushChildStrategy : PushChildStrategy {

    override fun pushChild(container: Fragment, placeholderId: Int, child: Fragment) {
        val childrenCount = container.childFragmentManager.backStackEntryCount
        val topChild = if (childrenCount > 0) {
            container.childFragmentManager.fragments[childrenCount - 1]
        } else {
            null
        }
        val transaction = container
                .childFragmentManager
                .beginTransaction()
        if (topChild != null) {
            transaction.hide(topChild)
        }
        transaction
                .add(placeholderId, child)
                .addToBackStack(null)
                .commit()
    }
}