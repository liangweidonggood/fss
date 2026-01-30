plugins {
    id("java-library")
}
dependencies{
    implementation("org.springframework.boot:spring-boot-starter-web")

    //模块依赖

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
