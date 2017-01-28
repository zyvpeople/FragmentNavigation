package com.develop.zuzik.fragmentnavigation.scheme

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

/**
 * User: zuzik
 * Date: 1/17/17
 */
//TODO: add test - add node save copy
class SchemeTest {

    private val scheme = Scheme<String>(
            createParent("a1", "b1", listOf(
                    createParent("b1", null, listOf(
                            createChild("c1"))),
                    createParent("b2", "c1", listOf(
                            createChild("c1"),
                            createParent("c2", "d1", listOf(
                                    createChild("d1"),
                                    createChild("d2"))))))))
    private val listener: SchemeListener<String> = mock(SchemeListener::class.java) as SchemeListener<String>

    @Before
    fun setUp() {
        scheme.addListener(listener)
    }

    //region State

    @Test
    fun stateReturnsCopy() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        val state1 = scheme.state
        val state2 = scheme.state

        assertEquals(expectedState, state1)
        assertEquals(expectedState, state2)
        assertNotSame(state1, state2)
    }

    @Test
    fun listenerReceivesCopyOfState() {
        val expectedState =
                createParent("a1", "b2", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createParent("c2", "d1", listOf(
                                        createChild("d1"),
                                        createChild("d2")))))))

        val captorListener1 = SchemeStateCaptorSchemeListener<String>()
        val captorListener2 = SchemeStateCaptorSchemeListener<String>()

        scheme.addListener(captorListener1)
        scheme.addListener(captorListener2)

        scheme.goTo("b2", listOf("a1"))

        val state1 = captorListener1.state
        val state2 = captorListener2.state

        assertEquals(expectedState, state1)
        assertEquals(expectedState, state2)
        assertNotSame(state1, state2)
    }

    //endregion

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

        scheme.add(createChild("b3"), listOf("a1"))

        assertEquals(expectedState, scheme.state)
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

        scheme.add(createChild("b3"), listOf("a1"))
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

        scheme.add(createChild("c2"), listOf("a1", "b1"))

        assertEquals(expectedState, scheme.state)
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

        scheme.add(createChild("c2"), listOf("a1", "b1"))

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

        scheme.add(createChild("d3"), listOf("a1", "b2", "c2"))

        assertEquals(expectedState, scheme.state)
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

        scheme.add(createChild("d3"), listOf("a1", "b2", "c2"))

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

        scheme.add(createChild("c2"), listOf())

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun addDoesNotCallListenerIfPathEmpty() {
        scheme.add(createChild("c2"), listOf())

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

        scheme.add(createChild("b2"), listOf("a1"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun addDoesCallListenerIfAddNoteToRootNodeAndNodeAlreadyExists() {
        scheme.add(createChild("b2"), listOf("a1"))

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

        scheme.add(createChild("c1"), listOf("a1", "b1"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNoteToFirstLevelNestedAndNodeAlreadyExists() {
        scheme.add(createChild("c1"), listOf("a1", "b1"))

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

        scheme.add(createChild("d1"), listOf("a1", "b2", "c2"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNoteToSecondLevelNestedAndNodeAlreadyExists() {
        scheme.add(createChild("d1"), listOf("a1", "b2", "c2"))

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

        scheme.add(createChild("b3"), listOf("b1"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNodeToNotExistedRootNode() {
        scheme.add(createChild("b3"), listOf("b1"))

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

        scheme.add(createChild("c2"), listOf("a1", "b3"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNodeToNotExistedFirstLevelNestedNode() {
        scheme.add(createChild("c2"), listOf("a1", "b3"))

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

        scheme.add(createChild("d3"), listOf("a1", "b2", "c3"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNodeToNotExistedSecondLevelNestedNode() {
        scheme.add(createChild("d3"), listOf("a1", "b2", "c3"))

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

        scheme.remove("b2", listOf("a1"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeCallsListenerIfRemoveNodeFromRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1")))))

        scheme.remove("b2", listOf("a1"))

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

        scheme.remove("b2", listOf())

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeDoesNotCallListenerIfPathEmpty() {
        scheme.remove("b2", listOf())

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

        scheme.remove("b3", listOf("a1"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromRootNodeAndNodeDoesNotExist() {
        scheme.remove("b3", listOf("a1"))

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

        scheme.remove("c2", listOf("a1", "b1"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromFirstLevelNestedNodeAndNodeDoesNotExist() {
        scheme.remove("c2", listOf("a1", "b1"))

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

        scheme.remove("d3", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromSecondLevelNestedNodeAndNodeDoesNotExist() {
        scheme.remove("d3", listOf("a1", "b2", "c2"))

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

        scheme.remove("b2", listOf("a2"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromNotExistedRootNode() {
        scheme.remove("b2", listOf("a2"))

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

        scheme.remove("c1", listOf("a1", "b3"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromNotExistedFirstLevelNestedNode() {
        scheme.remove("c1", listOf("a1", "b3"))

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

        scheme.remove("11", listOf("a1", "b2", "c3"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromNotExistedSecondLevelNestedNode() {
        scheme.remove("11", listOf("a1", "b2", "c3"))

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

        scheme.remove("b1", listOf("a1"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeDoesNotSetNullToCurrentNodeTagIfRemoveNotCurrentNodeFromRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1")))))

        scheme.remove("b2", listOf("a1"))

        assertEquals(expectedState, scheme.state)
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

        scheme.remove("c1", listOf("a1", "b2"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun removeDoesNotSetNullToCurrentNodeTagIfRemoveNotCurrentNodeFromFirstLevelNestedNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1")))))

        scheme.remove("c2", listOf("a1", "b2"))

        assertEquals(expectedState, scheme.state)
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

        scheme.remove("d1", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, scheme.state)
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

        scheme.remove("d2", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, scheme.state)
    }

    //endregion

    //region GoTo

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

        scheme.goTo("b2", listOf("a1"))

        assertEquals(expectedState, scheme.state)
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

        scheme.goTo("b2", listOf("a1"))

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

        scheme.goTo("c1", listOf("a1", "b1"))

        assertEquals(expectedState, scheme.state)
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

        scheme.goTo("c1", listOf("a1", "b1"))

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

        scheme.goTo("d2", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, scheme.state)
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

        scheme.goTo("d2", listOf("a1", "b2", "c2"))

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

        scheme.goTo("c1", listOf())

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun goToDoesNotCallListenerWhenPathIsEmpty() {
        scheme.goTo("c1", listOf())

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

        scheme.goTo("b3", listOf("a1"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToRootNodeIfNodeDoesNotExist() {
        scheme.goTo("b3", listOf("a1"))

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

        scheme.goTo("c2", listOf("a1", "b1"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToFirstLevelNestedNodeIfNodeDoesNotExist() {
        scheme.goTo("c2", listOf("a1", "b1"))

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

        scheme.goTo("d3", listOf("a1", "b2", "c2"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToSecondLevelNestedNodeIfNodeDoesNotExist() {
        scheme.goTo("d3", listOf("a1", "b2", "c2"))

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

        scheme.goTo("b2", listOf("a2"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToNotExistedRootNode() {
        scheme.goTo("b2", listOf("a2"))

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

        scheme.goTo("c2", listOf("a1", "b3"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToNotExistedFirstLevelNestedNode() {
        scheme.goTo("c2", listOf("a1", "b3"))

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

        scheme.remove("d2", listOf("a1", "b2", "c3"))

        assertEquals(expectedState, scheme.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToNotExistedSecondLevelNestedNode() {
        scheme.remove("d2", listOf("a1", "b2", "c3"))

        verifyZeroInteractions(listener)
    }

    //endregion

    //region Add Remove Listener

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
        val listener2: SchemeListener<String> = mock(SchemeListener::class.java) as SchemeListener<String>

        scheme.addListener(listener)
        scheme.addListener(listener2)

        scheme.goTo("b2", listOf("a1"))

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

        scheme.addListener(listener)
        scheme.addListener(listener)

        scheme.goTo("b2", listOf("a1"))

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
        val listener2: SchemeListener<String> = mock(SchemeListener::class.java) as SchemeListener<String>

        scheme.addListener(listener)
        scheme.addListener(listener2)

        scheme.removeListener(listener2)

        scheme.goTo("b2", listOf("a1"))

        verify(listener, times(1)).invoke(expectedState)
        verifyZeroInteractions(listener2)
    }

    //endregion

    private fun createParent(tag: String, currentChildTag: String?, children: List<Node<String>>) =
            Node(tag, "", currentChildTag, children.toMutableList())

    private fun createChild(tag: String) = createParent(tag, null, listOf())

    private class SchemeStateCaptorSchemeListener<Value>(var state: Node<Value>? = null) : SchemeListener<Value> {
        override fun invoke(p1: Node<Value>) {
            state = p1
        }
    }
}