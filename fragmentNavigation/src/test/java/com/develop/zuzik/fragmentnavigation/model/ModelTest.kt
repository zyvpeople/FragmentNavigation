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
                            createChild("c2"))))))
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
                                createChild("c2"))),
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
                                createChild("c2"))),
                        createChild("b3")))

        model.add(createChild("b3"), listOf("a1"))
        verify(listener, only()).invoke(expectedState)
    }

    @Test
    fun addAddsNodeToNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"),
                                createChild("c2"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.add(createChild("c2"), listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addCallsListenerIfAddsNodeToNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"),
                                createChild("c2"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.add(createChild("c2"), listOf("a1", "b1"))

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
                                createChild("c2")))))

        model.add(createChild("c2"), listOf())

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesNotCallListenerIfPathEmpty() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1")))))

        model.add(createChild("c2"), listOf())

        verify(listener, never()).invoke(expectedState)
    }

    @Test
    fun addDoesNotAddNodeToRootNodeIfNodeAlreadyExists() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.add(createChild("b2"), listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesCallListenerIfAddNoteToRootNodeAndNodeAlreadyExists() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1")))))

        model.add(createChild("b2"), listOf("a1"))

        verify(listener, never()).invoke(expectedState)
    }

    @Test
    fun addDoesNotAddNodeToNotRootNodeIfNodeAlreadyExists() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.add(createChild("c1"), listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesCallListenerIfAddNoteToNotRootNodeAndNodeAlreadyExists() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1")))))

        model.add(createChild("c1"), listOf("a1", "b1"))

        verify(listener, never()).invoke(expectedState)
    }

    @Test
    fun addDoesNotAddNodeToNotExistedRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.add(createChild("b3"), listOf("b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNodeToNotExistedRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1")))))

        model.add(createChild("b3"), listOf("b1"))

        verify(listener, never()).invoke(expectedState)
    }

    @Test
    fun addDoesNotAddNodeToNotExistedNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.add(createChild("c2"), listOf("a1", "b3"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun addDoesNotCallListenerIfAddNodeToNotExistedNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1")))))

        model.add(createChild("c2"), listOf("a1", "b3"))

        verify(listener, never()).invoke(expectedState)
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
                                createChild("c2")))))

        model.remove("b2", listOf())

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfPathEmpty() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1")))))

        model.remove("b2", listOf())

        verify(listener, never()).invoke(expectedState)
    }

    @Test
    fun removeDoesNotRemoveNodeFromRootNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.remove("b3", listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromRootNodeAndNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createChild("b2")))

        model.remove("b3", listOf("a1"))

        verify(listener, never()).invoke(expectedState)
    }

    @Test
    fun removeDoesNotRemoveNodeFromNotRootNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.remove("c2", listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromNotRootNodeAndNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createChild("b2")))

        model.remove("c2", listOf("a1", "b1"))

        verify(listener, never()).invoke(expectedState)
    }

    @Test
    fun removeDoesNotRemoveNodeFromNotExistedRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.remove("b2", listOf("a2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromNotExistedRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createChild("b2")))

        model.remove("b2", listOf("a2"))

        verify(listener, never()).invoke(expectedState)
    }

    @Test
    fun removeDoesNotRemoveNodeFromNotExistedNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.remove("c1", listOf("a1", "b3"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotCallListenerIfRemoveNodeFromNotExistedNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createChild("b2")))

        model.remove("c1", listOf("a1", "b3"))

        verify(listener, never()).invoke(expectedState)
    }

    @Test
    fun removeSetsNullToCurrentNodeTagIfRemoveCurrentNodeFromRootNode() {
        val expectedState =
                createParent("a1", null, listOf(
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

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
    fun removeSetsNullToCurrentNodeTagIfRemoveCurrentNodeFromNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", null, listOf(
                                createChild("c2")))))

        model.remove("c1", listOf("a1", "b2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun removeDoesNotSetNullToCurrentNodeTagIfRemoveNotCurrentNodeFromNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1")))))

        model.remove("c2", listOf("a1", "b2"))

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
                                createChild("c2")))))

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
                                createChild("c2")))))

        model.goTo("b2", listOf("a1"))

        verify(listener, only()).invoke(expectedState)
    }

    @Test
    fun goToSetsCurrentNodeTagToNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", "c1", listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.goTo("c1", listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToCallsListenerIfSetCurrentNodeTagToNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", "c1", listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.goTo("c1", listOf("a1", "b1"))

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
                                createChild("c2")))))

        model.goTo("c1", listOf())

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerWhenPathIsEmpty() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createChild("b2")))

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
                                createChild("c2")))))

        model.goTo("b3", listOf("a1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToRootNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createChild("b2")))

        model.goTo("b3", listOf("a1"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun goToDoesNotSetCurrentNodeToNotRootNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.goTo("c2", listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToNotRootNodeIfNodeDoesNotExist() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createChild("b2")))

        model.goTo("c2", listOf("a1", "b1"))

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
                                createChild("c2")))))

        model.remove("b2", listOf("a2"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToNotExistedRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createChild("b2")))

        model.remove("b2", listOf("a2"))

        verifyZeroInteractions(listener)
    }

    @Test
    fun goToDoesNotSetCurrentNodeIfSetCurrentNodeToNotExistedNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createParent("b2", "c1", listOf(
                                createChild("c1"),
                                createChild("c2")))))

        model.remove("c2", listOf("a1", "b1"))

        assertEquals(expectedState, model.state)
    }

    @Test
    fun goToDoesNotCallListenerIfSetCurrentNodeToNotExistedNotRootNode() {
        val expectedState =
                createParent("a1", "b1", listOf(
                        createParent("b1", null, listOf(
                                createChild("c1"))),
                        createChild("b2")))

        model.remove("c2", listOf("a1", "b1"))

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
                                createChild("c2")))))
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
                                createChild("c2")))))

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
                                createChild("c2")))))
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