plugins {
    id("org.springframework.boot") version "3.3.13"
    id("io.spring.dependency-management") version "1.1.7"
}
allprojects {
    repositories {
        mavenLocal()
        maven {
            url = uri("http://123.52.43.113:13002/repository/maven-public/")
            isAllowInsecureProtocol = true
        }
        maven { url = uri("https://repo.spring.io/release") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        mavenCentral()
        gradlePluginPortal()
    }
}
subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
        dependencies {
            // 通用工具 集合
            dependency("org.apache.commons:commons-lang3:3.20.0")
            dependency("org.apache.commons:commons-collections4:4.5.0")
            dependency("org.apache.commons:commons-text:1.15.0")
            dependency("com.google.guava:guava:33.5.0-jre")
            dependency("io.vavr:vavr:0.11.0")
            dependency("org.mapstruct:mapstruct:1.6.3")
            dependency("org.mapstruct:mapstruct-processor:1.6.3")
            // 缓存
            dependency("com.github.ben-manes.caffeine:caffeine:3.2.3")
            // orm
            dependency("com.mybatis-flex:mybatis-flex-spring-boot3-starter:1.11.5")
            dependency("com.mybatis-flex:mybatis-flex-codegen:1.11.5")
            //jwt
            dependency("io.jsonwebtoken:jjwt-api:0.13.0")
            dependency("io.jsonwebtoken:jjwt-impl:0.13.0")
            dependency("io.jsonwebtoken:jjwt-jackson:0.13.0")
            //文档
            dependency("com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter:4.5.0")

        }
    }
}
