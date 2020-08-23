package io.fluidsonic.locale


internal class HierarchicalLocalizedValueResolver<in Key : Any, out Value : Any>(
	private val delegate: LocalizedValueResolver<Key, Value>
) : LocalizedValueResolver<Key, Value> {

	override fun resolve(key: Key, language: String?, script: String?, region: String?, variants: List<String>): Value? {
		delegate.resolve(key = key, language = language, script = script, region = region, variants = variants)?.let { return it }

		if (variants.isNotEmpty()) {
			var remainingVariants: List<String> = variants
			do {
				remainingVariants = remainingVariants.dropLast(1)

				delegate.resolve(key = key, language = language, script = script, region = region, variants = remainingVariants)?.let { return it }
			}
			while (remainingVariants.isNotEmpty())
		}

		if (region != null)
			delegate.resolve(key = key, language = language, script = script)?.let { return it }

		if (script != null) {
			delegate.resolve(key = key, language = language, region = region, variants = variants)?.let { return it }

			if (variants.isNotEmpty()) {
				var remainingVariants: List<String> = variants
				do {
					remainingVariants = remainingVariants.dropLast(1)

					delegate.resolve(key = key, language = language, region = region, variants = remainingVariants)?.let { return it }
				}
				while (remainingVariants.isNotEmpty())
			}

			delegate.resolve(key = key, language = language)?.let { return it }
		}

		if (language != null)
			delegate.resolve(key = key, language = null)?.let { return it }

		return null
	}
}
