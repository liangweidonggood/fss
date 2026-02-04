plugins {
    id("org.springframework.boot") version "3.3.13"
    id("io.spring.dependency-management") version "1.1.7"
    checkstyle
    pmd
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
val fssDir: File = rootProject.projectDir.parentFile!!.parentFile!!
fun getStagedJavaFiles(moduleDir: File? = null): List<String> {
    try {
        val process = ProcessBuilder(
            "git", "diff", "--cached", "--name-only", "--diff-filter=ACM", "--", "*.java"
        ).directory(fssDir)
            .redirectErrorStream(true)
            .start()
        val isCompleted = process.waitFor(10, TimeUnit.SECONDS)
        if (!isCompleted) {
            process.destroy()
            return emptyList()
        }
        val globalStagedAbsolutePaths  = process.inputStream.bufferedReader().readLines()
            .filter { it.endsWith(".java") && it.isNotEmpty() }
            .map { File(fssDir, it).absolutePath }
            .filter { File(it).exists() }
        val moduleAbsolutePath = moduleDir!!.absolutePath
        return globalStagedAbsolutePaths .filter { it.startsWith(moduleAbsolutePath) }
    } catch (_: Exception) {
        return emptyList()
    }
}
subprojects {
    pluginManager.apply("org.springframework.boot")
    pluginManager.apply("io.spring.dependency-management")
    pluginManager.apply("checkstyle")
    pluginManager.apply("pmd")
    configure<CheckstyleExtension> {
        toolVersion = "13.1.0"
        isIgnoreFailures = false
        isShowViolations = true
        maxWarnings = 0
        maxErrors = 0
        configFile = fssDir.resolve("global/config/checkstyle/checkstyle.xml")
    }
    configure<PmdExtension> {
        toolVersion = "7.21.0"
        isConsoleOutput = true
        isIgnoreFailures = false
        ruleSets = listOf()
        ruleSetFiles = files(
            fssDir.resolve("global/config/pmd/ruleset.xml")
        )
    }
    tasks.withType<Checkstyle> {
        doFirst {
            val isLintStaged = providers.gradleProperty("lintStaged").orElse("false").get().toBoolean()
            val stagedFiles = getStagedJavaFiles(projectDir)
            if (isLintStaged && stagedFiles.isNotEmpty()) {
                logger.lifecycle("【增量检查】${project.name} - Checkstyle 扫描 ${stagedFiles.size} 个专属暂存区 Java 文件")
                source = project.files(stagedFiles).asFileTree
            } else {
                logger.lifecycle("【全量检查】${project.name} - Checkstyle 扫描自身源码文件")
                source = project.fileTree(projectDir) { include("src/*/java/**/*.java") }
            }
        }
    }
    tasks.withType<Pmd> {
        doFirst {
            val isLintStaged = providers.gradleProperty("lintStaged").orElse("false").get().toBoolean()
            val stagedFiles = getStagedJavaFiles(projectDir)
            if (isLintStaged && stagedFiles.isNotEmpty()) {
                logger.lifecycle("【增量检查】${project.name} - PMD 扫描 ${stagedFiles.size} 个专属暂存区 Java 文件")
                source = project.files(stagedFiles).asFileTree
            } else {
                logger.lifecycle("【全量检查】${project.name} - PMD 扫描自身源码文件")
                source = project.fileTree(projectDir) { include("src/*/java/**/*.java") }
            }
        }
    }
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
tasks.register("codeCheck") {
    group = "quality"
    description = "执行全量代码质量检查(Checkstyle + PMD)"
    dependsOn(
        subprojects.map { it.tasks.withType<Checkstyle>() },
        subprojects.map { it.tasks.withType<Pmd>() }
    )
}
