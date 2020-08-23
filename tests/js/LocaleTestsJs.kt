package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LocaleTestsJs {

	@Test
	fun testToCommon() {
		assertEquals(expected = "und", actual = PlatformLocale("und").toCommon().toString())
		assertEquals(expected = "en", actual = PlatformLocale("en").toCommon().toString())
		assertEquals(expected = "en", actual = PlatformLocale("EN").toCommon().toString())
		assertEquals(expected = "en-US", actual = PlatformLocale("en-US").toCommon().toString())
		assertEquals(expected = "und-US", actual = PlatformLocale("und-US").toCommon().toString())
		assertEquals(expected = "zh-Hant-CN", actual = PlatformLocale("zh-Hant-CN").toCommon().toString())
	}


	@Test
	fun testToPlatform() {
		assertEquals(expected = "und", actual = Locale.forLanguageTag("und").toPlatform().toString())
		assertEquals(expected = "en", actual = Locale.forLanguageTag("en").toPlatform().toString())
		assertEquals(expected = "en", actual = Locale.forLanguageTag("EN").toPlatform().toString())
		assertEquals(expected = "en-US", actual = Locale.forLanguageTag("en-US").toPlatform().toString())
		assertEquals(expected = "und-US", actual = Locale.forLanguageTag("und-US").toPlatform().toString())
		assertEquals(expected = "zh-Hant-CN", actual = Locale.forLanguageTag("zh-Hant-CN").toPlatform().toString())
	}
}
