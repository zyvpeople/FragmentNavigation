package com.develop.zuzik.fragmentnavigation.model.builder

import com.develop.zuzik.fragmentnavigation.model.Node
import com.develop.zuzik.fragmentnavigation.model.exception.ParentDoesNotHaveChildWithTagException
import com.develop.zuzik.fragmentnavigation.model.exception.ParentDoesNotHaveChildrenException
import com.develop.zuzik.fragmentnavigation.model.exception.ParentHasChildrenWithEqualTagsException
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ModelBuilderTest {
    @Test
    fun childCreatesModelWithOneNode() {
        val expectedState = child("a1", "a1Value")

        val model = ModelBuilder<String>().child("a1", "a1Value")

        assertEquals(expectedState, model.state)
    }

    @Test
    fun parentCreateModelWithTreeOfNodes() {
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


        val model = ModelBuilder<String>()
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
                    child(ModelBuilder<String>()
                            .parent("b4", "b4Value", "c1") {
                                child("c1", "c1Value")
                            })
                }

        assertEquals(expectedState, model.state)
    }

    @Test(expected = ParentDoesNotHaveChildrenException::class)
    fun parentThrowsExceptionWhenRootParentNodeDoesNotHaveChildren() {
        ModelBuilder<String>()
                .parent("a1", "a1Value", null) {
                }
    }

    @Test(expected = ParentDoesNotHaveChildrenException::class)
    fun parentThrowsExceptionWhenNotRootParentNodeDoesNotHaveChildren() {
        ModelBuilder<String>()
                .parent("a1", "a1Value", "b1") {
                    parent("b1", "b1Value", null) {
                    }
                }
    }

    @Test(expected = ParentHasChildrenWithEqualTagsException::class)
    fun parentThrowsExceptionWhenRootParentNodeHasChildrenWithEqualTag() {
        ModelBuilder<String>()
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
        ModelBuilder<String>()
                .parent("a1", "a1Value", "b1") {
                    parent("b1", "b1Value", null) {
                        child("c1", "c1Value")
                        child("c1", "c1Value")
                    }
                }
    }

    @Test(expected = ParentDoesNotHaveChildWithTagException::class)
    fun parentThrowsExceptionWhenRootParentDoesNotHaveCurrentNodeWithTag() {
        ModelBuilder<String>()
                .parent("a1", "a1Value", "b2") {
                    parent("b1", "b1Value", null) {
                        child("c1", "c1Value")
                    }
                }
    }

    @Test(expected = ParentDoesNotHaveChildWithTagException::class)
    fun parentThrowsExceptionWhenNotRootParentDoesNotHaveCurrentNodeWithTag() {
        ModelBuilder<String>()
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
        val model = ModelBuilder<String>()
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

        assertEquals(expectedNode, model.state.findNode(listOf("a1", "b1", "c1")))
    }
}