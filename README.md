fluid-locale
============

[![Maven Central](https://img.shields.io/maven-central/v/io.fluidsonic.locale/fluid-locale?label=Maven%20Central)](https://search.maven.org/artifact/io.fluidsonic.locale/fluid-locale)
[![JCenter](https://img.shields.io/bintray/v/fluidsonic/kotlin/locale?label=JCenter)](https://bintray.com/fluidsonic/kotlin/locale)
[![Tests](https://github.com/fluidsonic/fluid-locale/workflows/Tests/badge.svg)](https://github.com/fluidsonic/fluid-locale/actions?workflow=Tests)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.4.0%20(Darwin,%20JVM,%20JS)-blue.svg)](https://github.com/JetBrains/kotlin/releases/v1.4.0)
[![#fluid-libraries Slack Channel](https://img.shields.io/badge/slack-%23fluid--libraries-543951.svg?label=Slack)](https://kotlinlang.slack.com/messages/C7UDFSVT2/)

Kotlin multiplatform `Locale` support.

This library is very early stage and can't do much yet beside wrapping and normalizing BCP 47 language tags.  
Parsing varies between platforms and validation is not yet consistent either.

**Feel free to contribute!**



Installation
------------

`build.gradle.kts`:
```kotlin
dependencies {
    implementation("io.fluidsonic.locale:fluid-locale:0.9.0")
}
```



Usage
-----

```kotlin
println(Locale.fromTag("en-US").toString()) // en-US
```

### `class Locale`

A class with information about a specific locale defined by [BCP 47](https://tools.ietf.org/html/bcp47).

```kotlin
val locale = Locale.forTag("en-US") // throws if tag is invalid (as per BCP 47) or has an invalid format [validation not yet implemented]
println(locale.tag) // en-US
```

```kotlin
val locale = Locale.forTagOrNull("this-is-not-valid") // null if tag is invalid (as per BCP 47) or has an invalid format [validation not yet implemented]
println(currency) // null [validation not yet implemented]
```

### `class LanguageTag`

An inline class for BCP 47 language tags (e.g. `en`, `en-US` or `sl-IT-nedis`).

```kotlin
val tag = LanguageTag.parse("en-US") // throws if tag has invalid format [validation not yet implemented]
println(tag.toString()) // en-US
println(tag.isValid()) // true [validation not yet implemented]
```

```kotlin
val tag = LanguageTag.parse("ar-a-aaa-b-bbb-a-ccc") // throws if tag has invalid format [validation not yet implemented]
println(tag.toString()) // ABC
println(tag.isValid()) // false [validation not yet implemented]
```

```kotlin
val tag = LanguageTag.parseOrNull("ar-a-aaa-b-bbb-a-ccc") // null if tag has invalid format [validation not yet implemented]
println(tag) // null
```



License
-------

Apache 2.0
