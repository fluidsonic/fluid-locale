package io.fluidsonic.locale


/**
 * Represents a locale identified by a BCP 47 language tag.
 *
 * Use [forLanguage] or [forLanguageTag] to create instances.
 */
public class Locale private constructor(
	private val languageTag: LanguageTag,
) {

	override fun equals(other: Any?): Boolean =
		this === other || (other is Locale && languageTag == other.languageTag)


	override fun hashCode(): Int =
		languageTag.hashCode()


	/** ISO 639 language code, or `null` for the [root] locale. */
	public val language: String?
		get() = when (val language = languageTag.language) {
			LanguageTag.undeterminedPrefix -> null
			else -> language
		}


	/** ISO 3166-1 region code or UN M.49 code, or `null` if not specified. */
	public val region: String?
		get() = languageTag.region


	/** ISO 15924 script code, or `null` if not specified. */
	public val script: String?
		get() = languageTag.script


	/** Variant subtags as defined in RFC 5646. */
	public val variants: List<String>
		get() = languageTag.variants


	/** Creates a copy of this locale with the specified components replaced. */
	public fun copy(
		language: String? = this.language,
		script: String? = this.script,
		region: String? = this.region,
		variants: List<String> = this.variants,
	): Locale =
		forLanguage(language = language, script = script, region = region, variants = variants)


	/** Returns the underlying [LanguageTag] for this locale. */
	public fun toLanguageTag(): LanguageTag =
		languageTag


	/** Returns the BCP 47 language tag string representation. */
	override fun toString(): String =
		languageTag.toString()


	public companion object {

		/** The root locale with no language, region, script, or variants. */
		public val root: Locale = Locale(languageTag = LanguageTag.forLanguage(language = LanguageTag.undeterminedPrefix))


		/**
		 * Creates a [Locale] from individual subtag components.
		 *
		 * @throws IllegalArgumentException if any subtag is ill-formed per BCP 47.
		 */
		public fun forLanguage(
			language: String?,
			script: String? = null,
			region: String? = null,
			variants: List<String> = emptyList(),
		): Locale =
			forLanguageTag(LanguageTag.forLanguage(
				language = when (language) {
					null, "" -> LanguageTag.undeterminedPrefix
					else -> language
				},
				script = script,
				region = region,
				variants = variants,
			))


		/**
		 * Creates a [Locale] from individual subtag components, or returns `null` if any subtag is ill-formed.
		 */
		public fun forLanguageOrNull(
			language: String?,
			script: String? = null,
			region: String? = null,
			variants: List<String> = emptyList(),
		): Locale? =
			LanguageTag.forLanguageOrNull(
				language = when (language) {
					null, "" -> LanguageTag.undeterminedPrefix
					else -> language
				},
				script = script,
				region = region,
				variants = variants,
			)?.let(::forLanguageTag)


		/** Creates a [Locale] from a parsed [LanguageTag]. */
		public fun forLanguageTag(tag: LanguageTag): Locale =
			when (tag) {
				root.languageTag -> root
				else -> Locale(languageTag = tag)
			}


		/**
		 * Parses a BCP 47 language tag string and creates a [Locale].
		 *
		 * @throws IllegalStateException if the tag is ill-formed.
		 */
		public fun forLanguageTag(tag: String): Locale =
			forLanguageTag(LanguageTag.parse(tag))


		/** Parses a BCP 47 language tag string, or returns `null` if it is ill-formed. */
		public fun forLanguageTagOrNull(tag: String): Locale? =
			LanguageTag.parseOrNull(tag)?.let(::forLanguageTag)
	}
}
