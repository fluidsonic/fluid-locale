package tests

import io.fluidsonic.locale.*
import kotlin.test.*
import platform.Foundation.*


class LocaleTestsDarwin {

	@Test
	fun testToCommon() {
		assertEquals(expected = "und", actual = PlatformLocale("").toCommon().toString())
		assertEquals(expected = "en", actual = PlatformLocale("en").toCommon().toString())
		assertEquals(expected = "en", actual = PlatformLocale("EN").toCommon().toString())
		assertEquals(expected = "en-US", actual = PlatformLocale("en-US").toCommon().toString())
		assertEquals(expected = "en-US", actual = PlatformLocale("EN_us").toCommon().toString())
		assertEquals(expected = "und-US", actual = PlatformLocale("-US").toCommon().toString())
		assertEquals(expected = "und-US", actual = PlatformLocale("_us").toCommon().toString())
		assertEquals(expected = "zh-Hant-CN", actual = PlatformLocale("zh-Hant-CN").toCommon().toString())
		assertEquals(expected = "zh-Hant-CN", actual = PlatformLocale("ZH_hANt_cn").toCommon().toString())
	}


	@Test
	fun testToPlatform() {
		assertEquals(expected = "", actual = Locale.forLanguageTag("und").toPlatform().localeIdentifier)
		assertEquals(expected = "en", actual = Locale.forLanguageTag("en").toPlatform().localeIdentifier)
		assertEquals(expected = "en", actual = Locale.forLanguageTag("EN").toPlatform().localeIdentifier)
		assertEquals(expected = "en-US", actual = Locale.forLanguageTag("en-US").toPlatform().localeIdentifier)
		assertEquals(expected = "-US", actual = Locale.forLanguageTag("und-US").toPlatform().localeIdentifier)
		assertEquals(expected = "zh-Hant-CN", actual = Locale.forLanguageTag("zh-Hant-CN").toPlatform().localeIdentifier)
	}
}
