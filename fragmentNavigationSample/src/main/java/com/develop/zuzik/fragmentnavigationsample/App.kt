package com.develop.zuzik.fragmentnavigationsample

import android.app.Application
import com.develop.zuzik.fragmentnavigation.fragment.builder.FragmentSchemeBuilder
import com.develop.zuzik.fragmentnavigation.fragment.builder.list
import com.develop.zuzik.fragmentnavigation.fragment.builder.pager
import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory
import com.develop.zuzik.fragmentnavigation.scheme.Scheme

/**
 * User: zuzik
 * Date: 1/21/17
 */
class App : Application() {

    lateinit var scheme: Scheme<FragmentFactory>
        private set

    override fun onCreate() {
        super.onCreate()
        scheme = FragmentSchemeBuilder()
                .list("a1", "b1") {
                    list("b1", "f1") {
                        child("f1", TextFragmentFactory("f1"))
//                        child("f2", TextFragmentFactory("f2"))
                    }
//                    list("b2", "d1") {
//                        child("d1", TextFragmentFactory("d1"))
//                        child("d2", TextFragmentFactory("d2"))
//                    }
                    pager("b3", "e1") {
                        child("e1", TextFragmentFactory("e1"))
//                        child("e2", TextFragmentFactory("e2"))
                    }
                }
    }
}