package io.fluidsonic.locale


/**
 * A BCP 47 language tag, for example `en`, `en-US` or `sl-IT-nedis`.
 *
 * References:
 * - [https://tools.ietf.org/html/bcp47]
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "UNUSED_PARAMETER")
public inline class LanguageTag @PublishedApi internal constructor(private val value: String) {

	override fun toString(): String =
		value


	public companion object {

		public fun parse(string: String): LanguageTag =
			parseOrNull(string) ?: error("Invalid BCP 47 language tag format: $string")


		public fun parseOrNull(string: String): LanguageTag? =
			parseLanguageTagOrNull(string)
	}
}


internal expect fun parseLanguageTagOrNull(string: String): LanguageTag?
