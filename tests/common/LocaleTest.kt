package tests

import io.fluidsonic.locale.*
import kotlin.test.*
import kotlinx.serialization.json.*


class LocaleTest {

	@Test
	fun testParseAndSerialize() {
		val actual = Locale.parseOrNull("en-us")?.toString()
		val expected = "en-US"

		assertEquals(expected = expected, actual = actual)
	}


	@Test
	fun testSerializer() {
		assertEquals(expected = "en-US", actual = Json.decodeFromString(Locale.serializer(), "\"en-us\"").toString())
		assertEquals(expected = "\"en-US\"", actual = Json.encodeToString(Locale.serializer(), Locale.parseOrNull("en-us")!!))
	}
}
