package com.develop.zuzik.fragmentnavigationsample

import android.app.Application
import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.builder.ModelBuilder
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelFragmentFactory

/**
 * User: zuzik
 * Date: 1/21/17
 */
class App : Application() {

    lateinit var model: Model<ModelFragmentFactory>
        private set

    override fun onCreate() {
        super.onCreate()
        model = ModelBuilder<ModelFragmentFactory>()
                .child("a1", ModelTextFragmentFactory("a1"))
    }
}