package io.fluidsonic.locale


internal typealias PlatformLocale = java.util.Locale


public fun PlatformLocale.toCommon(): Locale =
	Locale(platform = this)


public fun Locale.toPlatform(): PlatformLocale =
	platform as PlatformLocale


internal actual fun parseLocaleOrNull(tag: String): Locale? =
	runCatching { java.util.Locale.Builder().setLanguageTag(tag).build() }.getOrNull()?.toCommon()


internal actual fun serializeLocale(locale: Locale): String =
	locale.toPlatform().toLanguageTag()
