package com.develop.zuzik.fragmentnavigationsample

import android.app.Application
import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.builder.FragmentModelBuilder
import com.develop.zuzik.fragmentnavigation.model.fragment.builder.list
import com.develop.zuzik.fragmentnavigation.model.fragment.builder.pager

/**
 * User: zuzik
 * Date: 1/21/17
 */
class App : Application() {

    lateinit var model: Model<FragmentFactory>
        private set

    override fun onCreate() {
        super.onCreate()
        model = FragmentModelBuilder()
                .pager("a1", "b1") {
                    pager("b1", "f2") {
//                        child("b1", TextFragmentFactory("f1"))

                        child("f2", TextFragmentFactory("f2"))
                        list("c1", "f1") {
                            child("f1", TextFragmentFactory("f1"))
                            child("f2", TextFragmentFactory("f2"))
                        }
                    }
//                    list("b2", "d1") {
//                        child("d1", TextFragmentFactory("d1"))
//                        child("d2", TextFragmentFactory("d2"))
//                    }
//                    pager("b3", "e1") {
//                        child("e1", TextFragmentFactory("e1"))
//                        child("e2", TextFragmentFactory("e2"))
//                    }
                }
    }
}