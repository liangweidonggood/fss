plugins {
    id("java-library")
}
dependencies{


    // 添加Redis和缓存相关依赖
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.apache.commons:commons-lang3")
    implementation("com.google.guava:guava")

    //模块依赖
    implementation(project(":admin-common"))

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
