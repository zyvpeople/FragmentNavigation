package com.develop.zuzik.fragmentnavigation.model.exception

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ParentHasChildrenWithEqualTags(nodeTag: String) : RuntimeException("Parent with tag [$nodeTag] has children with equal tags")