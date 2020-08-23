package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LocaleTests {

	@Test
	fun testEqualsAndHashCode() {
		assertEquals(expected = Locale.forLanguageTag("en-US"), actual = Locale.forLanguageTag("en-US"))
		assertEquals(expected = Locale.forLanguageTag("en-US").hashCode(), actual = Locale.forLanguageTag("en-US").hashCode())

		assertEquals(expected = Locale.forLanguageTag("en-US-variant"), actual = Locale.forLanguageTag("en-US-VARIANT"))
		assertEquals(expected = Locale.forLanguageTag("en-US-variant").hashCode(), actual = Locale.forLanguageTag("en-US-VARIANT").hashCode())
	}


	@Test
	fun testForLanguage() {
		assertEquals(expected = "und", actual = Locale.forLanguage(language = null).toString())
		assertEquals(expected = "und", actual = Locale.forLanguage(language = "").toString())
		assertEquals(expected = "en", actual = Locale.forLanguage(language = "EN").toString())
		assertEquals(expected = "en-US", actual = Locale.forLanguage(language = "EN", region = "us").toString())
		assertEquals(expected = "zh-Hant-CN", actual = Locale.forLanguage(language = "ZH", script = "hANT", region = "cn").toString())
		assertEquals(
			expected = "zh-Hant-CN-VARIANT1-VARIANT2",
			actual = Locale.forLanguage(language = "ZH", script = "hANT", region = "cn", variants = listOf("VARIANT1", "VARIANT2")).toString()
		)
	}


	@Test
	fun testForLanguageOrFail() {
		assertEquals(
			expected = "Invalid language: x",
			actual = assertFails { Locale.forLanguage(language = "x") }.message
		)
		assertEquals(
			expected = "Invalid region: x",
			actual = assertFails { Locale.forLanguage(language = "en", region = "x") }.message
		)
		assertEquals(
			expected = "Invalid script: x",
			actual = assertFails { Locale.forLanguage(language = "en", script = "x", region = "US") }.message
		)
		assertEquals(
			expected = "Invalid variant: var",
			actual = assertFails { Locale.forLanguage(language = "zh", script = "Hant", region = "CN", variants = listOf("var")) }.message
		)
	}


	@Test
	fun testForLanguageOrNull() {
		assertNull(Locale.forLanguageOrNull(language = "x"))
		assertNull(Locale.forLanguageOrNull(language = "en", region = "x"))
		assertNull(Locale.forLanguageOrNull(language = "en", script = "x", region = "US"))
		assertNull(Locale.forLanguageOrNull(language = "zh", script = "Hant", region = "CN", variants = listOf("var")))
	}


	@Test
	fun testForLanguageTag() {
		assertEquals(expected = "en-US", actual = Locale.forLanguageTag(LanguageTag.parse("en-US")).toString())
	}


	@Test
	fun testForLanguageTagString() {
		assertEquals(expected = "en-US", actual = Locale.forLanguageTag("en-US").toString())
		assertEquals(expected = "en-US", actual = Locale.forLanguageTag("en-us").toString())
	}


	@Test
	fun testProperties() {
		val tag = LanguageTag.parse("zh-Hant-CN-Variant")
		val locale = Locale.forLanguageTag(tag)

		assertEquals(expected = tag.language, actual = locale.language)
		assertEquals(expected = tag.region, actual = locale.region)
		assertEquals(expected = tag.script, actual = locale.script)
		assertEquals(expected = tag.variants, actual = locale.variants)
	}


	@Test
	fun testToLanguageTag() {
		val tag = LanguageTag.parse("zh-Hant-CN-Variant")
		val locale = Locale.forLanguageTag(tag)

		assertEquals(expected = tag, actual = locale.toLanguageTag())
	}


	@Test
	fun testRoot() {
		val locale = Locale.root

		assertNull(locale.language)
		assertNull(locale.region)
		assertNull(locale.script)
		assertEquals(expected = emptyList(), actual = locale.variants)
	}


	@Test
	fun testToString() {
		assertEquals(expected = "zh-Hant-CN-Variant", actual = Locale.forLanguageTag("ZH-HANT-CN-Variant").toString())
	}
}
