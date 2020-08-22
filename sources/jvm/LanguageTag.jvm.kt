package io.fluidsonic.locale


internal actual fun parseLanguageTagOrNull(string: String): LanguageTag? =
	runCatching { java.util.Locale.Builder().setLanguageTag(string).build() }.getOrNull()?.toLanguageTag()?.let(::LanguageTag)
