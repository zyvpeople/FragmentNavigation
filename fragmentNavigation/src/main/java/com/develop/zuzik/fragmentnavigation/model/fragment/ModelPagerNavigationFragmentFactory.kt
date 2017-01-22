package com.develop.zuzik.fragmentnavigation.model.fragment

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ModelPagerNavigationFragmentFactory : ModelFragmentFactory {
    override fun create(path: List<String>) = ModelPagerNavigationFragment.create(path)
}