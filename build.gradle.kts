import io.fluidsonic.gradle.*
import org.jetbrains.kotlin.gradle.plugin.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.23"
}

fluidLibrary(name = "locale", version = "0.9.6")

fluidLibraryModule(description = "Kotlin multiplatform locale library") {
	targets {
		darwin()
		js(KotlinJsCompilerType.BOTH)
		jvm()
	}
}
