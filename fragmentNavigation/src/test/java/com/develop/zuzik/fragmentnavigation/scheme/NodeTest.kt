package com.develop.zuzik.fragmentnavigation.scheme

import org.junit.Assert.*
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * User: zuzik
 * Date: 1/21/17
 */
class NodeTest {

    //region serializable

    @Test
    fun nodeIsSerializable() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", "c1", mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf())
                        ))
                ))

        ByteArrayOutputStream().use { baos ->
            ObjectOutputStream(baos).use { oos ->
                oos.writeObject(node)
                ByteArrayInputStream(baos.toByteArray()).use { bais ->
                    ObjectInputStream(bais).use { ois ->
                        val readNode = ois.readObject() as Node<String>
                        assertNotSame(node, readNode)
                        assertEquals(node, readNode)
                    }
                }
            }
        }
    }

    //endregion

    //region hasChild

    @Test
    fun hasChildReturnsTrueIfNodeHasChildWithEqualTag() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf())
                ))

        assertTrue(node.hasChild("b1"))
    }

    @Test
    fun hasChildReturnsFalseIfNodeDoesNotHaveChildWithEqualTag() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf())
                ))

        assertFalse(node.hasChild("b3"))
    }

    //endregion

    //region findNode

    @Test
    fun findNodeReturnsNullIfPathIsEmpty() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf())
                        ))
                ))

        assertNull(node.findNode(emptyList()))
    }

    @Test
    fun findNodeReturnsRootNodeIfPathRefersToExistedRootNode() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf())
                        ))
                ))

        val expectedNode =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf())
                        ))
                ))

        assertEquals(expectedNode, node.findNode(listOf("a1")))
    }

    @Test
    fun findNodeReturnsNullIfPathRefersToNotExistedRootNode() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf())
                        ))
                ))

        assertNull(node.findNode(listOf("a2")))
    }

    @Test
    fun findNodeReturnsFirstLevelNestedNodeIfPathRefersToExistedFirstLevelNestedNode() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf())
                        ))
                ))

        val expectedNode =
                Node("b2", "b2Value", null, mutableListOf(
                        Node("c1", "c1Value", null, mutableListOf()),
                        Node("c2", "c2Value", null, mutableListOf())
                ))

        assertEquals(expectedNode, node.findNode(listOf("a1", "b2")))
    }

    @Test
    fun findNodeReturnsNullIfPathRefersToNotExistedFirstLevelNestedNode() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf())
                        ))
                ))

        assertNull(node.findNode(listOf("a1", "b3")))
    }

    @Test
    fun findNodeReturnsSecondLevelNestedNodeIfPathRefersToExistedSecondLevelNestedNode() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf())
                        ))
                ))

        val expectedNode =
                Node("c2", "c2Value", null, mutableListOf())

        assertEquals(expectedNode, node.findNode(listOf("a1", "b2", "c2")))
    }

    @Test
    fun findNodeReturnsNullIfPathRefersToNotExistedSecondLevelNestedNode() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf())
                        ))
                ))

        assertNull(node.findNode(listOf("a1", "b2", "c3")))
    }

    @Test
    fun findNodeReturnsThirdLevelNestedNodeIfPathRefersToExistedThirdLevelNestedNode() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf(
                                        Node("d1", "d1Value", null, mutableListOf())
                                ))
                        ))
                ))

        val expectedNode =
                Node("d1", "d1Value", null, mutableListOf())

        assertEquals(expectedNode, node.findNode(listOf("a1", "b2", "c2", "d1")))
    }

    @Test
    fun findNodeReturnsNullIfPathRefersToNotExistedThirdLevelNestedNode() {
        val node =
                Node("a1", "a1Value", "b1", mutableListOf(
                        Node("b1", "b1Value", null, mutableListOf()),
                        Node("b2", "b2Value", null, mutableListOf(
                                Node("c1", "c1Value", null, mutableListOf()),
                                Node("c2", "c2Value", null, mutableListOf(
                                        Node("d1", "d1Value", null, mutableListOf())
                                ))
                        ))
                ))

        assertNull(node.findNode(listOf("a1", "b2", "c2", "d2")))
    }

    //endregion
}