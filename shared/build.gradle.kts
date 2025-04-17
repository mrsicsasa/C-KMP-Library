import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
        it.compilations{
            val main by getting{
                cinterops{
                    create("hello"){
                        header(file("cpp/hello.h"))
                        defFile(file("cpp/hello.def"))
                        compilerOpts("-I/Users/sasamrsic/AndroidStudioProjects/Cexample/shared/cpp")
                        linkerOpts("-L/Users/sasamrsic/AndroidStudioProjects/Cexample/shared/cpp", "-lhello")
                    }
                }
            }
        }

    }
    val compileHelloCpp by tasks.registering {
        doLast {
            val output = file("build/hello.o")
            output.parentFile.mkdirs()
            exec {
                commandLine(
                    "clang++", "-c",
                    "-std=c++11",
                    "cpp/hello.cpp",
                    "-o", output.absolutePath
                )
            }
        }
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.CInteropProcess>().configureEach {
        dependsOn(compileHelloCpp)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink>().configureEach {
        dependsOn(compileHelloCpp)
    }
    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

    }

}
android {
    namespace = "com.example.cexample"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
        ndk {
            abiFilters += listOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
        externalNativeBuild {
            cmake {
                path = file("src/androidMain/cpp/CMakeLists.txt")
            }
        }
}
