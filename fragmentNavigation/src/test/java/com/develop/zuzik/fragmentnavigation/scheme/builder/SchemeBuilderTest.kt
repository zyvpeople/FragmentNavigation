package com.develop.zuzik.fragmentnavigation.scheme.builder

import com.develop.zuzik.fragmentnavigation.builder.SchemeBuilder
import com.develop.zuzik.fragmentnavigation.scheme.Node
import com.develop.zuzik.fragmentnavigation.exception.ParentDoesNotHaveChildWithTagException
import com.develop.zuzik.fragmentnavigation.exception.ParentDoesNotHaveChildrenException
import com.develop.zuzik.fragmentnavigation.exception.ParentHasChildrenWithEqualTagsException
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * User: zuzik
 * Date: 1/21/17
 */
class SchemeBuilderTest {
    @Test
    fun childCreatesSchemeWithOneNode() {
        val expectedState = child("a1", "a1Value")

        val scheme = SchemeBuilder<String>().child("a1", "a1Value")

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun parentCreateSchemeWithTreeOfNodes() {
        val expectedState =
                parent("a1", "a1Value", "b1", listOf(
                        parent("b1", "b1Value", "c1", listOf(
                                parent("c1", "c1Value", "d1", listOf(
                                        child("d1", "d1Value"),
                                        child("d2", "d2Value")
                                )),
                                parent("c2", "c2Value", "d2", listOf(
                                        child("d1", "d1Value"),
                                        child("d2", "d2Value")
                                ))
                        )),
                        parent("b2", "b2Value", null, listOf(
                                parent("c1", "c1Value", "d1", listOf(
                                        child("d1", "d1Value"),
                                        child("d2", "d2Value")
                                )),
                                parent("c2", "c2Value", null, listOf(
                                        child("d1", "d1Value")
                                )),
                                child("c3", "c3Value")
                        )),
                        child("b3", "b3Value"),
                        parent("b4", "b4Value", "c1", listOf(
                                child("c1", "c1Value")
                        ))
                ))


        val scheme = SchemeBuilder<String>()
                .parent("a1", "a1Value", "b1") {
                    parent("b1", "b1Value", "c1") {
                        parent("c1", "c1Value", "d1") {
                            child("d1", "d1Value")
                            child("d2", "d2Value")
                        }
                        parent("c2", "c2Value", "d2") {
                            child("d1", "d1Value")
                            child("d2", "d2Value")
                        }
                    }
                    parent("b2", "b2Value", null) {
                        parent("c1", "c1Value", "d1") {
                            child("d1", "d1Value")
                            child("d2", "d2Value")
                        }
                        parent("c2", "c2Value", null) {
                            child("d1", "d1Value")
                        }
                        child("c3", "c3Value")
                    }
                    child("b3", "b3Value")
                    child(SchemeBuilder<String>()
                            .parent("b4", "b4Value", "c1") {
                                child("c1", "c1Value")
                            })
                }

        assertEquals(expectedState, scheme.state)
    }

    @Test(expected = ParentDoesNotHaveChildrenException::class)
    fun parentThrowsExceptionWhenRootParentNodeDoesNotHaveChildren() {
        SchemeBuilder<String>()
                .parent("a1", "a1Value", null) {
                }
    }

    @Test(expected = ParentDoesNotHaveChildrenException::class)
    fun parentThrowsExceptionWhenNotRootParentNodeDoesNotHaveChildren() {
        SchemeBuilder<String>()
                .parent("a1", "a1Value", "b1") {
                    parent("b1", "b1Value", null) {
                    }
                }
    }

    @Test(expected = ParentHasChildrenWithEqualTagsException::class)
    fun parentThrowsExceptionWhenRootParentNodeHasChildrenWithEqualTag() {
        SchemeBuilder<String>()
                .parent("a1", "a1Value", "b1") {
                    parent("b1", "b1Value", null) {
                        child("c1", "c1Value")
                    }
                    parent("b1", "b1Value", null) {
                        child("c1", "c1Value")
                    }
                }
    }

    @Test(expected = ParentHasChildrenWithEqualTagsException::class)
    fun parentThrowsExceptionWhenNotRootParentNodeHasChildrenWithEqualTag() {
        SchemeBuilder<String>()
                .parent("a1", "a1Value", "b1") {
                    parent("b1", "b1Value", null) {
                        child("c1", "c1Value")
                        child("c1", "c1Value")
                    }
                }
    }

    @Test(expected = ParentDoesNotHaveChildWithTagException::class)
    fun parentThrowsExceptionWhenRootParentDoesNotHaveCurrentNodeWithTag() {
        SchemeBuilder<String>()
                .parent("a1", "a1Value", "b2") {
                    parent("b1", "b1Value", null) {
                        child("c1", "c1Value")
                    }
                }
    }

    @Test(expected = ParentDoesNotHaveChildWithTagException::class)
    fun parentThrowsExceptionWhenNotRootParentDoesNotHaveCurrentNodeWithTag() {
        SchemeBuilder<String>()
                .parent("a1", "a1Value", "b1") {
                    parent("b1", "b1Value", "c2") {
                        child("c1", "c1Value")
                    }
                }
    }

    fun child(tag: String, value: String) = Node<String>(tag, value, null, mutableListOf())

    fun parent(tag: String, value: String, currentNodeTag: String?, children: List<Node<String>>) =
            Node<String>(tag, value, currentNodeTag, children.toMutableList())

    @Test
    fun bug() {
        val scheme = SchemeBuilder<String>()
                .parent("a1", "a1", "b1") {
                    parent("b1", "b1", "f2") {
                        child("f2", "f2")
                        parent("c1", "c1", "f1") {
                            child("f1", "f1")
                        }
                    }
                }

        val expectedNode =
                parent("c1", "c1", "f1", listOf(
                        child("f1", "f1")))

        assertEquals(expectedNode, scheme.state.findNode(listOf("a1", "b1", "c1")))
    }
}