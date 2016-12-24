package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy

import android.support.v4.app.Fragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
class ReplaceOldWithNewPushChildStrategy : PushChildStrategy {

    override fun pushChild(container: Fragment, placeholderId: Int, child: Fragment) {
        container
                .childFragmentManager
                .beginTransaction()
                .replace(placeholderId, child)
                .addToBackStack(null)
                .commit()
    }
}