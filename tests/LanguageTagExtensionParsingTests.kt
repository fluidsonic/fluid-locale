package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LanguageTagExtensionParsingTests {

	@Test
	fun parse_singleExtension() {
		val tag = LanguageTag.parse("en-a-value")

		assertEquals(actual = tag.language, expected = "en")
		assertEquals(actual = tag.extensions, expected = listOf("a-value"))
	}

	@Test
	fun parse_multipleExtensions() {
		val tag = LanguageTag.parse("en-a-val1-b-val2")

		assertEquals(actual = tag.language, expected = "en")
		assertEquals(actual = tag.extensions.size, expected = 2)
		assertTrue(tag.extensions.contains("a-val1"))
		assertTrue(tag.extensions.contains("b-val2"))
	}

	@Test
	fun parse_extensionAndPrivateuse() {
		val tag = LanguageTag.parse("en-a-value-x-private")

		assertEquals(actual = tag.language, expected = "en")
		assertEquals(actual = tag.extensions, expected = listOf("a-value"))
		assertEquals(actual = tag.privateuse, expected = "x-private")
	}

	@Test
	fun parse_extensionWithMultipleSubtags() {
		val tag = LanguageTag.parse("en-u-co-phonebk")

		assertEquals(actual = tag.language, expected = "en")
		assertEquals(actual = tag.extensions, expected = listOf("u-co-phonebk"))
	}

	@Test
	fun parse_extensionOrderPreserved() {
		val tag = LanguageTag.parse("en-b-val2-a-val1")

		// Parser preserves the order extensions appear in the input
		assertEquals(actual = tag.extensions[0], expected = "b-val2")
		assertEquals(actual = tag.extensions[1], expected = "a-val1")
	}

	@Test
	fun parse_extensionCanonicalizesToLowercase() {
		val tag = LanguageTag.parse("en-A-VALUE")

		assertEquals(actual = tag.extensions, expected = listOf("a-value"))
	}

	@Test
	fun parse_extlangSubtag() {
		val tag = LanguageTag.forLanguage(language = "zh", extlangs = listOf("cmn"))

		assertEquals(actual = tag.language, expected = "zh")
		assertEquals(actual = tag.extlangs, expected = listOf("cmn"))
		assertEquals(actual = tag.toString(), expected = "zh-cmn")
	}

	@Test
	fun parse_extlangWithScriptAndRegion() {
		val tag = LanguageTag.forLanguage(language = "zh", extlangs = listOf("cmn"), script = "Hans", region = "CN")

		assertEquals(actual = tag.toString(), expected = "zh-cmn-Hans-CN")
	}

	@Test
	fun parseOrNull_incompleteExtension() {
		assertNull(LanguageTag.parseOrNull("en-a"))
	}

	@Test
	fun parseOrNull_incompletePrivateuse() {
		assertNull(LanguageTag.parseOrNull("en-x"))
	}

	@Test
	fun parse_numericSingleton() {
		val tag = LanguageTag.parse("en-0-value")

		assertEquals(actual = tag.extensions, expected = listOf("0-value"))
	}

	@Test
	fun toString_extensionOrder() {
		val tag = LanguageTag.parse("en-US-b-val2-a-val1-x-private")
		val str = tag.toString()

		// Parser preserves input order of extensions
		assertEquals(actual = str, expected = "en-US-b-val2-a-val1-x-private")
	}
}
