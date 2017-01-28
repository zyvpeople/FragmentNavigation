package com.develop.zuzik.fragmentnavigation.exception

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ParentDoesNotHaveChildrenException(parentTag: String) : RuntimeException("Parent with tag [$parentTag] does not have children")