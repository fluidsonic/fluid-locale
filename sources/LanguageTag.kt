package io.fluidsonic.locale


/**
 * A BCP 47 language tag, for example `en`, `en-US` or `sl-IT-nedis`.
 *
 * References:
 * - [https://tools.ietf.org/html/bcp47]
 */
public class LanguageTag private constructor(
	public val extensions: List<String>,
	public val extlangs: List<String>,
	public val language: String?,
	public val privateuse: String?,
	public val region: String?,
	public val script: String?,
	public val variants: List<String>,
) {

	init {
		freeze()
	}


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

			privateuse != null ->
				forPrivateUse(privateuse = privateuse)

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
		result = 31 * result + variants.ifEmpty { null }?.map(String::toLowerCase).hashCode()

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

		public const val privateusePrefix: Char = 'x'
		public const val separator: Char = '-'
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


		public fun canonicalizeExtension(extension: String?): String? =
			extension?.ifEmpty { null }?.toLowerCase()


		public fun canonicalizeExtensionSingleton(singleton: String?): String? =
			singleton?.ifEmpty { null }?.toLowerCase()


		public fun canonicalizeExtensionSubtag(subtag: String?): String? =
			subtag?.ifEmpty { null }?.toLowerCase()


		public fun canonicalizeExtensions(extensions: List<String>): List<String> =
			extensions.ifEmpty { null }?.mapNotNull(::canonicalizeExtension)?.sorted().orEmpty()


		public fun canonicalizeExtlang(extlang: String?): String? =
			extlang?.ifEmpty { null }?.toLowerCase()


		public fun canonicalizeExtlangs(extlangs: List<String>): List<String> =
			extlangs.ifEmpty { null }?.mapNotNull(::canonicalizeExtlang).orEmpty()


		public fun canonicalizeLanguage(language: String?): String? =
			language?.ifEmpty { null }?.toLowerCase()


		public fun canonicalizePrivateuse(privateuse: String?): String? =
			privateuse?.ifEmpty { null }?.toLowerCase()


		public fun canonicalizePrivateusePrefix(prefix: String?): String? =
			prefix?.ifEmpty { null }?.toLowerCase()


		public fun canonicalizePrivateuseSubtag(subtag: String?): String? =
			subtag?.ifEmpty { null }?.toLowerCase()


		public fun canonicalizeRegion(region: String?): String? =
			region?.ifEmpty { null }?.toUpperCase()


		public fun canonicalizeScript(script: String?): String? =
			script?.ifEmpty { null }?.toUppercaseFirstLowercaseRest()


		public fun canonicalizeVariant(variant: String?): String? =
			variant?.ifEmpty { null }


		public fun canonicalizeVariants(variants: List<String>): List<String> =
			variants.ifEmpty { null }?.mapNotNull(::canonicalizeVariant).orEmpty()


		public fun forLanguage(
			language: String,
			script: String? = null,
			region: String? = null,
			variants: List<String> = emptyList(),
			extlangs: List<String> = emptyList(),
			extensions: List<String> = emptyList(),
			privateuse: String? = null,
		): LanguageTag {
			val canonicalLanguage = canonicalizeLanguage(language)
			val canonicalScript = canonicalizeScript(script)
			val canonicalRegion = canonicalizeRegion(region)
			val canonicalVariants = canonicalizeVariants(variants)
			val canonicalExtlangs = canonicalizeExtlangs(extlangs)
			val canonicalExtensions = canonicalizeExtensions(extensions)
			val canonicalPrivateuse = canonicalizePrivateuse(privateuse)

			require(canonicalLanguage != null && isLanguage(canonicalLanguage)) { "Invalid language: $language" }
			require(canonicalScript == null || isScript(canonicalScript)) { "Invalid script: $script" }
			require(canonicalRegion == null || isRegion(canonicalRegion)) { "Invalid region: $region" }

			for (variant in canonicalVariants)
				require(isVariant(variant)) { "Invalid variant: $variant" }
			for (extlang in canonicalExtlangs)
				require(isExtlang(extlang)) { "Invalid extlang: $extlang" }
			for (extension in canonicalExtensions)
				require(isExtension(extension)) { "Invalid extension: $extension" }

			require(canonicalPrivateuse == null || isPrivateuse(canonicalPrivateuse)) { "Invalid privateuse: $privateuse" }

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


		public fun forLanguageOrNull(
			language: String,
			script: String? = null,
			region: String? = null,
			variants: List<String> = emptyList(),
			extlangs: List<String> = emptyList(),
			extensions: List<String> = emptyList(),
			privateuse: String? = null,
		): LanguageTag? {
			val canonicalLanguage = canonicalizeLanguage(language)
			val canonicalScript = canonicalizeScript(script)
			val canonicalRegion = canonicalizeRegion(region)
			val canonicalVariants = canonicalizeVariants(variants)
			val canonicalExtlangs = canonicalizeExtlangs(extlangs)
			val canonicalExtensions = canonicalizeExtensions(extensions)
			val canonicalPrivateuse = canonicalizePrivateuse(privateuse)

			require(canonicalLanguage != null && isLanguage(canonicalLanguage)) { return null }
			require(canonicalScript == null || isScript(canonicalScript)) { return null }
			require(canonicalRegion == null || isRegion(canonicalRegion)) { return null }

			for (variant in canonicalVariants)
				require(isVariant(variant)) { return null }
			for (extlang in canonicalExtlangs)
				require(isExtlang(extlang)) { return null }
			for (extension in canonicalExtensions)
				require(isExtension(extension)) { return null }

			require(canonicalPrivateuse == null || isPrivateuse(canonicalPrivateuse)) { return null }

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


		public fun forPrivateUse(
			privateuse: String,
		): LanguageTag {
			val canonicalPrivateuse = canonicalizePrivateuse(privateuse)
			require(canonicalPrivateuse != null && isPrivateuse(privateuse)) { "Invalid privateuse: $privateuse" }

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


		public fun forPrivateUseOrNull(
			privateuse: String,
		): LanguageTag? {
			val canonicalPrivateuse = canonicalizePrivateuse(privateuse)
			require(canonicalPrivateuse != null && isPrivateuse(privateuse)) { return null }

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


		// extension     = singleton 1*("-" (2*8alphanum))
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


		// language      = 2*3ALPHA            ; shortest ISO 639 code
		//                 ["-" extlang]       ; sometimes followed by
		//                                     ; extended language subtags
		//               / 4ALPHA              ; or reserved for future use
		//               / 5*8ALPHA            ; or registered language subtag
		public fun isLanguage(language: String): Boolean =
			language.length in 2 .. 8 && language.isAlpha()


		// privateuse    = "x" 1*("-" (1*8alphanum))
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


		// region        = 2ALPHA              ; ISO 3166-1 code
		//               / 3DIGIT              ; UN M.49 code
		public fun isRegion(region: String): Boolean =
			when (region.length) {
				2 -> region.isAlpha()
				3 -> region.isNumeric()
				else -> false
			}


		// script        = 4ALPHA              ; ISO 15924 code
		public fun isScript(script: String): Boolean =
			script.length == 4 && script.isAlpha()


		// variant       = 5*8alphanum         ; registered variants
		//               / (DIGIT 3alphanum)
		public fun isVariant(variant: String): Boolean =
			when (variant.length) {
				4 -> variant[0].isNumeric() && variant[1].isAlphaNumeric() && variant[2].isAlphaNumeric() && variant[3].isAlphaNumeric()
				in 5 .. 8 -> variant.isAlphaNumeric()
				else -> false
			}


		public fun parse(string: String): LanguageTag =
			parseOrNull(string) ?: error("Ill-formed BCP 47 language tag: $string")


		@Suppress("NAME_SHADOWING")
		public fun parseOrNull(string: String): LanguageTag? {
			val string = grandfathered[string.toLowerCase()] ?: string

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
