package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LanguageTagValidationTests {

	// isLanguage — valid: 2-8 ALPHA

	@Test
	fun isLanguage_validTwoChar() {
		assertTrue(LanguageTag.isLanguage("en"))
	}

	@Test
	fun isLanguage_validThreeChar() {
		assertTrue(LanguageTag.isLanguage("eng"))
	}

	@Test
	fun isLanguage_validEightChar() {
		assertTrue(LanguageTag.isLanguage("abcdefgh"))
	}

	@Test
	fun isLanguage_invalidOneChar() {
		assertFalse(LanguageTag.isLanguage("a"))
	}

	@Test
	fun isLanguage_invalidNineChars() {
		assertFalse(LanguageTag.isLanguage("abcdefghi"))
	}

	@Test
	fun isLanguage_invalidDigits() {
		assertFalse(LanguageTag.isLanguage("e1"))
	}

	@Test
	fun isLanguage_invalidEmpty() {
		assertFalse(LanguageTag.isLanguage(""))
	}

	@Test
	fun isLanguage_invalidSpecialChars() {
		assertFalse(LanguageTag.isLanguage("en!"))
	}


	// isRegion — valid: 2 ALPHA or 3 DIGIT

	@Test
	fun isRegion_validTwoAlpha() {
		assertTrue(LanguageTag.isRegion("US"))
	}

	@Test
	fun isRegion_validThreeDigit() {
		assertTrue(LanguageTag.isRegion("001"))
	}

	@Test
	fun isRegion_invalidOneChar() {
		assertFalse(LanguageTag.isRegion("U"))
	}

	@Test
	fun isRegion_invalidFourChars() {
		assertFalse(LanguageTag.isRegion("USAA"))
	}

	@Test
	fun isRegion_invalidMixedTwoChar() {
		assertFalse(LanguageTag.isRegion("U1"))
	}

	@Test
	fun isRegion_invalidMixedThreeChar() {
		assertFalse(LanguageTag.isRegion("00A"))
	}

	@Test
	fun isRegion_invalidEmpty() {
		assertFalse(LanguageTag.isRegion(""))
	}

	@Test
	fun isRegion_invalidThreeAlpha() {
		assertFalse(LanguageTag.isRegion("USA"))
	}


	// isScript — valid: exactly 4 ALPHA

	@Test
	fun isScript_validFourAlpha() {
		assertTrue(LanguageTag.isScript("Latn"))
	}

	@Test
	fun isScript_invalidThreeChars() {
		assertFalse(LanguageTag.isScript("Lat"))
	}

	@Test
	fun isScript_invalidFiveChars() {
		assertFalse(LanguageTag.isScript("Latnn"))
	}

	@Test
	fun isScript_invalidDigits() {
		assertFalse(LanguageTag.isScript("La1n"))
	}

	@Test
	fun isScript_invalidEmpty() {
		assertFalse(LanguageTag.isScript(""))
	}


	// isVariant — valid: 5-8 alphanum, or 4 chars starting with digit

	@Test
	fun isVariant_validFiveAlphanum() {
		assertTrue(LanguageTag.isVariant("nedis"))
	}

	@Test
	fun isVariant_validEightAlphanum() {
		assertTrue(LanguageTag.isVariant("abcd1234"))
	}

	@Test
	fun isVariant_validFourStartingWithDigit() {
		assertTrue(LanguageTag.isVariant("1abc"))
	}

	@Test
	fun isVariant_invalidFourAlpha() {
		assertFalse(LanguageTag.isVariant("abcd"))
	}

	@Test
	fun isVariant_invalidThreeChars() {
		assertFalse(LanguageTag.isVariant("abc"))
	}

	@Test
	fun isVariant_invalidNineChars() {
		assertFalse(LanguageTag.isVariant("abcdefgh1"))
	}

	@Test
	fun isVariant_invalidEmpty() {
		assertFalse(LanguageTag.isVariant(""))
	}


	// isExtension — valid: singleton-subtag format

	@Test
	fun isExtension_validSingletonWithSubtag() {
		assertTrue(LanguageTag.isExtension("a-value"))
	}

	@Test
	fun isExtension_validSingletonWithMultipleSubtags() {
		assertTrue(LanguageTag.isExtension("u-co-phonebk"))
	}

	@Test
	fun isExtension_invalidEmpty() {
		assertFalse(LanguageTag.isExtension(""))
	}

	@Test
	fun isExtension_invalidNoSubtagAfterSingleton() {
		assertFalse(LanguageTag.isExtension("a"))
	}

	@Test
	fun isExtension_invalidXSingleton() {
		assertFalse(LanguageTag.isExtension("x-value"))
	}

	@Test
	fun isExtension_invalidSubtagTooShort() {
		assertFalse(LanguageTag.isExtension("a-v"))
	}


	// isExtensionSingleton(Char) and isExtensionSingleton(String)

	@Test
	fun isExtensionSingleton_validLetter() {
		assertTrue(LanguageTag.isExtensionSingleton('a'))
		assertTrue(LanguageTag.isExtensionSingleton('w'))
		assertTrue(LanguageTag.isExtensionSingleton('y'))
		assertTrue(LanguageTag.isExtensionSingleton('z'))
	}

	@Test
	fun isExtensionSingleton_validDigit() {
		assertTrue(LanguageTag.isExtensionSingleton('0'))
		assertTrue(LanguageTag.isExtensionSingleton('9'))
	}

	@Test
	fun isExtensionSingleton_invalidX() {
		assertFalse(LanguageTag.isExtensionSingleton('x'))
		assertFalse(LanguageTag.isExtensionSingleton('X'))
	}

	@Test
	fun isExtensionSingleton_invalidNonAlphanum() {
		assertFalse(LanguageTag.isExtensionSingleton('!'))
		assertFalse(LanguageTag.isExtensionSingleton('-'))
	}

	@Test
	fun isExtensionSingletonString_valid() {
		assertTrue(LanguageTag.isExtensionSingleton("a"))
		assertTrue(LanguageTag.isExtensionSingleton("0"))
	}

	@Test
	fun isExtensionSingletonString_invalidMultiChar() {
		assertFalse(LanguageTag.isExtensionSingleton("ab"))
	}

	@Test
	fun isExtensionSingletonString_invalidX() {
		assertFalse(LanguageTag.isExtensionSingleton("x"))
	}


	// isExtensionSubtag — valid: 2-8 alphanum

	@Test
	fun isExtensionSubtag_validTwoChar() {
		assertTrue(LanguageTag.isExtensionSubtag("ab"))
	}

	@Test
	fun isExtensionSubtag_validEightChar() {
		assertTrue(LanguageTag.isExtensionSubtag("abcd1234"))
	}

	@Test
	fun isExtensionSubtag_invalidOneChar() {
		assertFalse(LanguageTag.isExtensionSubtag("a"))
	}

	@Test
	fun isExtensionSubtag_invalidNineChars() {
		assertFalse(LanguageTag.isExtensionSubtag("abcdefgh1"))
	}

	@Test
	fun isExtensionSubtag_invalidEmpty() {
		assertFalse(LanguageTag.isExtensionSubtag(""))
	}


	// isExtlang — valid: 3 ALPHA

	@Test
	fun isExtlang_validThreeAlpha() {
		assertTrue(LanguageTag.isExtlang("cmn"))
	}

	@Test
	fun isExtlang_invalidTwoChars() {
		assertFalse(LanguageTag.isExtlang("cm"))
	}

	@Test
	fun isExtlang_invalidFourChars() {
		assertFalse(LanguageTag.isExtlang("cmna"))
	}

	@Test
	fun isExtlang_invalidDigits() {
		assertFalse(LanguageTag.isExtlang("c1n"))
	}


	// isPrivateuse — valid: "x-" followed by 1-8 alphanum subtags

	@Test
	fun isPrivateuse_validSimple() {
		assertTrue(LanguageTag.isPrivateuse("x-abc"))
	}

	@Test
	fun isPrivateuse_validMultipleSubtags() {
		assertTrue(LanguageTag.isPrivateuse("x-a-b-c"))
	}

	@Test
	fun isPrivateuse_invalidEmpty() {
		assertFalse(LanguageTag.isPrivateuse(""))
	}

	@Test
	fun isPrivateuse_invalidWrongPrefix() {
		assertFalse(LanguageTag.isPrivateuse("y-abc"))
	}

	@Test
	fun isPrivateuse_invalidNoSubtag() {
		assertFalse(LanguageTag.isPrivateuse("x"))
	}

	@Test
	fun isPrivateuse_invalidSubtagTooLong() {
		assertFalse(LanguageTag.isPrivateuse("x-toolongva"))
	}


	// isPrivateusePrefix(Char) and isPrivateusePrefix(String)

	@Test
	fun isPrivateusePrefix_validLowerX() {
		assertTrue(LanguageTag.isPrivateusePrefix('x'))
	}

	@Test
	fun isPrivateusePrefix_validUpperX() {
		assertTrue(LanguageTag.isPrivateusePrefix('X'))
	}

	@Test
	fun isPrivateusePrefix_invalidOtherChar() {
		assertFalse(LanguageTag.isPrivateusePrefix('a'))
	}

	@Test
	fun isPrivateusePrefix_stringValid() {
		assertTrue(LanguageTag.isPrivateusePrefix("x"))
		assertTrue(LanguageTag.isPrivateusePrefix("X"))
	}

	@Test
	fun isPrivateusePrefix_stringInvalid() {
		assertFalse(LanguageTag.isPrivateusePrefix("a"))
	}

	@Test
	fun isPrivateusePrefix_stringInvalidEmpty() {
		assertFalse(LanguageTag.isPrivateusePrefix(""))
	}

	@Test
	fun isPrivateusePrefix_stringInvalidMultiChar() {
		assertFalse(LanguageTag.isPrivateusePrefix("xx"))
	}


	// isPrivateuseSubtag — valid: 1-8 alphanum

	@Test
	fun isPrivateuseSubtag_validOneChar() {
		assertTrue(LanguageTag.isPrivateuseSubtag("a"))
	}

	@Test
	fun isPrivateuseSubtag_validEightChars() {
		assertTrue(LanguageTag.isPrivateuseSubtag("abcd1234"))
	}

	@Test
	fun isPrivateuseSubtag_invalidEmpty() {
		assertFalse(LanguageTag.isPrivateuseSubtag(""))
	}

	@Test
	fun isPrivateuseSubtag_invalidNineChars() {
		assertFalse(LanguageTag.isPrivateuseSubtag("abcdefgh1"))
	}
}
