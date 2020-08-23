package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LocalizedObjectResolverTests {

	@Test
	fun testMap() {
		val resolver = LocalizedValueResolver.buildMap {
			put(key = "foo", value = "Foo", language = null)
			put(key = "foo", value = "Foo L", language = "L")
			put(key = "foo", value = "Foo L-S", language = "L", script = "S")
			put(key = "foo", value = "Foo L-S-R", language = "L", script = "S", region = "R")
			put(key = "foo", value = "Foo L-S-R-V1", language = "L", script = "S", region = "R", variants = listOf("V1"))
			put(key = "foo", value = "Foo L-S-R-V1-V2", language = "L", script = "S", region = "R", variants = listOf("V1", "V2"))
		}

		assertEquals(
			expected = "Foo",
			actual = resolver.resolve(key = "foo", language = null)
		)
		assertNull(resolver.resolve(key = "foo", language = null, script = "S"))
		assertNull(resolver.resolve(key = "foo", language = "L2"))
		assertNull(resolver.resolve(key = "foo", language = "L2", script = "S"))

		assertEquals(
			expected = "Foo L",
			actual = resolver.resolve(key = "foo", language = "L")
		)
		assertNull(resolver.resolve(key = "foo", language = "L", script = "S2"))
		assertNull(resolver.resolve(key = "foo", language = "L", script = "S2", region = "R"))

		assertEquals(
			expected = "Foo L-S",
			actual = resolver.resolve(key = "foo", language = "L", script = "S")
		)
		assertNull(resolver.resolve(key = "foo", language = "L", script = "S", region = "R2"))
		assertNull(resolver.resolve(key = "foo", language = "L", script = "S", region = "R2", variants = listOf("V1")))

		assertEquals(
			expected = "Foo L-S-R",
			actual = resolver.resolve(key = "foo", language = "L", script = "S", region = "R")
		)
		assertNull(resolver.resolve(key = "foo", language = "L", script = "S", region = "R", variants = listOf("V3")))
		assertNull(resolver.resolve(key = "foo", language = "L", script = "S", region = "R", variants = listOf("V1", "V3")))

		assertEquals(
			expected = "Foo L-S-R-V1",
			actual = resolver.resolve(key = "foo", language = "L", script = "S", region = "R", variants = listOf("V1"))
		)

		assertEquals(
			expected = "Foo L-S-R-V1-V2",
			actual = resolver.resolve(key = "foo", language = "L", script = "S", region = "R", variants = listOf("V1", "V2"))
		)
		assertNull(resolver.resolve(key = "foo", language = "L", script = "S", region = "R", variants = listOf("V2", "V1")))

		assertNull(resolver.resolve(key = "bar", language = null))
	}
}
