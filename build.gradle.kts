import io.fluidsonic.gradle.*
import org.jetbrains.kotlin.gradle.plugin.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.25"
}

fluidLibrary(name = "locale", version = "0.10.0")

fluidLibraryModule(description = "Kotlin multiplatform locale library") {
	targets {
		darwin()
		js(KotlinJsCompilerType.BOTH)
		jvm()
	}
}
