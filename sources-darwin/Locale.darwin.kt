package io.fluidsonic.locale

import platform.Foundation.*


internal typealias PlatformLocale = NSLocale


public fun PlatformLocale.toCommon(): Locale {
	val tag = localeIdentifier.replace('_', '-')

	return Locale.forLanguageTag(when {
		tag.isEmpty() -> LanguageTag.undeterminedPrefix
		tag.startsWith("-") -> "${LanguageTag.undeterminedPrefix}$tag"
		else -> tag
	})
}


public fun Locale.toPlatform(): PlatformLocale {
	val tag = toLanguageTag().toString()

	return PlatformLocale(when {
		tag == LanguageTag.undeterminedPrefix -> ""
		tag.startsWith("${LanguageTag.undeterminedPrefix}-") -> tag.removePrefix(LanguageTag.undeterminedPrefix)
		else -> tag
	})
}
