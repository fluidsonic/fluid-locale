package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LanguageTagCopyTests {

	@Test
	fun copy_changedLanguage() {
		val original = LanguageTag.parse("en-US")
		val copy = original.copy(language = "fr")

		assertEquals(actual = copy.toString(), expected = "fr-US")
	}

	@Test
	fun copy_changedRegion() {
		val original = LanguageTag.parse("en-US")
		val copy = original.copy(region = "GB")

		assertEquals(actual = copy.toString(), expected = "en-GB")
	}

	@Test
	fun copy_changedScript() {
		val original = LanguageTag.parse("zh-Hans-CN")
		val copy = original.copy(script = "Hant")

		assertEquals(actual = copy.toString(), expected = "zh-Hant-CN")
	}

	@Test
	fun copy_changedVariants() {
		val original = LanguageTag.parse("sl-IT-nedis")
		val copy = original.copy(variants = listOf("rozaj"))

		assertEquals(actual = copy.toString(), expected = "sl-IT-rozaj")
	}

	@Test
	fun copy_nullRegion() {
		val original = LanguageTag.parse("en-US")
		val copy = original.copy(region = null)

		assertEquals(actual = copy.toString(), expected = "en")
		assertNull(copy.region)
	}

	@Test
	fun copy_nullScript() {
		val original = LanguageTag.parse("zh-Hans-CN")
		val copy = original.copy(script = null)

		assertEquals(actual = copy.toString(), expected = "zh-CN")
		assertNull(copy.script)
	}

	@Test
	fun copy_emptyVariants() {
		val original = LanguageTag.parse("sl-IT-nedis")
		val copy = original.copy(variants = emptyList())

		assertEquals(actual = copy.toString(), expected = "sl-IT")
		assertEquals(actual = copy.variants, expected = emptyList())
	}

	@Test
	fun copy_retainsUnchangedFields() {
		val original = LanguageTag.parse("zh-Hans-CN")
		val copy = original.copy()

		assertEquals(actual = copy, expected = original)
		assertEquals(actual = copy.language, expected = original.language)
		assertEquals(actual = copy.script, expected = original.script)
		assertEquals(actual = copy.region, expected = original.region)
		assertEquals(actual = copy.variants, expected = original.variants)
		assertEquals(actual = copy.extensions, expected = original.extensions)
		assertEquals(actual = copy.extlangs, expected = original.extlangs)
		assertEquals(actual = copy.privateuse, expected = original.privateuse)
	}

	@Test
	fun copy_privateUseOnlyTag() {
		val original = LanguageTag.forPrivateUse("x-custom")
		val copy = original.copy()

		assertEquals(actual = copy.toString(), expected = "x-custom")
		assertNull(copy.language)
		assertNotNull(copy.privateuse)
	}

	@Test
	fun copy_addRegionToLanguageOnly() {
		val original = LanguageTag.forLanguage(language = "en")
		val copy = original.copy(region = "US")

		assertEquals(actual = copy.toString(), expected = "en-US")
	}

	@Test
	fun copy_addPrivateuse() {
		val original = LanguageTag.parse("en-US")
		val copy = original.copy(privateuse = "x-custom")

		assertEquals(actual = copy.toString(), expected = "en-US-x-custom")
	}
}
