package com.develop.zuzik.fragmentnavigationsample

import android.app.Application
import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.builder.FragmentModelBuilder
import com.develop.zuzik.fragmentnavigation.model.fragment.builder.list
import com.develop.zuzik.fragmentnavigation.model.fragment.builder.pager

/**
 * User: zuzik
 * Date: 1/21/17
 */
class App : Application() {

    lateinit var model: Model<ModelFragmentFactory>
        private set

    override fun onCreate() {
        super.onCreate()
        model = FragmentModelBuilder()
                .pager("a1", "b1") {
                    pager("b1", "c1") {
                        child("c1", ModelTextFragmentFactory("c1"))
                        child("c2", ModelTextFragmentFactory("c2"))
                    }
                    list("b2", "d1") {
                        child("d1", ModelTextFragmentFactory("d1"))
                        child("d2", ModelTextFragmentFactory("d2"))
                    }
                    pager("b3", "e1") {
                        child("e1", ModelTextFragmentFactory("e1"))
                        child("e2", ModelTextFragmentFactory("e2"))
                    }
                }
    }
}