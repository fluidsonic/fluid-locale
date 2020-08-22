package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LocaleTests {

	@Test
	fun testEqualsAndHashCode() {
		assertEquals(expected = Locale.forTag("en-US"), actual = Locale.forTag("en-us"))
		assertEquals(expected = Locale.forTag("en-US").hashCode(), actual = Locale.forTag("en-us").hashCode())
	}


	@Test
	fun testForTag() {
		assertEquals(expected = "en-US", actual = Locale.forTag(LanguageTag.parse("en-US")).tag.toString())
		// FIXME Implement own consistent parsing and test negative cases.
	}


	@Test
	fun testForTagString() {
		assertEquals(expected = "en-US", actual = Locale.forTag("en-US").tag.toString())
		assertEquals(expected = "en-US", actual = Locale.forTag("en-us").tag.toString())
		// FIXME Implement own consistent parsing and test negative cases.
	}


	@Test
	fun testProperties() {
		val enUsTag = LanguageTag.parse("en-US")
		val enUs = Locale.forTag(enUsTag)

		assertEquals(expected = enUsTag, actual = enUs.tag)
	}


	@Test
	fun testToString() {
		assertEquals(expected = "en-US", actual = Locale.forTag("en-us").toString())
	}
}
