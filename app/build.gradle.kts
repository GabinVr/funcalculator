import javax.xml.parsers.DocumentBuilderFactory

plugins {
    alias(libs.plugins.android.application)
    id("jacoco")
}

android {
    namespace = "com.example.funcalculator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.funcalculator"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
    }

    testOptions {
        unitTests{
            isIncludeAndroidResources = true
            all {
                it.extensions.configure<JacocoTaskExtension> {
                    isIncludeNoLocationClasses = true
                    excludes = listOf("jdk.internal.*")
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.room.runtime)
    implementation(libs.volley)
    testImplementation(libs.androidx.core)
    testImplementation(libs.ext.junit)
//    // If this project only uses Java source, use the Java annotationProcessor
//    // No additional plugins are necessary
//    annotationProcessor(libs.room.compiler)
//    // optional - Test helpers
//    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockwebserver)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.mockito.core)
    // Js engine
    implementation(libs.rhino.android)
    implementation(libs.androidx.javascriptengine)
    testImplementation(libs.org.jacoco.core)
}

tasks.register("jacocoTestReport", JacocoReport::class) {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(true)
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/com/example/funcalculator/databinding/*.*"
    )

    val debugTree = fileTree("${buildDir}/intermediates/javac/debug") {
        exclude(fileFilter)
    }

    val kotlinDebugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    val mainSrc = "src/main/java"

    classDirectories.setFrom(files(listOf(debugTree, kotlinDebugTree)))
    sourceDirectories.setFrom(files(mainSrc))
    executionData.setFrom(fileTree(buildDir) {
        include(
            "jacoco/testDebugUnitTest.exec",
            "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
        )
    })
}

tasks.register("codeCoverage") {
    dependsOn("jacocoTestReport")
    doLast {
        val reportFile = file("${buildDir}/reports/jacoco/jacocoTestReport/html/index.html")
        if (!reportFile.exists()) {
            println("Jacoco HTML report not found at: ${reportFile.path}")
            return@doLast
        }

        val regex = """class="ctr2">([0-9]{1,2})""".toRegex()
        val content = reportFile.readText()

        val match = regex.find(content)
        if (match != null) {
            val coverage = match.groups[1]?.value
            println("TOTAL COVERAGE: $coverage%")
        } else {
            println("Coverage percentage not found in the HTML report.")
        }
    }
}

