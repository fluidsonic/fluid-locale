package io.fluidsonic.locale


/**
 * A [LocalizedValueResolver] wrapper that implements hierarchical locale fallback.
 *
 * Resolution order for a tag like `zh-Hant-TW-variant`:
 * 1. Exact match: `zh-Hant-TW-variant`
 * 2. Drop variants one at a time from the end
 * 3. Drop region: `zh-Hant`
 * 4. Drop script, retry with region + variants: `zh-TW-variant`, then drop variants, then `zh`
 * 5. Drop language: try `null` language (language-independent fallback)
 */
internal class HierarchicalLocalizedValueResolver<in Key : Any, out Value : Any>(
	private val delegate: LocalizedValueResolver<Key, Value>
) : LocalizedValueResolver<Key, Value> {

	override fun resolve(key: Key, language: String?, script: String?, region: String?, variants: List<String>): Value? {
		delegate.resolve(key = key, language = language, script = script, region = region, variants = variants)?.let { return it }

		if (variants.isNotEmpty())
			resolveWithVariantFallback(key = key, language = language, script = script, region = region, variants = variants)?.let { return it }

		if (region != null)
			delegate.resolve(key = key, language = language, script = script)?.let { return it }

		if (script != null) {
			delegate.resolve(key = key, language = language, region = region, variants = variants)?.let { return it }

			if (variants.isNotEmpty())
				resolveWithVariantFallback(key = key, language = language, script = null, region = region, variants = variants)?.let { return it }

			delegate.resolve(key = key, language = language)?.let { return it }
		}

		if (language != null)
			delegate.resolve(key = key, language = null)?.let { return it }

		return null
	}


	private fun resolveWithVariantFallback(key: Key, language: String?, script: String?, region: String?, variants: List<String>): Value? {
		var remainingVariants = variants
		do {
			remainingVariants = remainingVariants.dropLast(1)

			delegate.resolve(key = key, language = language, script = script, region = region, variants = remainingVariants)?.let { return it }
		}
		while (remainingVariants.isNotEmpty())

		return null
	}
}
