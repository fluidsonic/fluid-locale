package io.fluidsonic.locale

import kotlin.test.*


class ListExtensionTests {

	@Test
	fun testEquals_equalListsCaseInsensitive() {
		assertTrue(listOf("Hello", "World").equals(listOf("hello", "world"), ignoreCase = true))
		assertTrue(listOf("ABC", "DEF").equals(listOf("abc", "def"), ignoreCase = true))
	}

	@Test
	fun testEquals_equalListsCaseSensitive() {
		assertTrue(listOf("hello", "world").equals(listOf("hello", "world"), ignoreCase = false))
		assertFalse(listOf("Hello", "World").equals(listOf("hello", "world"), ignoreCase = false))
	}

	@Test
	fun testEquals_unequalLists() {
		assertFalse(listOf("hello", "world").equals(listOf("hello", "earth"), ignoreCase = true))
		assertFalse(listOf("hello", "world").equals(listOf("hello", "earth"), ignoreCase = false))
	}

	@Test
	fun testEquals_differentLengths() {
		assertFalse(listOf("hello").equals(listOf("hello", "world"), ignoreCase = true))
		assertFalse(listOf("hello", "world").equals(listOf("hello"), ignoreCase = true))
		assertFalse(listOf("hello").equals(emptyList(), ignoreCase = true))
		assertFalse(emptyList<String>().equals(listOf("hello"), ignoreCase = true))
	}

	@Test
	fun testEquals_emptyLists() {
		assertTrue(emptyList<String>().equals(emptyList(), ignoreCase = true))
		assertTrue(emptyList<String>().equals(emptyList(), ignoreCase = false))
	}

	@Test
	fun testEquals_singleElement() {
		assertTrue(listOf("Hello").equals(listOf("hello"), ignoreCase = true))
		assertFalse(listOf("Hello").equals(listOf("hello"), ignoreCase = false))
	}
}
