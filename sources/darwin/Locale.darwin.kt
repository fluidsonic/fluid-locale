package io.fluidsonic.locale

import platform.Foundation.*


internal typealias PlatformLocale = NSLocale


internal actual fun localeForLanguageTagOrNull(tag: LanguageTag): Locale? =
	PlatformLocale(tag.toString()).toCommon()


internal actual fun languageTagForLocale(locale: Locale): LanguageTag =
	LanguageTag(locale.toPlatform().localeIdentifier)


public fun PlatformLocale.toCommon(): Locale =
	Locale(tag = LanguageTag.parse(localeIdentifier))


public fun Locale.toPlatform(): PlatformLocale =
	PlatformLocale(tag.toString())
