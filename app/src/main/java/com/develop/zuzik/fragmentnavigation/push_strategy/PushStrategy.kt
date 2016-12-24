package com.develop.zuzik.fragmentnavigation.push_strategy

import android.support.v4.app.Fragment
import com.develop.zuzik.fragmentnavigation.ContainerFragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
interface PushStrategy {

    fun pushFragment(container: ContainerFragment, child: Fragment)
}