package io.fluidsonic.locale


internal typealias PlatformLocale = Intl.Locale


/** Converts a JS [Intl.Locale] to a [Locale]. */
public fun PlatformLocale.toCommon(): Locale =
	Locale.forLanguageTag(toString())


/** Converts a [Locale] to a JS [Intl.Locale]. */
public fun Locale.toPlatform(): PlatformLocale =
	PlatformLocale(toLanguageTag().toString())
