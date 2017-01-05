package com.develop.zuzik.fragmentnavigation.exception

/**
 * User: zuzik
 * Date: 1/4/17
 */
class FragmentAlreadyExistException(tag: String) : RuntimeException("Fragment with tag '$tag' already exist")