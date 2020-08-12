package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LocaleTest {

	@Test
	fun testParseAndSerialize() {
		val actual = Locale.parseOrNull("en-us")?.toString()
		val expected = "en-US"

		assertEquals(expected = expected, actual = actual)
	}
}
