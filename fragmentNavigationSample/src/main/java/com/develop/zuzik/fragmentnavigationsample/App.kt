package com.develop.zuzik.fragmentnavigationsample

import android.app.Application
import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.builder.ModelBuilder
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelListNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelPagerNavigationFragmentFactory

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
                .parent("a1", ModelPagerNavigationFragmentFactory(), "b1") {
                    child("b1", ModelTextFragmentFactory("b1"))
                    child("b2", ModelTextFragmentFactory("b2"))
                }
    }
}