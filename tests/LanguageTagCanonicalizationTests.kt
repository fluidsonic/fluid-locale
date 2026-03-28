package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LanguageTagCanonicalizationTests {

	// canonicalizeLanguage

	@Test
	fun canonicalizeLanguage_null() {
		assertNull(LanguageTag.canonicalizeLanguage(null))
	}

	@Test
	fun canonicalizeLanguage_empty() {
		assertNull(LanguageTag.canonicalizeLanguage(""))
	}

	@Test
	fun canonicalizeLanguage_uppercase() {
		assertEquals(actual = LanguageTag.canonicalizeLanguage("EN"), expected = "en")
	}

	@Test
	fun canonicalizeLanguage_alreadyLowercase() {
		assertEquals(actual = LanguageTag.canonicalizeLanguage("en"), expected = "en")
	}


	// canonicalizeRegion

	@Test
	fun canonicalizeRegion_null() {
		assertNull(LanguageTag.canonicalizeRegion(null))
	}

	@Test
	fun canonicalizeRegion_empty() {
		assertNull(LanguageTag.canonicalizeRegion(""))
	}

	@Test
	fun canonicalizeRegion_lowercase() {
		assertEquals(actual = LanguageTag.canonicalizeRegion("us"), expected = "US")
	}

	@Test
	fun canonicalizeRegion_alreadyUppercase() {
		assertEquals(actual = LanguageTag.canonicalizeRegion("US"), expected = "US")
	}

	@Test
	fun canonicalizeRegion_numericUnchanged() {
		assertEquals(actual = LanguageTag.canonicalizeRegion("001"), expected = "001")
	}


	// canonicalizeScript

	@Test
	fun canonicalizeScript_null() {
		assertNull(LanguageTag.canonicalizeScript(null))
	}

	@Test
	fun canonicalizeScript_empty() {
		assertNull(LanguageTag.canonicalizeScript(""))
	}

	@Test
	fun canonicalizeScript_allLowercase() {
		assertEquals(actual = LanguageTag.canonicalizeScript("latn"), expected = "Latn")
	}

	@Test
	fun canonicalizeScript_allUppercase() {
		assertEquals(actual = LanguageTag.canonicalizeScript("LATN"), expected = "Latn")
	}

	@Test
	fun canonicalizeScript_alreadyTitleCase() {
		assertEquals(actual = LanguageTag.canonicalizeScript("Latn"), expected = "Latn")
	}


	// canonicalizeVariant

	@Test
	fun canonicalizeVariant_null() {
		assertNull(LanguageTag.canonicalizeVariant(null))
	}

	@Test
	fun canonicalizeVariant_empty() {
		assertNull(LanguageTag.canonicalizeVariant(""))
	}

	@Test
	fun canonicalizeVariant_preservesCase() {
		assertEquals(actual = LanguageTag.canonicalizeVariant("VARIANT"), expected = "VARIANT")
	}

	@Test
	fun canonicalizeVariant_preservesLowercase() {
		assertEquals(actual = LanguageTag.canonicalizeVariant("variant"), expected = "variant")
	}


	// canonicalizeVariants

	@Test
	fun canonicalizeVariants_emptyList() {
		assertEquals(actual = LanguageTag.canonicalizeVariants(emptyList()), expected = emptyList())
	}

	@Test
	fun canonicalizeVariants_withValues() {
		assertEquals(
			actual = LanguageTag.canonicalizeVariants(listOf("VARIANT1", "variant2")),
			expected = listOf("VARIANT1", "variant2"),
		)
	}

	@Test
	fun canonicalizeVariants_filtersEmpty() {
		assertEquals(
			actual = LanguageTag.canonicalizeVariants(listOf("VARIANT", "")),
			expected = listOf("VARIANT"),
		)
	}


	// canonicalizeExtension

	@Test
	fun canonicalizeExtension_null() {
		assertNull(LanguageTag.canonicalizeExtension(null))
	}

	@Test
	fun canonicalizeExtension_empty() {
		assertNull(LanguageTag.canonicalizeExtension(""))
	}

	@Test
	fun canonicalizeExtension_uppercase() {
		assertEquals(actual = LanguageTag.canonicalizeExtension("A-VALUE"), expected = "a-value")
	}

	@Test
	fun canonicalizeExtension_alreadyLowercase() {
		assertEquals(actual = LanguageTag.canonicalizeExtension("a-value"), expected = "a-value")
	}


	// canonicalizeExtensions

	@Test
	fun canonicalizeExtensions_emptyList() {
		assertEquals(actual = LanguageTag.canonicalizeExtensions(emptyList()), expected = emptyList())
	}

	@Test
	fun canonicalizeExtensions_withValues() {
		assertEquals(
			actual = LanguageTag.canonicalizeExtensions(listOf("B-VALUE", "A-OTHER")),
			expected = listOf("a-other", "b-value"),
		)
	}


	// canonicalizeExtensionSingleton

	@Test
	fun canonicalizeExtensionSingleton_null() {
		assertNull(LanguageTag.canonicalizeExtensionSingleton(null))
	}

	@Test
	fun canonicalizeExtensionSingleton_empty() {
		assertNull(LanguageTag.canonicalizeExtensionSingleton(""))
	}

	@Test
	fun canonicalizeExtensionSingleton_uppercase() {
		assertEquals(actual = LanguageTag.canonicalizeExtensionSingleton("A"), expected = "a")
	}

	@Test
	fun canonicalizeExtensionSingleton_alreadyLowercase() {
		assertEquals(actual = LanguageTag.canonicalizeExtensionSingleton("a"), expected = "a")
	}


	// canonicalizeExtensionSubtag

	@Test
	fun canonicalizeExtensionSubtag_null() {
		assertNull(LanguageTag.canonicalizeExtensionSubtag(null))
	}

	@Test
	fun canonicalizeExtensionSubtag_empty() {
		assertNull(LanguageTag.canonicalizeExtensionSubtag(""))
	}

	@Test
	fun canonicalizeExtensionSubtag_uppercase() {
		assertEquals(actual = LanguageTag.canonicalizeExtensionSubtag("VALUE"), expected = "value")
	}

	@Test
	fun canonicalizeExtensionSubtag_alreadyLowercase() {
		assertEquals(actual = LanguageTag.canonicalizeExtensionSubtag("value"), expected = "value")
	}


	// canonicalizeExtlang

	@Test
	fun canonicalizeExtlang_null() {
		assertNull(LanguageTag.canonicalizeExtlang(null))
	}

	@Test
	fun canonicalizeExtlang_empty() {
		assertNull(LanguageTag.canonicalizeExtlang(""))
	}

	@Test
	fun canonicalizeExtlang_uppercase() {
		assertEquals(actual = LanguageTag.canonicalizeExtlang("CMN"), expected = "cmn")
	}

	@Test
	fun canonicalizeExtlang_alreadyLowercase() {
		assertEquals(actual = LanguageTag.canonicalizeExtlang("cmn"), expected = "cmn")
	}


	// canonicalizeExtlangs

	@Test
	fun canonicalizeExtlangs_emptyList() {
		assertEquals(actual = LanguageTag.canonicalizeExtlangs(emptyList()), expected = emptyList())
	}

	@Test
	fun canonicalizeExtlangs_withValues() {
		assertEquals(
			actual = LanguageTag.canonicalizeExtlangs(listOf("CMN", "yue")),
			expected = listOf("cmn", "yue"),
		)
	}


	// canonicalizePrivateuse

	@Test
	fun canonicalizePrivateuse_null() {
		assertNull(LanguageTag.canonicalizePrivateuse(null))
	}

	@Test
	fun canonicalizePrivateuse_empty() {
		assertNull(LanguageTag.canonicalizePrivateuse(""))
	}

	@Test
	fun canonicalizePrivateuse_uppercase() {
		assertEquals(actual = LanguageTag.canonicalizePrivateuse("X-VALUE"), expected = "x-value")
	}

	@Test
	fun canonicalizePrivateuse_alreadyLowercase() {
		assertEquals(actual = LanguageTag.canonicalizePrivateuse("x-value"), expected = "x-value")
	}


	// canonicalizePrivateusePrefix

	@Test
	fun canonicalizePrivateusePrefix_null() {
		assertNull(LanguageTag.canonicalizePrivateusePrefix(null))
	}

	@Test
	fun canonicalizePrivateusePrefix_empty() {
		assertNull(LanguageTag.canonicalizePrivateusePrefix(""))
	}

	@Test
	fun canonicalizePrivateusePrefix_uppercase() {
		assertEquals(actual = LanguageTag.canonicalizePrivateusePrefix("X"), expected = "x")
	}

	@Test
	fun canonicalizePrivateusePrefix_alreadyLowercase() {
		assertEquals(actual = LanguageTag.canonicalizePrivateusePrefix("x"), expected = "x")
	}


	// canonicalizePrivateuseSubtag

	@Test
	fun canonicalizePrivateuseSubtag_null() {
		assertNull(LanguageTag.canonicalizePrivateuseSubtag(null))
	}

	@Test
	fun canonicalizePrivateuseSubtag_empty() {
		assertNull(LanguageTag.canonicalizePrivateuseSubtag(""))
	}

	@Test
	fun canonicalizePrivateuseSubtag_uppercase() {
		assertEquals(actual = LanguageTag.canonicalizePrivateuseSubtag("VALUE"), expected = "value")
	}

	@Test
	fun canonicalizePrivateuseSubtag_alreadyLowercase() {
		assertEquals(actual = LanguageTag.canonicalizePrivateuseSubtag("value"), expected = "value")
	}
}
