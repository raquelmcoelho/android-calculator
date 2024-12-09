plugins {
    alias(libs.plugins.android.application)
    id("jacoco")
}

tasks.withType<Test> {
    reports {
        html.required.set(true)
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    group = "verification"
    description = "Generate Jacoco coverage report."

    classDirectories.setFrom(files("build/intermediates/javac/debug/compileDebugJavaWithJavac/classes/fr/ensicaen/calculator/"))
    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(files(layout.buildDirectory.dir("jacoco/testDebugUnitTest.exec")))

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}


android {
    namespace = "fr.ensicaen.calculator"
    compileSdk = 34

    defaultConfig {
        applicationId = "fr.ensicaen.calculator"
        minSdk = 24
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(libs.mathparser.org.mxparser)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
}
