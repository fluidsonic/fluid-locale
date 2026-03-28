package tests

import io.fluidsonic.locale.*
import kotlin.test.*


class LanguageTagGrandfatheredTests {

	@Test
	fun artLojban() {
		assertEquals(actual = LanguageTag.parse("art-lojban").toString(), expected = "jbo")
	}

	@Test
	fun iKlingon() {
		assertEquals(actual = LanguageTag.parse("i-klingon").toString(), expected = "tlh")
	}

	@Test
	fun zhMinNan() {
		assertEquals(actual = LanguageTag.parse("zh-min-nan").toString(), expected = "nan")
	}

	@Test
	fun enGbOed() {
		assertEquals(actual = LanguageTag.parse("en-GB-oed").toString(), expected = "en-GB-oxendict")
	}

	@Test
	fun noBok() {
		assertEquals(actual = LanguageTag.parse("no-bok").toString(), expected = "nb")
	}

	@Test
	fun noNyn() {
		assertEquals(actual = LanguageTag.parse("no-nyn").toString(), expected = "nn")
	}

	@Test
	fun zhGuoyu() {
		assertEquals(actual = LanguageTag.parse("zh-guoyu").toString(), expected = "cmn")
	}

	@Test
	fun zhHakka() {
		assertEquals(actual = LanguageTag.parse("zh-hakka").toString(), expected = "hak")
	}

	@Test
	fun sgnBeFr() {
		assertEquals(actual = LanguageTag.parse("sgn-BE-FR").toString(), expected = "sfb")
	}

	@Test
	fun iAmi() {
		assertEquals(actual = LanguageTag.parse("i-ami").toString(), expected = "ami")
	}

	@Test
	fun grandfathered_caseInsensitive() {
		assertEquals(actual = LanguageTag.parse("ART-LOJBAN").toString(), expected = "jbo")
		assertEquals(actual = LanguageTag.parse("I-KLINGON").toString(), expected = "tlh")
	}

	@Test
	fun iDefault() {
		assertEquals(actual = LanguageTag.parse("i-default").toString(), expected = "en-x-i-default")
	}

	@Test
	fun sgnBeNl() {
		assertEquals(actual = LanguageTag.parse("sgn-BE-NL").toString(), expected = "vgt")
	}

	@Test
	fun sgnChDe() {
		assertEquals(actual = LanguageTag.parse("sgn-CH-DE").toString(), expected = "sgg")
	}

	@Test
	fun zhXiang() {
		assertEquals(actual = LanguageTag.parse("zh-xiang").toString(), expected = "hsn")
	}
}
