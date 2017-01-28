package com.develop.zuzik.fragmentnavigation.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * User: zuzik
 * Date: 1/17/17
 */
class ModelTest {

    private val model = Model<String>(
            createParent("a1", "b1", listOf(
                    createParent("b1", null, listOf(
                            createChild("c1"))),
                    createParent("b2", "c1", listOf(
                            createChild("c1"),
                            createParent("c2", "d1", listOf(
                                    createChild("d1"),
                                    createChild("d2"))))))))
    private val listener: ModelListener<String> = mock(ModelListener::class.java) as ModelListener<String>

    @Before
    fun setUp() {
        model.addListener(listener)
    }

    //region Add

    @Test
    fun addAddsNodeToRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2"))))),
                        createChild("b3")))

        model.add(createChild("b3"), listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addCallsListenerIfAddsNodeToRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2"))))),
                        createChild("b3")))

        model.add(createChild("b3"), listOf("a1"))
        verify(listener, only()).invoke(expectedState)
    }

    @Test
    fun addAddsNodeToFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"),
                                createChild("c2"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.add(createChild("c2"), listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addCallsListenerIfAddsNodeToFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"),
                                createChild("c2"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.add(createChild("c2"), listOf("a1", "b1"))

        verify(listener, only()).invoke(expectedState)
    }

    @Test
    fun addAddsNodeToSecondLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2"),
                                        createChild("d3")))))))

        model.add(createChild("d3"), listOf("a1", "b2", "c2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addCallsListenerIfAddsNodeToSecondLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2"),
                                        createChild("d3")))))))

        model.add(createChild("d3"), listOf("a1", "b2", "c2"))

        verify(listener, only()).invoke(expectedState)
    }

    @Test
    fun addDoesNotAddNodeIfPathEmpty() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.add(createChild("c2"), listOf())

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesNotCallListenerIfPathEmpty() {
        model.add(createChild("c2"), listOf())

        verifyZeroInteractions(listener)
    }

    @Test
    fun addDoesNotAddNodeToRootNodeIfNodeAlreadyExists() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.add(createChild("b2"), listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesCallListenerIfAddNoteToRootNodeAndNodeAlreadyExists() {
        model.add(createChild("b2"), listOf("a1"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun addDoesNotAddNodeToFirstLevelNestedNodeIfNodeAlreadyExists() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.add(createChild("c1"), listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNoteToFirstLevelNestedAndNodeAlreadyExists() {
        model.add(createChild("c1"), listOf("a1", "b1"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun addDoesNotAddNodeToSecondLevelNestedNodeIfNodeAlreadyExists() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.add(createChild("d1"), listOf("a1", "b2", "c2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNoteToSecondLevelNestedAndNodeAlreadyExists() {
        model.add(createChild("d1"), listOf("a1", "b2", "c2"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun addDoesNotAddNodeToNotExistedRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.add(createChild("b3"), listOf("b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNodeToNotExistedRootNode() {
        model.add(createChild("b3"), listOf("b1"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun addDoesNotAddNodeToNotExistedFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.add(createChild("c2"), listOf("a1", "b3"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNodeToNotExistedFirstLevelNestedNode() {
        model.add(createChild("c2"), listOf("a1", "b3"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun addDoesNotAddNodeToNotExistedSecondLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.add(createChild("d3"), listOf("a1", "b2", "c3"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNodeToNotExistedSecondLevelNestedNode() {
        model.add(createChild("d3"), listOf("a1", "b2", "c3"))

        verifyZeroInteractions(listener)
    }

    //endregion

    //region Remove

    @Test
    fun removeRemovesNodeFromRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1")))))

        model.remove("b2", listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeCallsListenerIfRemoveNodeFromRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1")))))

        model.remove("b2", listOf("a1"))

        verify(listener, only()).invoke(expectedState)
    }

    @Test
    fun removeDoesNotRemoveNodeIfPathEmpty() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("b2", listOf())

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfPathEmpty() {
        model.remove("b2", listOf())

        verifyZeroInteractions(listener)
    }

    @Test
    fun removeDoesNotRemoveNodeFromRootNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("b3", listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromRootNodeAndNodeDoesNotExist() {
        model.remove("b3", listOf("a1"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun removeDoesNotRemoveNodeFromFirstLevelNestedNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("c2", listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromFirstLevelNestedNodeAndNodeDoesNotExist() {
        model.remove("c2", listOf("a1", "b1"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun removeDoesNotRemoveNodeFromSecondLevelNestedNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("d3", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromSecondLevelNestedNodeAndNodeDoesNotExist() {
        model.remove("d3", listOf("a1", "b2", "c2"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun removeDoesNotRemoveNodeFromNotExistedRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("b2", listOf("a2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromNotExistedRootNode() {
        model.remove("b2", listOf("a2"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun removeDoesNotRemoveNodeFromNotExistedFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("c1", listOf("a1", "b3"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromNotExistedFirstLevelNestedNode() {
        model.remove("c1", listOf("a1", "b3"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun removeDoesNotRemoveNodeFromNotExistedSecondLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("11", listOf("a1", "b2", "c3"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromNotExistedSecondLevelNestedNode() {
        model.remove("11", listOf("a1", "b2", "c3"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun removeSetsNullToCurrentNodeTagIfRemoveCurrentNodeFromRootNode() {
        val expectedState =
                createParent("a1", null, listOf(
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("b1", listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotSetNullToCurrentNodeTagIfRemoveNotCurrentNodeFromRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1")))))

        model.remove("b2", listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeSetsNullToCurrentNodeTagIfRemoveCurrentNodeFromFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", null, listOf(
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("c1", listOf("a1", "b2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotSetNullToCurrentNodeTagIfRemoveNotCurrentNodeFromFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1")))))

        model.remove("c2", listOf("a1", "b2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeSetsNullToCurrentNodeTagIfRemoveCurrentNodeFromSecondLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", null, listOf(
                                        createChild("d2")))))))

        model.remove("d1", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotSetNullToCurrentNodeTagIfRemoveNotCurrentNodeFromSecondLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1")))))))

        model.remove("d2", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, model.state)
    }

    //endregion

    //region GoTO

    @Test
    fun goToSetsCurrentNodeTagToRootNode() {
        val expectedState =
                createParent("a1", "b2", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("b2", listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToCallsListenerIfSetCurrentNodeTagToRootNode() {
        val expectedState =
                createParent("a1", "b2", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("b2", listOf("a1"))

        verify(listener, only()).invoke(expectedState)
    }

    @Test
    fun goToSetsCurrentNodeTagToFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", "c1", listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("c1", listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToCallsListenerIfSetCurrentNodeTagToFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", "c1", listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("c1", listOf("a1", "b1"))

        verify(listener, only()).invoke(expectedState)
    }

    @Test
    fun goToSetsCurrentNodeTagToSecondLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d2", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("d2", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToCallsListenerIfSetCurrentNodeTagToSecondLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d2", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("d2", listOf("a1", "b2", "c2"))

        verify(listener, only()).invoke(expectedState)
    }

    @Test
    fun goToDoesNotSetCurrentNodeTagWhenPathIsEmpty() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("c1", listOf())

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerWhenPathIsEmpty() {
        model.goTo("c1", listOf())

        verifyZeroInteractions(listener)
    }

    @Test
    fun goToDoesNotSetCurrentNodeToRootNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("b3", listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToRootNodeIfNodeDoesNotExist() {
        model.goTo("b3", listOf("a1"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun goToDoesNotSetCurrentNodeToFirstLevelNestedNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("c2", listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToFirstLevelNestedNodeIfNodeDoesNotExist() {
        model.goTo("c2", listOf("a1", "b1"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun goToDoesNotSetCurrentNodeToSecondLevelNestedNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("d3", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToSecondLevelNestedNodeIfNodeDoesNotExist() {
        model.goTo("d3", listOf("a1", "b2", "c2"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun goToDoesNotSetCurrentNodeIfSetCurrentNodeToNotExistedRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("b2", listOf("a2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToNotExistedRootNode() {
        model.goTo("b2", listOf("a2"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun goToDoesNotSetCurrentNodeIfSetCurrentNodeToNotExistedFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.goTo("c2", listOf("a1", "b3"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToNotExistedFirstLevelNestedNode() {
        model.goTo("c2", listOf("a1", "b3"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun goToDoesNotSetCurrentNodeIfSetCurrentNodeToNotExistedSecondLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.remove("d2", listOf("a1", "b2", "c3"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToNotExistedSecondLevelNestedNode() {
        model.remove("d2", listOf("a1", "b2", "c3"))

        verifyZeroInteractions(listener)
    }

    //endregion

    //region AddRemoveListener

    @Test
    fun addListenerAddsMoreThanOneListener() {
        val expectedState =
                createParent("a1", "b2", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))
        val listener2: ModelListener<String> = mock(ModelListener::class.java) as ModelListener<String>

        model.addListener(listener)
        model.addListener(listener2)

        model.goTo("b2", listOf("a1"))

        verify(listener, times(1)).invoke(expectedState)
        verify(listener2, times(1)).invoke(expectedState)
    }

    @Test
    fun addListenerAddsSameListenerOnlyOnce() {
        val expectedState =
                createParent("a1", "b2", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        model.addListener(listener)
        model.addListener(listener)

        model.goTo("b2", listOf("a1"))

        verify(listener, times(1)).invoke(expectedState)
    }

    @Test
    fun removeListenerRemoveOnlySameListener() {
        val expectedState =
                createParent("a1", "b2", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))
        val listener2: ModelListener<String> = mock(ModelListener::class.java) as ModelListener<String>

        model.addListener(listener)
        model.addListener(listener2)

        model.removeListener(listener2)

        model.goTo("b2", listOf("a1"))

        verify(listener, times(1)).invoke(expectedState)
        verifyZeroInteractions(listener2)
    }

    //endregion

    private fun createParent(tag: String, currentChildTag: String?, children: List<Node<String>>) =
            Node(tag, "", currentChildTag, children.toMutableList())

    private fun createChild(tag: String) = createParent(tag, null, listOf())
}