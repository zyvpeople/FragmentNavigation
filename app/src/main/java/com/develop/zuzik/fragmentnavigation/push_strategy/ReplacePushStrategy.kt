package com.develop.zuzik.fragmentnavigation.push_strategy

import android.support.v4.app.Fragment
import com.develop.zuzik.fragmentnavigation.ContainerFragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
class ReplacePushStrategy : PushStrategy {

    override fun pushFragment(container: ContainerFragment, child: Fragment) {
        container
                .childFragmentManager
                .beginTransaction()
                .replace(container.placeholderId(), child)
                .addToBackStack(null)
                .commit()
    }
}