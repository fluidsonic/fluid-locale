package io.fluidsonic.locale

import platform.Foundation.*


internal actual fun parseLanguageTagOrNull(string: String): LanguageTag? =
	LanguageTag(PlatformLocale(string).localeIdentifier)
