package io.fluidsonic.locale


internal typealias PlatformLocale = Intl.Locale


public fun PlatformLocale.toCommon(): Locale =
	Locale(platform = this)


public fun Locale.toPlatform(): PlatformLocale =
	platform as PlatformLocale


internal actual fun parseLocaleOrNull(tag: String): Locale? =
	runCatching { Intl.Locale(tag) }.getOrNull()?.toCommon()


internal actual fun serializeLocale(locale: Locale): String =
	locale.toPlatform().toString()
