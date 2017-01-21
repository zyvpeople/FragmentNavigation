package com.develop.zuzik.fragmentnavigation.model.exception

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ParentDoesNotHaveChildWithTagException(parentTag: String, childTag: String) : RuntimeException("Parent with tag [$parentTag] does not have child with tag [$childTag]")