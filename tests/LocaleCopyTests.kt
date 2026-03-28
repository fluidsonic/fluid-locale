package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LocaleCopyTests {

	@Test
	fun locale_forLanguageWithRegion() {
		val locale = Locale.forLanguage(language = "en", region = "US")

		assertEquals(actual = locale.language, expected = "en")
		assertEquals(actual = locale.region, expected = "US")
		assertEquals(actual = locale.toString(), expected = "en-US")
	}

	@Test
	fun locale_copyViaLanguageTag() {
		val locale = Locale.forLanguage(language = "en")
		val tag = locale.toLanguageTag()
		val modifiedTag = tag.copy(region = "US")
		val modifiedLocale = Locale.forLanguageTag(modifiedTag)

		assertEquals(actual = modifiedLocale.language, expected = "en")
		assertEquals(actual = modifiedLocale.region, expected = "US")
		assertEquals(actual = modifiedLocale.toString(), expected = "en-US")
	}

	@Test
	fun roundTrip_localeToTagToLocale() {
		val original = Locale.forLanguage(language = "zh", script = "Hant", region = "TW", variants = listOf("variant"))

		val tag = original.toLanguageTag()
		val roundTripped = Locale.forLanguageTag(tag)

		assertEquals(actual = roundTripped.language, expected = original.language)
		assertEquals(actual = roundTripped.region, expected = original.region)
		assertEquals(actual = roundTripped.script, expected = original.script)
		assertEquals(actual = roundTripped.variants, expected = original.variants)
		assertEquals(actual = roundTripped, expected = original)
	}

	@Test
	fun roundTrip_stringToLocaleToString() {
		val tagString = "en-Latn-US"
		val locale = Locale.forLanguageTag(tagString)

		assertEquals(actual = locale.toString(), expected = tagString)
	}

	@Test
	fun roundTrip_localeToStringToLocale() {
		val original = Locale.forLanguage(language = "de", region = "AT")
		val str = original.toString()
		val parsed = Locale.forLanguageTag(str)

		assertEquals(actual = parsed, expected = original)
	}

	@Test
	fun copy_changeLanguage() {
		val locale = Locale.forLanguage(language = "en", region = "US")
		val copied = locale.copy(language = "fr")

		assertEquals(actual = copied.language, expected = "fr")
		assertEquals(actual = copied.region, expected = "US")
		assertEquals(actual = copied.toString(), expected = "fr-US")
	}

	@Test
	fun copy_changeRegion() {
		val locale = Locale.forLanguage(language = "en")
		val copied = locale.copy(region = "GB")

		assertEquals(actual = copied.language, expected = "en")
		assertEquals(actual = copied.region, expected = "GB")
		assertEquals(actual = copied.toString(), expected = "en-GB")
	}

	@Test
	fun copy_removeRegion() {
		val locale = Locale.forLanguage(language = "en", region = "US")
		val copied = locale.copy(region = null)

		assertEquals(actual = copied.language, expected = "en")
		assertNull(copied.region)
		assertEquals(actual = copied.toString(), expected = "en")
	}

	@Test
	fun copy_changeScript() {
		val locale = Locale.forLanguage(language = "zh", region = "TW")
		val copied = locale.copy(script = "Hant")

		assertEquals(actual = copied.language, expected = "zh")
		assertEquals(actual = copied.script, expected = "Hant")
		assertEquals(actual = copied.region, expected = "TW")
		assertEquals(actual = copied.toString(), expected = "zh-Hant-TW")
	}

	@Test
	fun copy_noChanges() {
		val locale = Locale.forLanguage(language = "en", script = "Latn", region = "US")
		val copied = locale.copy()

		assertEquals(actual = copied, expected = locale)
	}

	@Test
	fun copy_multipleChanges() {
		val locale = Locale.forLanguage(language = "en", region = "US")
		val copied = locale.copy(language = "de", region = "AT")

		assertEquals(actual = copied.language, expected = "de")
		assertEquals(actual = copied.region, expected = "AT")
		assertEquals(actual = copied.toString(), expected = "de-AT")
	}

	@Test
	fun locale_rootRoundTrip() {
		val root = Locale.root
		val tag = root.toLanguageTag()
		val fromTag = Locale.forLanguageTag(tag)

		assertEquals(actual = fromTag, expected = root)
		assertNull(fromTag.language)
	}
}
