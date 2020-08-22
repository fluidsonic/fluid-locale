package io.fluidsonic.locale


internal actual fun parseLanguageTagOrNull(string: String): LanguageTag? =
	runCatching { PlatformLocale(string) }.getOrNull()?.toString()?.let(::LanguageTag)
