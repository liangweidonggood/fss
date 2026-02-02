plugins {
    id("java-library")
}
dependencies{
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.mybatis-flex:mybatis-flex-spring-boot3-starter")

    //模块依赖
    //implementation(project(":admin-auth"))
    implementation(project(":admin-common"))
    implementation(project(":admin-core:admin-core-service"))
    implementation(project(":admin-api:admin-api-system"))

    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
}
