package io.fluidsonic.locale


internal typealias PlatformLocale = java.util.Locale


internal actual fun languageTagForLocale(locale: Locale): LanguageTag =
	LanguageTag(locale.toPlatform().toLanguageTag())


internal actual fun localeForLanguageTagOrNull(tag: LanguageTag): Locale? =
	runCatching { java.util.Locale.Builder().setLanguageTag(tag.toString()).build() }.getOrNull()?.toCommon()


public fun PlatformLocale.toCommon(): Locale =
	Locale(tag = LanguageTag.parse(toLanguageTag()))


public fun Locale.toPlatform(): PlatformLocale =
	PlatformLocale.forLanguageTag(tag.toString())
