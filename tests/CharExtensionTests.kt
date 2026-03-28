package io.fluidsonic.locale

import kotlin.test.*


class CharExtensionTests {

	@Test
	fun testIsAlpha_lowercaseLetters() {
		assertTrue('a'.isAlpha())
		assertTrue('m'.isAlpha())
		assertTrue('z'.isAlpha())
	}

	@Test
	fun testIsAlpha_uppercaseLetters() {
		assertTrue('A'.isAlpha())
		assertTrue('M'.isAlpha())
		assertTrue('Z'.isAlpha())
	}

	@Test
	fun testIsAlpha_nonLetters() {
		assertFalse('0'.isAlpha())
		assertFalse('9'.isAlpha())
		assertFalse('@'.isAlpha())
		assertFalse('['.isAlpha())
		assertFalse('`'.isAlpha())
		assertFalse('{'.isAlpha())
		assertFalse(' '.isAlpha())
		assertFalse('-'.isAlpha())
	}

	@Test
	fun testIsAlphaNumeric_letters() {
		assertTrue('a'.isAlphaNumeric())
		assertTrue('z'.isAlphaNumeric())
		assertTrue('A'.isAlphaNumeric())
		assertTrue('Z'.isAlphaNumeric())
	}

	@Test
	fun testIsAlphaNumeric_digits() {
		assertTrue('0'.isAlphaNumeric())
		assertTrue('5'.isAlphaNumeric())
		assertTrue('9'.isAlphaNumeric())
	}

	@Test
	fun testIsAlphaNumeric_specialChars() {
		assertFalse('@'.isAlphaNumeric())
		assertFalse('['.isAlphaNumeric())
		assertFalse('`'.isAlphaNumeric())
		assertFalse('{'.isAlphaNumeric())
		assertFalse('/'.isAlphaNumeric())
		assertFalse(':'.isAlphaNumeric())
		assertFalse(' '.isAlphaNumeric())
		assertFalse('-'.isAlphaNumeric())
	}

	@Test
	fun testIsLowerCase_lowercaseLetters() {
		assertTrue('a'.isLowerCase())
		assertTrue('m'.isLowerCase())
		assertTrue('z'.isLowerCase())
	}

	@Test
	fun testIsLowerCase_nonLowercase() {
		assertFalse('A'.isLowerCase())
		assertFalse('Z'.isLowerCase())
		assertFalse('0'.isLowerCase())
		assertFalse('@'.isLowerCase())
		assertFalse('`'.isLowerCase())
		assertFalse('{'.isLowerCase())
	}

	@Test
	fun testIsUpperCase_uppercaseLetters() {
		assertTrue('A'.isUpperCase())
		assertTrue('M'.isUpperCase())
		assertTrue('Z'.isUpperCase())
	}

	@Test
	fun testIsUpperCase_nonUppercase() {
		assertFalse('a'.isUpperCase())
		assertFalse('z'.isUpperCase())
		assertFalse('0'.isUpperCase())
		assertFalse('@'.isUpperCase())
		assertFalse('['.isUpperCase())
	}

	@Test
	fun testIsNumeric_digits() {
		assertTrue('0'.isNumeric())
		assertTrue('5'.isNumeric())
		assertTrue('9'.isNumeric())
	}

	@Test
	fun testIsNumeric_nonDigits() {
		assertFalse('/'.isNumeric())
		assertFalse(':'.isNumeric())
		assertFalse('a'.isNumeric())
		assertFalse('A'.isNumeric())
		assertFalse(' '.isNumeric())
	}

	@Test
	fun testLowercaseChar_uppercaseToLowercase() {
		assertEquals(expected = 'a', actual = 'A'.lowercaseChar())
		assertEquals(expected = 'm', actual = 'M'.lowercaseChar())
		assertEquals(expected = 'z', actual = 'Z'.lowercaseChar())
	}

	@Test
	fun testLowercaseChar_alreadyLowercase() {
		assertEquals(expected = 'a', actual = 'a'.lowercaseChar())
		assertEquals(expected = 'z', actual = 'z'.lowercaseChar())
	}

	@Test
	fun testLowercaseChar_nonLetters() {
		assertEquals(expected = '0', actual = '0'.lowercaseChar())
		assertEquals(expected = '9', actual = '9'.lowercaseChar())
		assertEquals(expected = '-', actual = '-'.lowercaseChar())
		assertEquals(expected = ' ', actual = ' '.lowercaseChar())
	}

	@Test
	fun testUppercaseChar_lowercaseToUppercase() {
		assertEquals(expected = 'A', actual = 'a'.uppercaseChar())
		assertEquals(expected = 'M', actual = 'm'.uppercaseChar())
		assertEquals(expected = 'Z', actual = 'z'.uppercaseChar())
	}

	@Test
	fun testUppercaseChar_alreadyUppercase() {
		assertEquals(expected = 'A', actual = 'A'.uppercaseChar())
		assertEquals(expected = 'Z', actual = 'Z'.uppercaseChar())
	}

	@Test
	fun testUppercaseChar_nonLetters() {
		assertEquals(expected = '0', actual = '0'.uppercaseChar())
		assertEquals(expected = '9', actual = '9'.uppercaseChar())
		assertEquals(expected = '-', actual = '-'.uppercaseChar())
		assertEquals(expected = ' ', actual = ' '.uppercaseChar())
	}
}
