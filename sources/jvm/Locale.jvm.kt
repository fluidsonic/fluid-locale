package io.fluidsonic.locale


internal typealias PlatformLocale = java.util.Locale


public fun PlatformLocale.toCommon(): Locale =
	Locale.forLanguageTag(toLanguageTag())


public fun Locale.toPlatform(): PlatformLocale =
	PlatformLocale.forLanguageTag(toLanguageTag().toString())
