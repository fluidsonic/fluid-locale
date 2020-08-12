import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.4"
}

fluidLibrary(name = "locale", version = "0.9.0")

fluidLibraryModule(description = "Kotlin multiplatform locale library") {
	targets {
		common {
			dependencies {
				implementation(kotlinx("serialization-runtime", "1.0-M1-1.4.0-rc"))
			}
		}
		js()
		jvmJdk7()
		nativeDarwin()
	}
}
