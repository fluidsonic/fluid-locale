package io.fluidsonic.locale

import kotlin.test.*


class IteratorExtensionTests {

	@Test
	fun testNextOrNull_nonEmptyIterator() {
		val iterator = listOf("a", "b", "c").iterator()
		assertEquals(expected = "a", actual = iterator.nextOrNull())
	}

	@Test
	fun testNextOrNull_emptyIterator() {
		val iterator = emptyList<String>().iterator()
		assertNull(iterator.nextOrNull())
	}

	@Test
	fun testNextOrNull_consumesElements() {
		val iterator = listOf("a", "b").iterator()
		assertEquals(expected = "a", actual = iterator.nextOrNull())
		assertEquals(expected = "b", actual = iterator.nextOrNull())
		assertNull(iterator.nextOrNull())
	}

	@Test
	fun testNextOrNull_singleElement() {
		val iterator = listOf("only").iterator()
		assertEquals(expected = "only", actual = iterator.nextOrNull())
		assertNull(iterator.nextOrNull())
	}
}
