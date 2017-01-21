package com.develop.zuzik.fragmentnavigationsample

import android.content.Context
import android.content.ContextWrapper

/**
 * User: zuzik
 * Date: 1/21/17
 */

val Context.app: App
    get() = ContextWrapper(this).applicationContext as App