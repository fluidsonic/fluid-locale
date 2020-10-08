import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.10"
}

fluidLibrary(name = "locale", version = "0.9.3")

fluidLibraryModule(description = "Kotlin multiplatform locale library") {
	targets {
		darwin()
		js()
		jvm()
	}
}
