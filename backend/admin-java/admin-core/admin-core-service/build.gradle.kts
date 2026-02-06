plugins {
    id("java-library")
}
dependencies{
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.mybatis-flex:mybatis-flex-spring-boot3-starter")
    implementation("com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter")
    implementation("com.mybatis-flex:mybatis-flex-codegen")
    implementation("com.zaxxer:HikariCP")
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")

    //模块依赖
    implementation(project(":admin-common"))

    //开发测试
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
}

tasks.bootJar{
    enabled = false
}
