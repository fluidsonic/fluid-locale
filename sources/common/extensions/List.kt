package io.fluidsonic.locale


internal fun List<String>.equals(other: List<String>, ignoreCase: Boolean): Boolean {
	if (size != other.size)
		return false

	forEachIndexed { index, element ->
		if (!element.equals(other[index], ignoreCase = ignoreCase))
			return false
	}

	return true
}
