package com.develop.zuzik.fragmentnavigation.model

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
    fun findNodeReturnsNotRootNodeIfPathRefersToExistedNotRootNode() {
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
    fun findNodeReturnsNullIfPathRefersToNotExistedNotRootNode() {
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
}