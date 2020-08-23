package io.fluidsonic.locale


internal fun <T : Any> Iterator<T>.nextOrNull() =
	if (hasNext()) next() else null
