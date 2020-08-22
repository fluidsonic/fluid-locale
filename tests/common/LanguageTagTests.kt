package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LanguageTagTests {

	@Test
	fun testEqualsAndHashCode() {
		assertEquals(expected = LanguageTag.parse("en-US"), actual = LanguageTag.parse("en-US"))
		assertEquals(expected = LanguageTag.parse("en-US").hashCode(), actual = LanguageTag.parse("en-US").hashCode())
	}


	@Test
	fun testParseAndToString() {
		assertEquals(expected = "en-US", actual = LanguageTag.parse("en-US").toString())
		assertEquals(expected = "en-US", actual = LanguageTag.parse("en-us").toString())

		// FIXME Implement own consistent parsing and test negative cases.
	}
}
