fluid-locale
============

[![Maven Central](https://img.shields.io/maven-central/v/io.fluidsonic.locale/fluid-locale?label=Maven%20Central)](https://search.maven.org/artifact/io.fluidsonic.locale/fluid-locale)
[![JCenter](https://img.shields.io/bintray/v/fluidsonic/kotlin/locale?label=JCenter)](https://bintray.com/fluidsonic/kotlin/locale)
[![Tests](https://github.com/fluidsonic/fluid-locale/workflows/Tests/badge.svg)](https://github.com/fluidsonic/fluid-locale/actions?workflow=Tests)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.4.10%20(Darwin,%20JVM,%20JS)-blue.svg)](https://github.com/JetBrains/kotlin/releases/v1.4.10)
[![#fluid-libraries Slack Channel](https://img.shields.io/badge/slack-%23fluid--libraries-543951.svg?label=Slack)](https://kotlinlang.slack.com/messages/C7UDFSVT2/)

Kotlin multiplatform locale library.  
**Experimental. Feel free to contribute!**



Installation
------------

`build.gradle.kts`:

```kotlin
dependencies {
	implementation("io.fluidsonic.locale:fluid-locale:0.9.3")
}
```

Usage
-----

### `class Locale`

For now this is only a thin layer over a `LanguageTag`. To be improved.

```kotlin
val locale = Locale.forLanguageTag("en-us") // throws if tag is not well-formed
println(locale.language) // en
println(locale.region) // US
println(locale.toLanguageTag()) // en-US
```

```kotlin
val locale = Locale.forLanguageTagOrNull("a-b-c-1-2-3") // null if tag is not well-formed
println(locale) // null
```

```kotlin
val locale = Locale.forLanguage("en", region = "US")
println(locale.language) // en
println(locale.region) // US
println(locale.toLanguageTag()) // en-US
```

### `class LanguageTag`

A class for BCP 47 language tags (e.g. `en`, `en-US` or `sl-IT-nedis`).

```kotlin
val tag = LanguageTag.parse("ZH-HANT-cn-somevar") // throws if tag is not well-formed
println(tag.language) // zh
println(tag.script) // Hant
println(tag.region) // CN
println(tag.variants) // [somevar]
println(tag.toString()) // zh-Hant-CN-somevar
```

```kotlin
val tag = LanguageTag.parseOrNull("a-b-c-1-2-3") // null if tag is not well-formed
println(tag) // null
```

```kotlin
val tag = LanguageTag.forLanguage("ZH", script = "HANT", region = "cn", variants = listOf("somevar"))
println(tag.language) // zh
println(tag.script) // Hant
println(tag.region) // CN
println(tag.variants) // [somevar]
println(tag.toString()) // zh-Hant-CN-somevar
```

License
-------

Apache 2.0
