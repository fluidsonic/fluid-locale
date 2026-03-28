package io.fluidsonic.locale

// ASCII-only character utilities for BCP 47 language tag processing. Avoids stdlib's Unicode/locale overhead.

internal fun Char.isAlpha() =
	this in 'a' .. 'z' || this in 'A' .. 'Z'


internal fun Char.isAlphaNumeric() =
	isAlpha() || isNumeric()


internal fun Char.isLowerCase() =
	this in 'a' .. 'z'


internal fun Char.isNumeric() =
	this in '0' .. '9'


internal fun Char.isUpperCase() =
	this in 'A' .. 'Z'


internal fun Char.lowercaseChar() =
	when {
		isUpperCase() -> this + 0x20
		else -> this
	}


internal fun Char.uppercaseChar() =
	when {
		isLowerCase() -> this - 0x20
		else -> this
	}
