package io.fluidsonic.locale


internal typealias PlatformLocale = java.util.Locale


/** Converts a [java.util.Locale] to a [Locale]. */
public fun PlatformLocale.toCommon(): Locale =
	Locale.forLanguageTag(toLanguageTag())


/** Converts a [Locale] to a [java.util.Locale]. */
public fun Locale.toPlatform(): PlatformLocale =
	PlatformLocale.forLanguageTag(toLanguageTag().toString())
