plugins {
    id("java-library")
}
dependencies{
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.mybatis-flex:mybatis-flex-spring-boot3-starter")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("io.jsonwebtoken:jjwt-api")
    implementation("io.jsonwebtoken:jjwt-impl")
    implementation("io.jsonwebtoken:jjwt-jackson")
    implementation("com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter")
    implementation("com.mybatis-flex:mybatis-flex-codegen")
    implementation("com.zaxxer:HikariCP")
    implementation("org.postgresql:postgresql")

    //开发测试
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    compileOnly("org.springframework.boot:spring-boot-starter-security")
}
tasks.bootJar{
    enabled = false
}
