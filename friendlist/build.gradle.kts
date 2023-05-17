dependencies {
    implementation(libs.spring.starter.web)
    implementation(libs.spring.data.jpa)
    implementation(libs.postgres)
    implementation(project(":friendlist-shared"))
    implementation(libs.openApi)
    testImplementation(libs.springboot.test)
    testImplementation(libs.h2.database)
    annotationProcessor(libs.mapstruct.processor)
    implementation(libs.mapstruct)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

group = "com.bytro.friendlist"

version = "1.0.0"
