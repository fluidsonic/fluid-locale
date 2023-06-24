import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.3.1"
}

fluidLibrary(name = "locale", version = "0.12.0")

fluidLibraryModule(description = "Kotlin multiplatform locale library") {
	targets {
		darwin()
		js()
		jvm()
	}
}
