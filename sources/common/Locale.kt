package io.fluidsonic.locale

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*


@Serializable(with = LocaleSerializer::class)
public class Locale internal constructor(
	internal val platform: Any
) {

	init {
		freeze()
	}


	override fun equals(other: Any?): Boolean =
		this === other || (other is Locale && platform == other.platform)


	override fun hashCode(): Int =
		platform.hashCode()


	override fun toString(): String =
		serializeLocale(this)


	public companion object {

		public fun parseOrNull(tag: String): Locale? =
			parseLocaleOrNull(tag)
	}
}


@Serializer(forClass = Locale::class)
internal object LocaleSerializer : KSerializer<Locale> {

	override val descriptor = PrimitiveSerialDescriptor("io.fluidsonic.locale.Locale", PrimitiveKind.STRING)


	override fun deserialize(decoder: Decoder) =
		decoder.decodeString().let { tag ->
			Locale.parseOrNull(tag) ?: throw SerializationException("Invalid Locale tag: $tag")
		}


	override fun serialize(encoder: Encoder, value: Locale) {
		encoder.encodeString(value.toString())
	}
}


internal expect fun parseLocaleOrNull(tag: String): Locale?
internal expect fun serializeLocale(locale: Locale): String
