package io.fluidsonic.locale


internal typealias PlatformLocale = Intl.Locale


internal actual fun languageTagForLocale(locale: Locale): LanguageTag =
	LanguageTag(locale.toPlatform().toString())


internal actual fun localeForLanguageTagOrNull(tag: LanguageTag): Locale? =
	runCatching { PlatformLocale(tag.toString()) }.getOrNull()?.toCommon()


public fun PlatformLocale.toCommon(): Locale =
	Locale(tag = LanguageTag.parse(toString()))


public fun Locale.toPlatform(): PlatformLocale =
	PlatformLocale(tag.toString())
