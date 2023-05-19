dependencies {
    implementation(libs.jackson.annotation)
    implementation(libs.spring.validation)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

group = "com.bytro.friendlist"

version = "1.0.0"
