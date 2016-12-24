package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy

import android.support.v4.app.Fragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
class AddNewPushChildStrategy : PushChildStrategy {

    override fun pushChild(container: Fragment, placeholderId: Int, child: Fragment) {
        container
                .childFragmentManager
                .beginTransaction()
                .add(placeholderId, child)
                .addToBackStack(null)
                .commit()
    }
}