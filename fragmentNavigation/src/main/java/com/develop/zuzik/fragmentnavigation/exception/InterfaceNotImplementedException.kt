package com.develop.zuzik.fragmentnavigation.exception

/**
 * User: zuzik
 * Date: 1/28/17
 */
class InterfaceNotImplementedException(
        objectWithoutInterface: Any,
        interfaceForImplementation: Class<*>)
    : RuntimeException("${objectWithoutInterface.javaClass.simpleName} must implement ${interfaceForImplementation.simpleName}") {
}