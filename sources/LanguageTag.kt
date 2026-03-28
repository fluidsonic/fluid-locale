package io.fluidsonic.locale


/**
 * A parsed BCP 47 language tag, for example `en`, `en-US` or `sl-IT-nedis`.
 *
 * Supports the full RFC 5646 grammar including language, script, region, variants, extensions, extlangs, and private-use subtags.
 * Grandfathered tags are normalized to their modern equivalents during parsing.
 *
 * Use [parse] or [forLanguage] to create instances.
 *
 * References:
 * - [RFC 5646](https://tools.ietf.org/html/rfc5646)
 * - [BCP 47](https://tools.ietf.org/html/bcp47)
 */
public class LanguageTag private constructor(
	/** Extension subtags, each as a singleton followed by its subtags (e.g. `u-co-phonebk`). Sorted and lowercased. */
	public val extensions: List<String>,
	/** Extended language subtags (ISO 639 codes). */
	public val extlangs: List<String>,
	/** Primary language subtag (ISO 639), or `null` for private-use-only tags. Lowercased. */
	public val language: String?,
	/** Private-use subtag sequence (e.g. `x-custom`), or `null` if absent. Lowercased. */
	public val privateuse: String?,
	/** Region subtag (ISO 3166-1 alpha-2 or UN M.49), or `null` if absent. Uppercased. */
	public val region: String?,
	/** Script subtag (ISO 15924), or `null` if absent. Title-cased. */
	public val script: String?,
	/** Variant subtags as defined in the IANA subtag registry. */
	public val variants: List<String>,
) {

	/** Creates a copy of this tag with the specified subtags replaced. */
	public fun copy(
		extensions: List<String> = this.extensions,
		extlangs: List<String> = this.extlangs,
		language: String? = this.language,
		privateuse: String? = this.privateuse,
		region: String? = this.region,
		script: String? = this.script,
		variants: List<String> = this.variants,
	): LanguageTag =
		when {
			language != null ->
				forLanguage(
					extensions = extensions,
					extlangs = extlangs,
					language = language,
					privateuse = privateuse,
					region = region,
					script = script,
					variants = variants
				)

			privateuse != null -> {
				require(language == null && script == null && region == null && variants.isEmpty() && extlangs.isEmpty() && extensions.isEmpty()) {
					"Cannot set language, script, region, variants, extlangs, or extensions on a private-use-only tag"
				}
				forPrivateUse(privateuse = privateuse)
			}

			else ->
				error("Either 'language' or 'privateuse' must be non-null.")
		}


	override fun equals(other: Any?): Boolean =
		this === other ||
			(other is LanguageTag &&
				extensions == other.extensions &&
				extlangs == other.extlangs &&
				language == other.language &&
				privateuse == other.privateuse &&
				region == other.region &&
				script == other.script &&
				variants.equals(other.variants, ignoreCase = true))


	override fun hashCode(): Int {
		var result = extensions.hashCode()
		result = 31 * result + extlangs.hashCode()
		result = 31 * result + language.hashCode()
		result = 31 * result + privateuse.hashCode()
		result = 31 * result + region.hashCode()
		result = 31 * result + script.hashCode()
		result = 31 * result + variants.map(String::lowercase).hashCode()

		return result
	}


	override fun toString(): String = buildString {
		if (language != null) {
			append(language)

			for (extlang in extlangs) {
				append(separator)
				append(extlang)
			}

			if (script != null) {
				append(separator)
				append(script)
			}

			if (region != null) {
				append(separator)
				append(region)
			}

			for (variant in variants) {
				append(separator)
				append(variant)
			}

			for (extension in extensions) {
				append(separator)
				append(extension)
			}
		}

		if (privateuse != null) {
			if (isNotEmpty())
				append(separator)

			append(privateuse)
		}
	}


	public companion object {

		/** The `x` prefix character for private-use subtags. */
		public const val privateusePrefix: Char = 'x'

		/** The `-` separator used between subtags. */
		public const val separator: Char = '-'

		/** The `und` prefix representing an undetermined language. */
		public const val undeterminedPrefix: String = "und"

		// https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry
		// grandfathered = irregular           ; non-redundant tags registered
		//               / regular             ; during the RFC 3066 era
		//
		// irregular     = "en-GB-oed"         ; irregular tags do not match
		//               / "i-ami"             ; the 'langtag' production and
		//               / "i-bnn"             ; would not otherwise be
		//               / "i-default"         ; considered 'well-formed'
		//               / "i-enochian"        ; These tags are all valid,
		//               / "i-hak"             ; but most are deprecated
		//               / "i-klingon"         ; in favor of more modern
		//               / "i-lux"             ; subtags or subtag
		//               / "i-mingo"           ; combination
		//               / "i-navajo"
		//               / "i-pwn"
		//               / "i-tao"
		//               / "i-tay"
		//               / "i-tsu"
		//               / "sgn-BE-FR"
		//               / "sgn-BE-NL"
		//               / "sgn-CH-DE"
		//
		// regular       = "art-lojban"        ; these tags match the 'langtag'
		//               / "cel-gaulish"       ; production, but their subtags
		//               / "no-bok"            ; are not extended language
		//               / "no-nyn"            ; or variant subtags: their meaning
		//               / "zh-guoyu"          ; is defined by their registration
		//               / "zh-hakka"          ; and all of these are deprecated
		//               / "zh-min"            ; in favor of a more modern
		//               / "zh-min-nan"        ; subtag or sequence of subtags
		//               / "zh-xiang"
		private val grandfathered: Map<String, String> = hashMapOf(
			"art-lojban" to "jbo",
			"cel-gaulish" to "xtg-x-cel-gaulish",
			"en-gb-oed" to "en-GB-oxendict",
			"i-ami" to "ami",
			"i-bnn" to "bnn",
			"i-default" to "en-x-i-default",
			"i-enochian" to "und-x-i-enochian",
			"i-hak" to "hak",
			"i-klingon" to "tlh",
			"i-lux" to "lb",
			"i-mingo" to "see-x-i-mingo",
			"i-navajo" to "nv",
			"i-pwn" to "pwn",
			"i-tao" to "tao",
			"i-tay" to "tay",
			"i-tsu" to "tsu",
			"no-bok" to "nb",
			"no-nyn" to "nn",
			"sgn-be-fr" to "sfb",
			"sgn-be-nl" to "vgt",
			"sgn-ch-de" to "sgg",
			"zh-guoyu" to "cmn",
			"zh-hakka" to "hak",
			"zh-min" to "nan-x-zh-min",
			"zh-min-nan" to "nan",
			"zh-xiang" to "hsn",
		)


		/** Lowercases an extension subtag, or returns `null` if blank. */
		public fun canonicalizeExtension(extension: String?): String? =
			extension?.ifEmpty { null }?.lowercase()


		/** Lowercases an extension singleton, or returns `null` if blank. */
		public fun canonicalizeExtensionSingleton(singleton: String?): String? =
			singleton?.ifEmpty { null }?.lowercase()


		/** Lowercases an extension subtag value, or returns `null` if blank. */
		public fun canonicalizeExtensionSubtag(subtag: String?): String? =
			subtag?.ifEmpty { null }?.lowercase()


		/** Canonicalizes and sorts a list of extension subtags. */
		public fun canonicalizeExtensions(extensions: List<String>): List<String> =
			extensions.ifEmpty { null }?.mapNotNull(::canonicalizeExtension)?.sorted().orEmpty()


		/** Lowercases an extlang subtag, or returns `null` if blank. */
		public fun canonicalizeExtlang(extlang: String?): String? =
			extlang?.ifEmpty { null }?.lowercase()


		/** Canonicalizes a list of extlang subtags. */
		public fun canonicalizeExtlangs(extlangs: List<String>): List<String> =
			extlangs.ifEmpty { null }?.mapNotNull(::canonicalizeExtlang).orEmpty()


		/** Lowercases a language subtag, or returns `null` if blank. */
		public fun canonicalizeLanguage(language: String?): String? =
			language?.ifEmpty { null }?.lowercase()


		/** Lowercases a private-use subtag sequence, or returns `null` if blank. */
		public fun canonicalizePrivateuse(privateuse: String?): String? =
			privateuse?.ifEmpty { null }?.lowercase()


		/** Lowercases a private-use prefix, or returns `null` if blank. */
		public fun canonicalizePrivateusePrefix(prefix: String?): String? =
			prefix?.ifEmpty { null }?.lowercase()


		/** Lowercases a private-use subtag, or returns `null` if blank. */
		public fun canonicalizePrivateuseSubtag(subtag: String?): String? =
			subtag?.ifEmpty { null }?.lowercase()


		/** Uppercases a region subtag, or returns `null` if blank. */
		public fun canonicalizeRegion(region: String?): String? =
			region?.ifEmpty { null }?.uppercase()


		/** Title-cases a script subtag (first letter upper, rest lower), or returns `null` if blank. */
		public fun canonicalizeScript(script: String?): String? =
			script?.ifEmpty { null }?.toUppercaseFirstLowercaseRest()


		/** Returns the variant subtag as-is (case-preserved per RFC 5646), or `null` if blank. */
		public fun canonicalizeVariant(variant: String?): String? =
			variant?.ifEmpty { null }


		/** Canonicalizes a list of variant subtags. */
		public fun canonicalizeVariants(variants: List<String>): List<String> =
			variants.ifEmpty { null }?.mapNotNull(::canonicalizeVariant).orEmpty()


		/**
		 * Creates a [LanguageTag] from individual subtag components. All subtags are canonicalized.
		 *
		 * @throws IllegalArgumentException if any subtag is ill-formed per BCP 47.
		 */
		public fun forLanguage(
			language: String,
			script: String? = null,
			region: String? = null,
			variants: List<String> = emptyList(),
			extlangs: List<String> = emptyList(),
			extensions: List<String> = emptyList(),
			privateuse: String? = null,
		): LanguageTag =
			forLanguageInternal(
				language = language,
				script = script,
				region = region,
				variants = variants,
				extlangs = extlangs,
				extensions = extensions,
				privateuse = privateuse,
			) ?: throw IllegalArgumentException(forLanguageErrorMessage(
				language = language,
				script = script,
				region = region,
				variants = variants,
				extlangs = extlangs,
				extensions = extensions,
				privateuse = privateuse,
			))


		/** Like [forLanguage], but returns `null` instead of throwing if any subtag is ill-formed. */
		public fun forLanguageOrNull(
			language: String,
			script: String? = null,
			region: String? = null,
			variants: List<String> = emptyList(),
			extlangs: List<String> = emptyList(),
			extensions: List<String> = emptyList(),
			privateuse: String? = null,
		): LanguageTag? =
			forLanguageInternal(
				language = language,
				script = script,
				region = region,
				variants = variants,
				extlangs = extlangs,
				extensions = extensions,
				privateuse = privateuse,
			)


		private fun forLanguageInternal(
			language: String,
			script: String?,
			region: String?,
			variants: List<String>,
			extlangs: List<String>,
			extensions: List<String>,
			privateuse: String?,
		): LanguageTag? {
			val canonicalLanguage = canonicalizeLanguage(language)
			val canonicalScript = canonicalizeScript(script)
			val canonicalRegion = canonicalizeRegion(region)
			val canonicalVariants = canonicalizeVariants(variants)
			val canonicalExtlangs = canonicalizeExtlangs(extlangs)
			val canonicalExtensions = canonicalizeExtensions(extensions)
			val canonicalPrivateuse = canonicalizePrivateuse(privateuse)

			if (canonicalLanguage == null || !isLanguage(canonicalLanguage)) return null
			if (canonicalScript != null && !isScript(canonicalScript)) return null
			if (canonicalRegion != null && !isRegion(canonicalRegion)) return null

			for (variant in canonicalVariants)
				if (!isVariant(variant)) return null
			for (extlang in canonicalExtlangs)
				if (!isExtlang(extlang)) return null
			for (extension in canonicalExtensions)
				if (!isExtension(extension)) return null

			if (canonicalPrivateuse != null && !isPrivateuse(canonicalPrivateuse)) return null

			return LanguageTag(
				extensions = canonicalExtensions,
				extlangs = canonicalExtlangs,
				language = canonicalLanguage,
				privateuse = canonicalPrivateuse,
				region = canonicalRegion,
				script = canonicalScript,
				variants = canonicalVariants
			)
		}


		private fun forLanguageErrorMessage(
			language: String,
			script: String?,
			region: String?,
			variants: List<String>,
			extlangs: List<String>,
			extensions: List<String>,
			privateuse: String?,
		): String {
			val canonicalLanguage = canonicalizeLanguage(language)
			if (canonicalLanguage == null || !isLanguage(canonicalLanguage)) return "Invalid language: $language"

			val canonicalScript = canonicalizeScript(script)
			if (canonicalScript != null && !isScript(canonicalScript)) return "Invalid script: $script"

			val canonicalRegion = canonicalizeRegion(region)
			if (canonicalRegion != null && !isRegion(canonicalRegion)) return "Invalid region: $region"

			for (variant in canonicalizeVariants(variants))
				if (!isVariant(variant)) return "Invalid variant: $variant"
			for (extlang in canonicalizeExtlangs(extlangs))
				if (!isExtlang(extlang)) return "Invalid extlang: $extlang"
			for (extension in canonicalizeExtensions(extensions))
				if (!isExtension(extension)) return "Invalid extension: $extension"

			val canonicalPrivateuse = canonicalizePrivateuse(privateuse)
			if (canonicalPrivateuse != null && !isPrivateuse(canonicalPrivateuse)) return "Invalid privateuse: $privateuse"

			return "Invalid language tag"
		}


		/**
		 * Creates a private-use-only [LanguageTag] (e.g. `x-custom`).
		 *
		 * @throws IllegalArgumentException if [privateuse] is ill-formed.
		 */
		public fun forPrivateUse(
			privateuse: String,
		): LanguageTag =
			forPrivateUseInternal(privateuse)
				?: throw IllegalArgumentException("Invalid privateuse: $privateuse")


		/** Like [forPrivateUse], but returns `null` instead of throwing if [privateuse] is ill-formed. */
		public fun forPrivateUseOrNull(
			privateuse: String,
		): LanguageTag? =
			forPrivateUseInternal(privateuse)


		private fun forPrivateUseInternal(
			privateuse: String,
		): LanguageTag? {
			val canonicalPrivateuse = canonicalizePrivateuse(privateuse)
			if (canonicalPrivateuse == null || !isPrivateuse(canonicalPrivateuse)) return null

			return LanguageTag(
				extensions = emptyList(),
				extlangs = emptyList(),
				language = null,
				privateuse = canonicalPrivateuse,
				region = null,
				script = null,
				variants = emptyList()
			)
		}


		/** Checks whether [extension] is a well-formed BCP 47 extension subtag (singleton + subtags). */
		public fun isExtension(extension: String): Boolean {
			val tokens = extension.splitToSequence(separator).iterator()

			if (!isExtensionSingleton(tokens.next()))
				return false

			if (!tokens.hasNext())
				return false

			for (token in tokens)
				if (!isExtensionSubtag(token))
					return false

			return true
		}


		//                                     ; Single alphanumerics
		//                                     ; "x" reserved for private use
		// singleton     = DIGIT               ; 0 - 9
		//               / %x41-57             ; A - W
		//               / %x59-5A             ; Y - Z
		//               / %x61-77             ; a - w
		//               / %x79-7A             ; y - z
		public fun isExtensionSingleton(singleton: Char): Boolean =
			singleton.isAlphaNumeric() && !isPrivateusePrefix(singleton)


		public fun isExtensionSingleton(singleton: String): Boolean =
			singleton.length == 1 && isExtensionSingleton(singleton[0])


		// extension     = singleton 1*("-" (2*8alphanum))
		public fun isExtensionSubtag(extension: String): Boolean =
			extension.length in 2 .. 8 && extension.isAlphaNumeric()


		// extlang       = 3ALPHA              ; selected ISO 639 codes
		//                 *2("-" 3ALPHA)      ; permanently reserved
		public fun isExtlang(extlang: String): Boolean =
			extlang.length == 3 && extlang.isAlpha()


		/** Checks whether [language] is a well-formed BCP 47 primary language subtag (2-8 ALPHA). */
		public fun isLanguage(language: String): Boolean =
			language.length in 2 .. 8 && language.isAlpha()


		/** Checks whether [privateuse] is a well-formed BCP 47 private-use subtag sequence (e.g. `x-custom`). */
		public fun isPrivateuse(privateuse: String): Boolean {
			val tokens = privateuse.splitToSequence(separator).iterator()

			if (!isPrivateusePrefix(tokens.next()))
				return false

			if (!tokens.hasNext())
				return false

			for (token in tokens)
				if (!isPrivateuseSubtag(token))
					return false

			return true
		}


		// privateuse    = "x" 1*("-" (1*8alphanum))
		public fun isPrivateusePrefix(prefix: Char): Boolean =
			prefix.equals(privateusePrefix, ignoreCase = true)


		public fun isPrivateusePrefix(prefix: String): Boolean =
			prefix.length == 1 && isPrivateusePrefix(prefix[0])


		// privateuse    = "x" 1*("-" (1*8alphanum))
		public fun isPrivateuseSubtag(privateuse: String): Boolean =
			privateuse.length in 1 .. 8 && privateuse.isAlphaNumeric()


		/** Checks whether [region] is a well-formed BCP 47 region subtag (2 ALPHA or 3 DIGIT). */
		public fun isRegion(region: String): Boolean =
			when (region.length) {
				2 -> region.isAlpha()
				3 -> region.isNumeric()
				else -> false
			}


		/** Checks whether [script] is a well-formed BCP 47 script subtag (4 ALPHA). */
		public fun isScript(script: String): Boolean =
			script.length == 4 && script.isAlpha()


		/** Checks whether [variant] is a well-formed BCP 47 variant subtag. */
		public fun isVariant(variant: String): Boolean =
			when (variant.length) {
				4 -> variant[0].isNumeric() && variant[1].isAlphaNumeric() && variant[2].isAlphaNumeric() && variant[3].isAlphaNumeric()
				in 5 .. 8 -> variant.isAlphaNumeric()
				else -> false
			}


		/**
		 * Parses a BCP 47 language tag string. Grandfathered tags are replaced with modern equivalents.
		 *
		 * @throws IllegalStateException if the tag is ill-formed.
		 */
		public fun parse(string: String): LanguageTag {
			require(string.length <= 256) { "Language tag too long: ${string.length} characters (max 256)" }
			return parseOrNull(string) ?: error("Ill-formed BCP 47 language tag: $string")
		}


		/** Like [parse], but returns `null` instead of throwing if the tag is ill-formed. */
		@Suppress("NAME_SHADOWING")
		public fun parseOrNull(string: String): LanguageTag? {
			if (string.length > 256) return null
			val string = grandfathered[string.lowercase()] ?: string

			val tokens = string.splitToSequence(separator).iterator()

			var extlangs: MutableList<String>? = null
			var extensions: MutableList<String>? = null
			var language: String? = null
			var privateuse: String? = null
			var region: String? = null
			var script: String? = null
			var variants: MutableList<String>? = null

			var token: String?
			token = tokens.next()
			if (isLanguage(token)) {
				language = canonicalizeLanguage(token)
				token = tokens.nextOrNull()

				if (token != null && isExtlang(token)) {
					extlangs = mutableListOf()

					do {
						extlangs.add(canonicalizeExtlang(token)!!)

						token = tokens.nextOrNull()
					}
					while (token != null && extlangs.size < 3 && isExtlang(token))
				}

				if (token != null && isScript(token)) {
					script = canonicalizeScript(token)
					token = tokens.nextOrNull()
				}

				if (token != null && isRegion(token)) {
					region = canonicalizeRegion(token)
					token = tokens.nextOrNull()
				}

				if (token != null && isVariant(token)) {
					variants = mutableListOf()

					do {
						variants.add(canonicalizeVariant(token)!!)

						token = tokens.nextOrNull()
					}
					while (token != null && isVariant(token))
				}

				if (token != null && isExtensionSingleton(token)) {
					extensions = mutableListOf()

					do {
						val extensionBuilder = StringBuilder(canonicalizeExtensionSingleton(token)!!)

						token = tokens.nextOrNull()

						if (token != null && isExtensionSubtag(token)) {
							do {
								extensionBuilder.append(separator)
								extensionBuilder.append(canonicalizeExtensionSubtag(token))

								token = tokens.nextOrNull()
							}
							while (token != null && isExtensionSubtag(token))
						}
						else
							return null // incomplete extension

						extensions.add(extensionBuilder.toString())
					}
					while (token != null && isExtensionSingleton(token))
				}
			}

			if (token != null && isPrivateusePrefix(token)) {
				val privateuseBuilder = StringBuilder(canonicalizePrivateusePrefix(token)!!)

				token = tokens.nextOrNull()

				if (token != null && isPrivateuseSubtag(token)) {
					do {
						privateuseBuilder.append(separator)
						privateuseBuilder.append(canonicalizePrivateuseSubtag(token))

						token = tokens.nextOrNull()
					}
					while (token != null && isPrivateuseSubtag(token))
				}
				else
					return null // incomplete privateuse

				privateuse = privateuseBuilder.toString()
			}

			if (token != null)
				return null

			return LanguageTag(
				extensions = extensions.orEmpty(),
				extlangs = extlangs.orEmpty(),
				language = language,
				privateuse = privateuse,
				region = region,
				script = script,
				variants = variants.orEmpty(),
			)
		}
	}
}
