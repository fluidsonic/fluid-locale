package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LanguageTagTests {

	@Test
	fun testEqualsAndHashCode() {
		assertEquals(expected = LanguageTag.parse("en-US"), actual = LanguageTag.parse("en-US"))
		assertEquals(expected = LanguageTag.parse("en-US").hashCode(), actual = LanguageTag.parse("en-US").hashCode())

		assertEquals(expected = LanguageTag.parse("en-US-variant"), actual = LanguageTag.parse("en-US-VARIANT"))
		assertEquals(expected = LanguageTag.parse("en-US-variant").hashCode(), actual = LanguageTag.parse("en-US-VARIANT").hashCode())
	}


	@Test
	fun testForLanguage() {
		assertEquals(expected = "en", actual = LanguageTag.forLanguage(language = "EN").toString())
		assertEquals(expected = "en-US", actual = LanguageTag.forLanguage(language = "EN", region = "us").toString())
		assertEquals(expected = "zh-Hant-CN", actual = LanguageTag.forLanguage(language = "ZH", script = "hANT", region = "cn").toString())
		assertEquals(
			expected = "zh-Hant-CN-VARIANT1-VARIANT2",
			actual = LanguageTag.forLanguage(language = "ZH", script = "hANT", region = "cn", variants = listOf("VARIANT1", "VARIANT2")).toString()
		)
	}


	@Test
	fun testForLanguageOrFail() {
		assertEquals(
			expected = "Invalid language: ",
			actual = assertFails { LanguageTag.forLanguage(language = "") }.message
		)
		assertEquals(
			expected = "Invalid language: x",
			actual = assertFails { LanguageTag.forLanguage(language = "x") }.message
		)
		assertEquals(
			expected = "Invalid region: x",
			actual = assertFails { LanguageTag.forLanguage(language = "en", region = "x") }.message
		)
		assertEquals(
			expected = "Invalid script: x",
			actual = assertFails { LanguageTag.forLanguage(language = "en", script = "x", region = "US") }.message
		)
		assertEquals(
			expected = "Invalid variant: var",
			actual = assertFails { LanguageTag.forLanguage(language = "zh", script = "Hant", region = "CN", variants = listOf("var")) }.message
		)
	}


	@Test
	fun testForLanguageOrNull() {
		assertNull(LanguageTag.forLanguageOrNull(language = ""))
		assertNull(LanguageTag.forLanguageOrNull(language = "x"))
		assertNull(LanguageTag.forLanguageOrNull(language = "en", region = "x"))
		assertNull(LanguageTag.forLanguageOrNull(language = "en", script = "x", region = "US"))
		assertNull(LanguageTag.forLanguageOrNull(language = "zh", script = "Hant", region = "CN", variants = listOf("var")))
	}


	@Test
	fun testForPrivateUse() {
		assertEquals(expected = "x-hello", actual = LanguageTag.forPrivateUse(privateuse = "x-hello").toString())

		assertEquals(
			expected = "Invalid privateuse: a-b-c",
			actual = assertFails { LanguageTag.forPrivateUse(privateuse = "a-b-c") }.message
		)
	}


	@Test
	fun testForPrivateUseOrNull() {
		assertNull(LanguageTag.forPrivateUseOrNull(privateuse = "a-b-c"))
	}


	@Test
	fun testParseAndToString() {
		assertEquals(expected = "en-US", actual = LanguageTag.parse("en-US").toString())
		assertEquals(expected = "en-US", actual = LanguageTag.parse("en-us").toString())

		assertEquals(expected = "zh-Hans-CN-VARIANT-1VAR-0-ext1-w-ext2-x-very-private", actual = LanguageTag.parse("ZH-HANS-CN-VARIANT-1VAR-0-EXT1-W-EXT2-X-VERY-PRIVATE").toString())
		assertEquals(expected = "zh-Hans-CN-variant-1var-0-ext1-w-ext2-x-very-private", actual = LanguageTag.parse("zh-hans-cn-variant-1var-0-ext1-w-ext2-x-very-private").toString())
	}


	@Test
	fun testParseOrFail() {
		assertEquals(
			expected = "Ill-formed BCP 47 language tag: ",
			actual = assertFails { LanguageTag.parse("") }.message
		)
		assertEquals(
			expected = "Ill-formed BCP 47 language tag: longlonglong",
			actual = assertFails { LanguageTag.parse("longlonglong") }.message
		)
		assertEquals(
			expected = "Ill-formed BCP 47 language tag: -US",
			actual = assertFails { LanguageTag.parse("-US") }.message
		)
		assertEquals(
			expected = "Ill-formed BCP 47 language tag: en-US-0",
			actual = assertFails { LanguageTag.parse("en-US-0") }.message
		)
		assertEquals(
			expected = "Ill-formed BCP 47 language tag: en-US-x",
			actual = assertFails { LanguageTag.parse("en-US-x") }.message
		)
	}


	@Test
	fun testParseOrNull() {
		assertNull(LanguageTag.parseOrNull(""))
		assertNull(LanguageTag.parseOrNull("longlonglong"))
		assertNull(LanguageTag.parseOrNull("-US"))
		assertNull(LanguageTag.parseOrNull("en-US-0"))
		assertNull(LanguageTag.parseOrNull("en-US-x"))
	}
}
