// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        // Check for the latest version on Firebase's documentation
        classpath ("com.google.gms:google-services:4.3.10")
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}
