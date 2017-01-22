package com.develop.zuzik.fragmentnavigation.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
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
                Node<String>("a1", "a1Value", "b1", mutableListOf(
                        Node<String>("b1", "b1Value", "c1", mutableListOf(
                                Node<String>("c1", "c1Value", null, mutableListOf()),
                                Node<String>("c2", "c2Value", null, mutableListOf())
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
}