import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "3.0.0"
}

fluidLibrary(name = "locale", version = "0.14.0")

fluidLibraryModule(description = "Kotlin multiplatform locale library") {
	targets {
		js()
		jvm()
	}
}
