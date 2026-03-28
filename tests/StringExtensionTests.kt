package io.fluidsonic.locale

import kotlin.test.*


class StringExtensionTests {

	@Test
	fun testIsAlpha_alphaStrings() {
		assertTrue("abc".isAlpha())
		assertTrue("ABC".isAlpha())
		assertTrue("aBcDeF".isAlpha())
	}

	@Test
	fun testIsAlpha_nonAlphaStrings() {
		assertFalse("abc1".isAlpha())
		assertFalse("123".isAlpha())
		assertFalse("ab-cd".isAlpha())
		assertFalse("ab cd".isAlpha())
	}

	@Test
	fun testIsAlpha_emptyString() {
		assertTrue("".isAlpha())
	}

	@Test
	fun testIsAlphaNumeric_alphanumericStrings() {
		assertTrue("abc".isAlphaNumeric())
		assertTrue("123".isAlphaNumeric())
		assertTrue("abc123".isAlphaNumeric())
		assertTrue("A1B2C3".isAlphaNumeric())
	}

	@Test
	fun testIsAlphaNumeric_nonAlphanumericStrings() {
		assertFalse("abc-123".isAlphaNumeric())
		assertFalse("ab cd".isAlphaNumeric())
		assertFalse("hello!".isAlphaNumeric())
	}

	@Test
	fun testIsAlphaNumeric_emptyString() {
		assertTrue("".isAlphaNumeric())
	}

	@Test
	fun testIsNumeric_numericStrings() {
		assertTrue("0".isNumeric())
		assertTrue("123".isNumeric())
		assertTrue("0123456789".isNumeric())
	}

	@Test
	fun testIsNumeric_nonNumericStrings() {
		assertFalse("12a3".isNumeric())
		assertFalse("abc".isNumeric())
		assertFalse("12-34".isNumeric())
		assertFalse("12 34".isNumeric())
	}

	@Test
	fun testIsNumeric_emptyString() {
		assertTrue("".isNumeric())
	}

	@Test
	fun testLowercase_mixedCase() {
		assertEquals(expected = "hello world", actual = "Hello World".lowercase())
		assertEquals(expected = "abcdef", actual = "ABCDEF".lowercase())
		assertEquals(expected = "abc123", actual = "ABC123".lowercase())
	}

	@Test
	fun testLowercase_alreadyLowercase() {
		assertEquals(expected = "hello", actual = "hello".lowercase())
	}

	@Test
	fun testLowercase_emptyString() {
		assertEquals(expected = "", actual = "".lowercase())
	}

	@Test
	fun testLowercase_specialChars() {
		assertEquals(expected = "hello-world", actual = "HELLO-WORLD".lowercase())
		assertEquals(expected = "a1b2c3", actual = "A1B2C3".lowercase())
	}

	@Test
	fun testUppercase_mixedCase() {
		assertEquals(expected = "HELLO WORLD", actual = "Hello World".uppercase())
		assertEquals(expected = "ABCDEF", actual = "abcdef".uppercase())
		assertEquals(expected = "ABC123", actual = "abc123".uppercase())
	}

	@Test
	fun testUppercase_alreadyUppercase() {
		assertEquals(expected = "HELLO", actual = "HELLO".uppercase())
	}

	@Test
	fun testUppercase_emptyString() {
		assertEquals(expected = "", actual = "".uppercase())
	}

	@Test
	fun testUppercase_specialChars() {
		assertEquals(expected = "HELLO-WORLD", actual = "hello-world".uppercase())
		assertEquals(expected = "A1B2C3", actual = "a1b2c3".uppercase())
	}

	@Test
	fun testToUppercaseFirstLowercaseRest_normalCases() {
		assertEquals(expected = "Hello", actual = "hello".toUppercaseFirstLowercaseRest())
		assertEquals(expected = "Hello", actual = "HELLO".toUppercaseFirstLowercaseRest())
		assertEquals(expected = "Hello", actual = "hELLO".toUppercaseFirstLowercaseRest())
	}

	@Test
	fun testToUppercaseFirstLowercaseRest_singleChar() {
		assertEquals(expected = "A", actual = "a".toUppercaseFirstLowercaseRest())
		assertEquals(expected = "A", actual = "A".toUppercaseFirstLowercaseRest())
	}

	@Test
	fun testToUppercaseFirstLowercaseRest_emptyString() {
		assertEquals(expected = "", actual = "".toUppercaseFirstLowercaseRest())
	}

	@Test
	fun testToUppercaseFirstLowercaseRest_alreadyFormatted() {
		assertEquals(expected = "Hello", actual = "Hello".toUppercaseFirstLowercaseRest())
	}

	@Test
	fun testToUppercaseFirstLowercaseRest_withNonLetters() {
		assertEquals(expected = "A1b2c3", actual = "a1B2C3".toUppercaseFirstLowercaseRest())
	}

	@Test
	fun testMapCharacters_identity() {
		assertEquals(expected = "hello", actual = "hello".mapCharacters { it })
	}

	@Test
	fun testMapCharacters_transformation() {
		assertEquals(expected = "bcd", actual = "abc".mapCharacters { it + 1 })
	}

	@Test
	fun testMapCharacters_emptyString() {
		assertEquals(expected = "", actual = "".mapCharacters { it + 1 })
	}

	@Test
	fun testMapCharactersIndexed_indexBasedTransformation() {
		assertEquals(
			expected = "a_c_e",
			actual = "abcde".mapCharactersIndexed { index, char ->
				if (index % 2 == 0) char else '_'
			}
		)
	}

	@Test
	fun testMapCharactersIndexed_emptyString() {
		assertEquals(
			expected = "",
			actual = "".mapCharactersIndexed { _, char -> char }
		)
	}

	@Test
	fun testMapCharactersIndexed_usesIndex() {
		assertEquals(
			expected = "aceg",
			actual = "abcd".mapCharactersIndexed { index, char -> char + index }
		)
	}
}
