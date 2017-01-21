package com.develop.zuzik.fragmentnavigation.model.exception

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ParentDoesNotHaveChildrenException(nodeTag: String) : RuntimeException("Parent with tag [$nodeTag] does not have children")