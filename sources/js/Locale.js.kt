package io.fluidsonic.locale


internal typealias PlatformLocale = Intl.Locale


public fun PlatformLocale.toCommon(): Locale =
	Locale.forLanguageTag(toString())


public fun Locale.toPlatform(): PlatformLocale =
	PlatformLocale(toLanguageTag().toString())
