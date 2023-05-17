plugins {
    java
    alias(libs.plugins.spotless)
}

allprojects {
    apply(plugin = "com.diffplug.spotless")

    repositories { mavenCentral() }

    spotless {
        java { googleJavaFormat().aosp() }
        kotlin { ktfmt().kotlinlangStyle() }
        kotlinGradle { ktfmt().kotlinlangStyle() }
    }
}

configure(subprojects) {
    apply(plugin = "java")

    java.targetCompatibility = JavaVersion.VERSION_17
    java.sourceCompatibility = JavaVersion.VERSION_17

    tasks.test { useJUnitPlatform() }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-Xlint:all,-processing")
        options.compilerArgs.add("-Werror")
    }
}
