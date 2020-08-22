package io.fluidsonic.locale


public class Locale internal constructor(
	public val tag: LanguageTag
) {

	init {
		freeze()
	}


	override fun equals(other: Any?): Boolean =
		this === other || (other is Locale && tag == other.tag)


	override fun hashCode(): Int =
		tag.hashCode()


	override fun toString(): String =
		tag.toString()


	public companion object {


		public fun forTag(tag: LanguageTag): Locale =
			forTagOrNull(tag) ?: error("Invalid ISO 4217 currency code: $tag")


		public fun forTagOrNull(tag: LanguageTag): Locale? =
			localeForLanguageTagOrNull(tag)


		public fun forTag(tag: String): Locale =
			forTagOrNull(LanguageTag.parse(tag)) ?: error("Invalid BCP 47 language tag: $tag")


		public fun forTagOrNull(tag: String): Locale? =
			LanguageTag.parseOrNull(tag)?.let(::forTagOrNull)
	}
}


internal expect fun languageTagForLocale(locale: Locale): LanguageTag
internal expect fun localeForLanguageTagOrNull(tag: LanguageTag): Locale?
