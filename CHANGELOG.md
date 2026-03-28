# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/),
and this project adheres to [Semantic Versioning](https://semver.org/).

## [0.14.0] - 2026-03-28

### Changed

- Updated to Kotlin 2.3.20 via fluid-gradle 3.0.0
- Updated Gradle wrapper to 9.4.1
- Minimum JDK raised to 21
- Renamed internal extension functions to match Kotlin 2.x naming conventions (`lowercase()`, `uppercase()`, `lowercaseChar()`, `uppercaseChar()`)
- Removed deprecated `@BuilderInference` annotation from `LocalizedValueResolver.buildMap()`
- Updated CI to use latest GitHub Actions (checkout v4, setup-java v4)
- Updated JVM tests to use non-deprecated `java.util.Locale.of()` factory method

### Added

- `Locale.copy()` method for creating modified copies of locales
- Input length guard on `LanguageTag.parse()` and `parseOrNull()` (max 256 chars)
- `LanguageTag.copy()` now validates incompatible params on private-use-only tags via `require()`
- KDoc documentation on all public API surfaces
- Comprehensive unit tests for internal extension functions (Char, String, Iterator, List)
- Comprehensive unit tests for LanguageTag validation, canonicalization, copy, extension parsing, and grandfathered tag normalization
- Unit tests for Locale copy and round-trip conversions

### Fixed

- `forPrivateUse()` and `forPrivateUseOrNull()` now validate the canonicalized private-use value instead of the raw input
- Improved hash distribution for `LanguageTag` instances without variants
- Deduplicated internal validation logic in factory methods

### Removed

- Darwin/Native target support (iOS, macOS, tvOS, watchOS)

### Deprecated

- JS target support (deprecated upstream in fluid-gradle 3.0.0)
