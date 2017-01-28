package com.develop.zuzik.fragmentnavigation.exception

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ParentHasChildrenWithEqualTagsException(parentTag: String) : RuntimeException("Parent with tag [$parentTag] has children with equal tags")