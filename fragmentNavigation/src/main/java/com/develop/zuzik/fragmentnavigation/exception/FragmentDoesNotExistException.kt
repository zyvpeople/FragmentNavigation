package com.develop.zuzik.fragmentnavigation.exception

/**
 * User: zuzik
 * Date: 1/4/17
 */
class FragmentDoesNotExistException(tag: String) : RuntimeException("Fragment with tag '$tag' does not exit")