package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class HierarchicalLocalizedObjectResolverTests {

	@Test
	fun testFirst() {
		val tracker = TrackingValueResolver(value = Unit)
		val resolver = tracker.asHierarchical()
		val locale = Locale.forLanguage(language = "en", script = "Scpt", region = "US", variants = listOf("Vari1", "Vari2"))

		resolver.resolve(key = Unit, locale = locale)

		assertEquals(
			expected = listOf(
				Locale.forLanguage(language = "en", script = "Scpt", region = "US", variants = listOf("Vari1", "Vari2"))
			),
			actual = tracker.requestedLocales
		)
	}


	@Test
	fun testFull() {
		val tracker = TrackingValueResolver()
		val resolver = tracker.asHierarchical()
		val locale = Locale.forLanguage(language = "en", script = "Scpt", region = "US", variants = listOf("Vari1", "Vari2"))

		resolver.resolve(key = Unit, locale = locale)

		assertEquals(
			expected = listOf(
				Locale.forLanguage(language = "en", script = "Scpt", region = "US", variants = listOf("Vari1", "Vari2")),
				Locale.forLanguage(language = "en", script = "Scpt", region = "US", variants = listOf("Vari1")),
				Locale.forLanguage(language = "en", script = "Scpt", region = "US"),
				Locale.forLanguage(language = "en", script = "Scpt"),
				Locale.forLanguage(language = "en", script = null, region = "US", variants = listOf("Vari1", "Vari2")),
				Locale.forLanguage(language = "en", script = null, region = "US", variants = listOf("Vari1")),
				Locale.forLanguage(language = "en", script = null, region = "US"),
				Locale.forLanguage(language = "en"),
				Locale.root,
			),
			actual = tracker.requestedLocales
		)
	}


	@Test
	fun testRoot() {
		val tracker = TrackingValueResolver()
		val resolver = tracker.asHierarchical()
		val locale = Locale.root

		resolver.resolve(key = Unit, locale = locale)

		assertEquals(
			expected = listOf(
				Locale.root,
			),
			actual = tracker.requestedLocales
		)
	}


	@Test
	fun testWithoutLanguage() {
		val tracker = TrackingValueResolver()
		val resolver = tracker.asHierarchical()
		val locale = Locale.forLanguage(language = null, script = "Scpt", region = "US", variants = listOf("Vari1", "Vari2"))

		resolver.resolve(key = Unit, locale = locale)

		assertEquals(
			expected = listOf(
				Locale.forLanguage(language = null, script = "Scpt", region = "US", variants = listOf("Vari1", "Vari2")),
				Locale.forLanguage(language = null, script = "Scpt", region = "US", variants = listOf("Vari1")),
				Locale.forLanguage(language = null, script = "Scpt", region = "US"),
				Locale.forLanguage(language = null, script = "Scpt"),
				Locale.forLanguage(language = null, script = null, region = "US", variants = listOf("Vari1", "Vari2")),
				Locale.forLanguage(language = null, script = null, region = "US", variants = listOf("Vari1")),
				Locale.forLanguage(language = null, script = null, region = "US"),
				Locale.root,
			),
			actual = tracker.requestedLocales
		)
	}


	@Test
	fun testWithoutRegion() {
		val tracker = TrackingValueResolver()
		val resolver = tracker.asHierarchical()
		val locale = Locale.forLanguage(language = "en", script = "Scpt", region = null, variants = listOf("Vari1", "Vari2"))

		resolver.resolve(key = Unit, locale = locale)

		assertEquals(
			expected = listOf(
				Locale.forLanguage(language = "en", script = "Scpt", variants = listOf("Vari1", "Vari2")),
				Locale.forLanguage(language = "en", script = "Scpt", variants = listOf("Vari1")),
				Locale.forLanguage(language = "en", script = "Scpt"),
				Locale.forLanguage(language = "en", script = null, variants = listOf("Vari1", "Vari2")),
				Locale.forLanguage(language = "en", script = null, variants = listOf("Vari1")),
				Locale.forLanguage(language = "en", script = null),
				Locale.forLanguage(language = "en"),
				Locale.root,
			),
			actual = tracker.requestedLocales
		)
	}


	@Test
	fun testWithoutScript() {
		val tracker = TrackingValueResolver()
		val resolver = tracker.asHierarchical()
		val locale = Locale.forLanguage(language = "en", script = null, region = "US", variants = listOf("Vari1", "Vari2"))

		resolver.resolve(key = Unit, locale = locale)

		assertEquals(
			expected = listOf(
				Locale.forLanguage(language = "en", region = "US", variants = listOf("Vari1", "Vari2")),
				Locale.forLanguage(language = "en", region = "US", variants = listOf("Vari1")),
				Locale.forLanguage(language = "en", region = "US"),
				Locale.forLanguage(language = "en"),
				Locale.root,
			),
			actual = tracker.requestedLocales
		)
	}


	@Test
	fun testWithoutVariants() {
		val tracker = TrackingValueResolver()
		val resolver = tracker.asHierarchical()
		val locale = Locale.forLanguage(language = "en", script = "Scpt", region = "US")

		resolver.resolve(key = Unit, locale = locale)

		assertEquals(
			expected = listOf(
				Locale.forLanguage(language = "en", script = "Scpt", region = "US"),
				Locale.forLanguage(language = "en", script = "Scpt"),
				Locale.forLanguage(language = "en", script = null, region = "US"),
				Locale.forLanguage(language = "en"),
				Locale.root,
			),
			actual = tracker.requestedLocales
		)
	}


	private class TrackingValueResolver(
		private val value: Unit? = null,
	) : LocalizedValueResolver<Unit, Unit> {

		val requestedLocales = mutableListOf<Locale>()


		override fun resolve(key: Unit, language: String?, script: String?, region: String?, variants: List<String>): Unit? {
			requestedLocales += Locale.forLanguage(language = language, script = script, region = region, variants = variants)

			return value
		}
	}
}
