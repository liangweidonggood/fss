plugins {
    id("java-library")
}
dependencies{
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    //模块依赖
    implementation(project(":admin-auth"))
    implementation(project(":admin-common"))
    implementation(project(":admin-core:admin-core-service"))
    implementation(project(":admin-api:admin-api-system"))
}
