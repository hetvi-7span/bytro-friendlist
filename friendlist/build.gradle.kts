dependencies {
    implementation(libs.spring.starter.web)
    implementation(libs.spring.data.jpa)
    implementation(libs.postgres)
    implementation(project(":friendlist-shared"))
    implementation(libs.openApi)
    testImplementation(libs.springboot.test)
    testImplementation(libs.h2.database)
}

group = "com.bytro.friendlist"

version = "1.0.0"
