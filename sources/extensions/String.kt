package io.fluidsonic.locale

// ASCII-only string utilities for BCP 47 language tag processing. Avoids stdlib's Unicode/locale overhead.

internal fun String.isAlpha() =
	all(Char::isAlpha)


internal fun String.isAlphaNumeric() =
	all(Char::isAlphaNumeric)


internal fun String.isNumeric() =
	all(Char::isNumeric)


internal inline fun String.mapCharacters(transform: (character: Char) -> Char): String {
	val characters = toCharArray()
	characters.forEachIndexed { index, character ->
		characters[index] = transform(character)
	}

	return characters.concatToString()
}


internal inline fun String.mapCharactersIndexed(transform: (index: Int, character: Char) -> Char): String {
	val characters = toCharArray()
	characters.forEachIndexed { index, character ->
		characters[index] = transform(index, character)
	}

	return characters.concatToString()
}


internal fun String.lowercase(): String {
	if (all(Char::isLowerCase))
		return this

	return mapCharacters(Char::lowercaseChar)
}


internal fun String.uppercase(): String {
	if (all(Char::isUpperCase))
		return this

	return mapCharacters(Char::uppercaseChar)
}


internal fun String.toUppercaseFirstLowercaseRest(): String {
	var needsConversion = false

	forEachIndexed { index, character ->
		when (index) {
			0 -> if (character in 'a' .. 'z') needsConversion = true
			else -> if (character in 'A' .. 'Z') needsConversion = true
		}
	}

	if (!needsConversion)
		return this

	return mapCharactersIndexed { index, character ->
		when (index) {
			0 -> character.uppercaseChar()
			else -> character.lowercaseChar()
		}
	}
}
