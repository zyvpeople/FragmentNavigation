package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy

import android.support.v4.app.Fragment
import java.io.Serializable

/**
 * User: zuzik
 * Date: 12/24/16
 */
interface PushChildStrategy : Serializable {

    fun pushChild(container: Fragment, placeholderId: Int, child: Fragment)
}