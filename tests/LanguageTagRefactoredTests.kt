package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LanguageTagRefactoredTests {

	// parse() length guard

	@Test
	fun parse_throwsOnStringOver256Chars() {
		val longTag = "en-" + "a".repeat(254)
		assertEquals(
			actual = assertFails { LanguageTag.parse(longTag) }.message,
			expected = "Language tag too long: ${longTag.length} characters (max 256)",
		)
	}

	@Test
	fun parse_acceptsStringAtExactly256Chars() {
		// 256 chars but ill-formed — should throw the normal error, not the length error
		val tag = "a".repeat(256)
		val error = assertFails { LanguageTag.parse(tag) }
		assertEquals(actual = error.message, expected = "Ill-formed BCP 47 language tag: $tag")
	}


	// parseOrNull() length guard

	@Test
	fun parseOrNull_returnsNullOnStringOver256Chars() {
		val longTag = "en-" + "a".repeat(254)
		assertNull(LanguageTag.parseOrNull(longTag))
	}

	@Test
	fun parseOrNull_acceptsStringAtExactly256Chars() {
		// 256 chars but ill-formed — should return null via normal parsing, not the length guard
		assertNull(LanguageTag.parseOrNull("a".repeat(256)))
	}


	// copy() require on private-use-only tags

	@Test
	fun copy_privateUseOnly_allowsLanguageUpgrade() {
		// Setting language on a private-use tag routes to forLanguage, which succeeds.
		val tag = LanguageTag.forPrivateUse("x-custom")
		val copy = tag.copy(language = "en")
		assertEquals(actual = copy.toString(), expected = "en-x-custom")
	}

	@Test
	fun copy_privateUseOnly_rejectsRegion() {
		val tag = LanguageTag.forPrivateUse("x-custom")
		assertEquals(
			actual = assertFails { tag.copy(region = "US") }.message,
			expected = "Cannot set language, script, region, variants, extlangs, or extensions on a private-use-only tag",
		)
	}

	@Test
	fun copy_privateUseOnly_rejectsScript() {
		val tag = LanguageTag.forPrivateUse("x-custom")
		assertEquals(
			actual = assertFails { tag.copy(script = "Latn") }.message,
			expected = "Cannot set language, script, region, variants, extlangs, or extensions on a private-use-only tag",
		)
	}

	@Test
	fun copy_privateUseOnly_rejectsVariants() {
		val tag = LanguageTag.forPrivateUse("x-custom")
		assertEquals(
			actual = assertFails { tag.copy(variants = listOf("nedis")) }.message,
			expected = "Cannot set language, script, region, variants, extlangs, or extensions on a private-use-only tag",
		)
	}

	@Test
	fun copy_privateUseOnly_rejectsExtlangs() {
		val tag = LanguageTag.forPrivateUse("x-custom")
		assertEquals(
			actual = assertFails { tag.copy(extlangs = listOf("cmn")) }.message,
			expected = "Cannot set language, script, region, variants, extlangs, or extensions on a private-use-only tag",
		)
	}

	@Test
	fun copy_privateUseOnly_rejectsExtensions() {
		val tag = LanguageTag.forPrivateUse("x-custom")
		assertEquals(
			actual = assertFails { tag.copy(extensions = listOf("u-co-phonebk")) }.message,
			expected = "Cannot set language, script, region, variants, extlangs, or extensions on a private-use-only tag",
		)
	}

	@Test
	fun copy_privateUseOnly_allowsPrivateuseChange() {
		val tag = LanguageTag.forPrivateUse("x-custom")
		val copy = tag.copy(privateuse = "x-other")
		assertEquals(actual = copy.toString(), expected = "x-other")
	}


	// forLanguage / forLanguageOrNull behavior preserved after refactoring

	@Test
	fun forLanguage_basicBehavior() {
		assertEquals(actual = LanguageTag.forLanguage(language = "EN").toString(), expected = "en")
		assertEquals(actual = LanguageTag.forLanguage(language = "EN", region = "us").toString(), expected = "en-US")
		assertEquals(actual = LanguageTag.forLanguage(language = "ZH", script = "hANT", region = "cn").toString(), expected = "zh-Hant-CN")
	}

	@Test
	fun forLanguage_throwsOnInvalidLanguage() {
		assertEquals(
			actual = assertFails { LanguageTag.forLanguage(language = "") }.message,
			expected = "Invalid language: ",
		)
		assertEquals(
			actual = assertFails { LanguageTag.forLanguage(language = "x") }.message,
			expected = "Invalid language: x",
		)
	}

	@Test
	fun forLanguage_throwsOnInvalidRegion() {
		assertEquals(
			actual = assertFails { LanguageTag.forLanguage(language = "en", region = "x") }.message,
			expected = "Invalid region: x",
		)
	}

	@Test
	fun forLanguage_throwsOnInvalidScript() {
		assertEquals(
			actual = assertFails { LanguageTag.forLanguage(language = "en", script = "x") }.message,
			expected = "Invalid script: x",
		)
	}

	@Test
	fun forLanguage_throwsOnInvalidVariant() {
		assertEquals(
			actual = assertFails { LanguageTag.forLanguage(language = "zh", script = "Hant", region = "CN", variants = listOf("var")) }.message,
			expected = "Invalid variant: var",
		)
	}

	@Test
	fun forLanguageOrNull_returnsNullOnInvalid() {
		assertNull(LanguageTag.forLanguageOrNull(language = ""))
		assertNull(LanguageTag.forLanguageOrNull(language = "x"))
		assertNull(LanguageTag.forLanguageOrNull(language = "en", region = "x"))
		assertNull(LanguageTag.forLanguageOrNull(language = "en", script = "x"))
		assertNull(LanguageTag.forLanguageOrNull(language = "zh", script = "Hant", region = "CN", variants = listOf("var")))
	}

	@Test
	fun forLanguageOrNull_returnsTagOnValid() {
		val tag = LanguageTag.forLanguageOrNull(language = "en", region = "US")
		assertNotNull(tag)
		assertEquals(actual = tag.toString(), expected = "en-US")
	}

	@Test
	fun forPrivateUse_throwsOnInvalid() {
		assertEquals(
			actual = assertFails { LanguageTag.forPrivateUse(privateuse = "a-b-c") }.message,
			expected = "Invalid privateuse: a-b-c",
		)
	}

	@Test
	fun forPrivateUseOrNull_returnsNullOnInvalid() {
		assertNull(LanguageTag.forPrivateUseOrNull(privateuse = "a-b-c"))
	}

	@Test
	fun forPrivateUseOrNull_returnsTagOnValid() {
		val tag = LanguageTag.forPrivateUseOrNull(privateuse = "x-hello")
		assertNotNull(tag)
		assertEquals(actual = tag.toString(), expected = "x-hello")
	}
}
