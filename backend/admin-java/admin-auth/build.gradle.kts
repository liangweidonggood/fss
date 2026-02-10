plugins {
    id("java-library")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api")
    implementation("io.jsonwebtoken:jjwt-impl")
    implementation("io.jsonwebtoken:jjwt-jackson")
    implementation("com.mybatis-flex:mybatis-flex-spring-boot3-starter")
    implementation("com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter")
    //模块依赖
    implementation(project(":admin-common"))
    implementation(project(":admin-core:admin-core-service"))

    //开发测试
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.bootJar{
    enabled = false
}
