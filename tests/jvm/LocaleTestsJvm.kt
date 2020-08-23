package tests

import java.util.Locale.Builder as LocaleBuilder
import io.fluidsonic.locale.*
import kotlin.test.*


class LocaleTestsJvm {

	@Test
	fun testToCommon() {
		assertEquals(expected = "und", actual = PlatformLocale("").toCommon().toString())
		assertEquals(expected = "en", actual = PlatformLocale.forLanguageTag("en").toCommon().toString())
		assertEquals(expected = "en", actual = PlatformLocale("EN").toCommon().toString())
		assertEquals(expected = "en-US", actual = PlatformLocale.forLanguageTag("en-US").toCommon().toString())
		assertEquals(expected = "en-US", actual = PlatformLocale("EN", "us").toCommon().toString())
		assertEquals(expected = "und-US", actual = PlatformLocale.forLanguageTag("und-US").toCommon().toString())
		assertEquals(expected = "und-US", actual = PlatformLocale("", "us").toCommon().toString())
		assertEquals(expected = "zh-Hant-CN", actual = PlatformLocale.forLanguageTag("zh-Hant-CN").toCommon().toString())
		assertEquals(expected = "zh-Hant-CN", actual = LocaleBuilder().setLanguage("ZH").setScript("hANt").setRegion("cn").build().toCommon().toString())
	}


	@Test
	fun testToPlatform() {
		assertEquals(expected = "und", actual = Locale.forLanguageTag("und").toPlatform().toLanguageTag())
		assertEquals(expected = "en", actual = Locale.forLanguageTag("en").toPlatform().toLanguageTag())
		assertEquals(expected = "en", actual = Locale.forLanguageTag("EN").toPlatform().toLanguageTag())
		assertEquals(expected = "en-US", actual = Locale.forLanguageTag("en-US").toPlatform().toLanguageTag())
		assertEquals(expected = "und-US", actual = Locale.forLanguageTag("und-US").toPlatform().toLanguageTag())
		assertEquals(expected = "zh-Hant-CN", actual = Locale.forLanguageTag("zh-Hant-CN").toPlatform().toLanguageTag())
	}
}
