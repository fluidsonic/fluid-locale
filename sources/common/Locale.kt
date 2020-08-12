package io.fluidsonic.locale


public class Locale internal constructor(
	internal val platform: Any
) {

	init {
		freeze()
	}


	override fun equals(other: Any?): Boolean =
		this === other || (other is Locale && platform == other.platform)


	override fun hashCode(): Int =
		platform.hashCode()


	override fun toString(): String =
		serializeLocale(this)


	public companion object {

		public fun parseOrNull(tag: String): Locale? =
			parseLocaleOrNull(tag)
	}
}


internal expect fun parseLocaleOrNull(tag: String): Locale?
internal expect fun serializeLocale(locale: Locale): String
