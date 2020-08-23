package io.fluidsonic.locale


public interface LocalizedValueResolver<in Key : Any, out Value : Any> {

	public fun resolve(
		key: Key,
		language: String?,
		script: String? = null,
		region: String? = null,
		variants: List<String> = emptyList(),
	): Value?


	public companion object {

		public inline fun <Key : Any, Value : Any> buildMap(
			@BuilderInference builderAction: MapBuilder<Key, Value>.() -> Unit,
		): LocalizedValueResolver<Key, Value> =
			MapBuilder<Key, Value>().apply(builderAction).build()
	}


	private class LanguageMap<Key, Value>(
		private val regionMaps: Map<String?, RegionMap<Key, Value>>?,
		private val scriptMaps: Map<String?, ScriptMap<Key, Value>>?,
		private val values: Map<Key, Value>?,
		private val variantMaps: Map<List<String>, Map<Key, Value>>?,
	) {

		fun resolve(key: Key, script: String?, region: String?, variants: List<String>): Value? =
			when {
				script != null -> scriptMaps?.get(script)?.resolve(key = key, region = region, variants = variants)
				region != null -> regionMaps?.get(region)?.resolve(key = key, variants = variants)
				variants.isNotEmpty() -> variantMaps?.get(variants)?.get(key)
				else -> values?.get(key)
			}
	}


	public class MapBuilder<Key : Any, Value : Any> {

		private var languageMaps: MutableMap<String?, MutableLanguageMap>? = null


		public fun build(): LocalizedValueResolver<Key, Value> =
			MapResolver(languageMaps = languageMaps?.mapValues { it.value.toImmutable() })


		@Suppress("NAME_SHADOWING")
		public fun put(key: Key, value: Value, language: String?, script: String? = null, region: String? = null, variants: List<String> = emptyList()) {
			val language = LanguageTag.canonicalizeLanguage(language)
			val script = LanguageTag.canonicalizeScript(script)
			val region = LanguageTag.canonicalizeRegion(region)
			val variants = LanguageTag.canonicalizeVariants(variants)

			(languageMaps ?: hashMapOf<String?, MutableLanguageMap>().also { languageMaps = it })
				.getOrPut(language, ::MutableLanguageMap).put(key = key, value = value, script = script, region = region, variants = variants)
		}


		private inner class MutableLanguageMap {

			private var regionMaps: MutableMap<String?, MutableRegionMap>? = null
			private var scriptMaps: MutableMap<String?, MutableScriptMap>? = null
			private var values: MutableMap<Key, Value>? = null
			private var variantMaps: MutableMap<List<String>, MutableMap<Key, Value>>? = null


			fun put(key: Key, value: Value, script: String?, region: String?, variants: List<String>) {
				when {
					script != null -> (scriptMaps ?: hashMapOf<String?, MutableScriptMap>().also { scriptMaps = it })
						.getOrPut(script, ::MutableScriptMap).put(key = key, value = value, region = region, variants = variants)

					region != null -> (regionMaps ?: hashMapOf<String?, MutableRegionMap>().also { regionMaps = it })
						.getOrPut(region, ::MutableRegionMap).put(key = key, value = value, variants = variants)

					variants.isNotEmpty() -> (variantMaps ?: hashMapOf<List<String>, MutableMap<Key, Value>>().also { variantMaps = it })
						.getOrPut(variants, ::hashMapOf)[key] = value

					else -> (values ?: hashMapOf<Key, Value>().also { values = it })[key] = value
				}
			}


			fun toImmutable() = LanguageMap(
				regionMaps = regionMaps?.mapValues { it.value.toImmutable() },
				scriptMaps = scriptMaps?.mapValues { it.value.toImmutable() },
				values = values?.toMap(hashMapOf()),
				variantMaps = variantMaps?.mapValues { it.value.toMap(hashMapOf()) }
			)
		}


		private inner class MutableRegionMap {

			private var values: MutableMap<Key, Value>? = null
			private var variantMaps: MutableMap<List<String>, MutableMap<Key, Value>>? = null


			fun put(key: Key, value: Value, variants: List<String>) {
				when {
					variants.isNotEmpty() -> (variantMaps ?: hashMapOf<List<String>, MutableMap<Key, Value>>().also { variantMaps = it })
						.getOrPut(variants, ::hashMapOf)[key] = value

					else -> (values ?: hashMapOf<Key, Value>().also { values = it })[key] = value
				}
			}


			fun toImmutable() = RegionMap(
				values = values?.toMap(hashMapOf()),
				variantMaps = variantMaps?.mapValues { it.value.toMap(hashMapOf()) }
			)
		}


		private inner class MutableScriptMap {

			private var regionMaps: MutableMap<String?, MutableRegionMap>? = null
			private var values: MutableMap<Key, Value>? = null
			private var variantMaps: MutableMap<List<String>, MutableMap<Key, Value>>? = null


			fun put(key: Key, value: Value, region: String?, variants: List<String>) {
				when {
					region != null -> (regionMaps ?: hashMapOf<String?, MutableRegionMap>().also { regionMaps = it })
						.getOrPut(region, ::MutableRegionMap).put(key = key, value = value, variants = variants)

					variants.isNotEmpty() -> (variantMaps ?: hashMapOf<List<String>, MutableMap<Key, Value>>().also { variantMaps = it })
						.getOrPut(variants, ::hashMapOf)[key] = value

					else -> (values ?: hashMapOf<Key, Value>().also { values = it })[key] = value
				}
			}


			fun toImmutable() = ScriptMap(
				regionMaps = regionMaps?.mapValues { it.value.toImmutable() },
				values = values?.toMap(hashMapOf()),
				variantMaps = variantMaps?.mapValues { it.value.toMap(hashMapOf()) }
			)
		}
	}


	private class MapResolver<in Key : Any, out Value : Any>(
		private val languageMaps: Map<String?, LanguageMap<Key, Value>>?,
	) : LocalizedValueResolver<Key, Value> {

		init {
			freeze()
		}


		@Suppress("NAME_SHADOWING")
		override fun resolve(key: Key, language: String?, script: String?, region: String?, variants: List<String>): Value? {
			val language = LanguageTag.canonicalizeLanguage(language)
			val script = LanguageTag.canonicalizeScript(script)
			val region = LanguageTag.canonicalizeRegion(region)
			val variants = LanguageTag.canonicalizeVariants(variants)

			return languageMaps?.get(language)?.resolve(key = key, script = script, region = region, variants = variants)
		}
	}


	private class RegionMap<Key, Value>(
		private val values: Map<Key, Value>?,
		private val variantMaps: Map<List<String>, Map<Key, Value>>?,
	) {

		fun resolve(key: Key, variants: List<String>): Value? =
			when {
				variants.isNotEmpty() -> variantMaps?.get(variants)?.get(key)
				else -> values?.get(key)
			}
	}


	private class ScriptMap<Key, Value>(
		private val regionMaps: Map<String?, RegionMap<Key, Value>>?,
		private val values: Map<Key, Value>?,
		private val variantMaps: Map<List<String>, Map<Key, Value>>?,
	) {

		fun resolve(key: Key, region: String?, variants: List<String>): Value? =
			when {
				region != null -> regionMaps?.get(region)?.resolve(key = key, variants = variants)
				variants.isNotEmpty() -> variantMaps?.get(variants)?.get(key)
				else -> values?.get(key)
			}
	}
}


public fun <Key : Any, Value : Any> LocalizedValueResolver<Key, Value>.asHierarchical(): LocalizedValueResolver<Key, Value> =
	when (this) {
		is HierarchicalLocalizedValueResolver<Key, Value> -> this
		else -> HierarchicalLocalizedValueResolver(delegate = this)
	}


public fun <Key : Any, Value : Any> LocalizedValueResolver<Key, Value>.resolve(key: Key, locale: Locale): Value? =
	resolve(key = key, language = locale.language, script = locale.script, region = locale.region, variants = locale.variants)
