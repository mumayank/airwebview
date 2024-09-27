plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish") // Plugin to enable publishing to Maven
}

android {
    namespace = "com.mumayank.airwebview"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.pdfium.android)
    implementation(libs.androidpdfviewer)

    configurations.all {
        exclude(group = "com.android.support")
    }
}

group = "com.github.mumayank" // Your GitHub username (JitPack uses this as the Maven group ID)
version = "0.1" // The version you wish to publish

// Task to package source files (Optional but recommended for library)
val sourcesJar by tasks.creating(Jar::class) {
    from(android.sourceSets["main"].java.srcDirs)
    archiveClassifier.set("sources")
}

// Publishing configuration for JitPack
publishing {
    publications {
        // Defines Maven publication for the library
        create<MavenPublication>("mavenJava") {
            groupId = "com.github.mumayank" // Your GitHub username
            artifactId = "airwebview" // Name of your library
            version = "0.1" // Version of the library
            // Attach source code jar (Optional but good practice)
            artifact(sourcesJar)
        }
    }
}