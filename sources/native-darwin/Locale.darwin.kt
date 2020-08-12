package io.fluidsonic.locale

import platform.Foundation.localeIdentifier


internal typealias PlatformLocale = platform.Foundation.NSLocale


public fun PlatformLocale.toCommon(): Locale =
	Locale(platform = this)


public fun Locale.toPlatform(): PlatformLocale =
	platform as PlatformLocale


internal actual fun parseLocaleOrNull(tag: String): Locale? =
	platform.Foundation.NSLocale(tag).toCommon()


internal actual fun serializeLocale(locale: Locale): String =
	locale.toPlatform().localeIdentifier
