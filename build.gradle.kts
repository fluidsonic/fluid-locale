import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.6"
}

fluidLibrary(name = "locale", version = "0.9.0")

fluidLibraryModule(description = "Kotlin multiplatform locale library") {
	targets {
		darwin()
		js()
		jvm()
	}
}
