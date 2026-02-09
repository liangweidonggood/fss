// ==========================================================
// 配置缓存兼容的 Git 逻辑
// ==========================================================
abstract class StagedFilesValueSource : ValueSource<List<String>, StagedFilesValueSource.Parameters> {
    interface Parameters : ValueSourceParameters {
        val rootDir: DirectoryProperty
        val moduleDir: DirectoryProperty
        @get:InputFile
        @get:Optional
        val gitIndexFile: RegularFileProperty
    }

    override fun obtain(): List<String>? {
        val root = parameters.rootDir.get().asFile
        val modulePath = parameters.moduleDir.get().asFile.absolutePath

        return try {
            val process = ProcessBuilder("git", "diff", "--cached", "--name-only", "--diff-filter=ACM", "--", "*.java")
                .directory(root)
                .start()

            val isCompleted = process.waitFor(10, TimeUnit.SECONDS)
            if (!isCompleted) return emptyList()

            process.inputStream.bufferedReader().readLines()
                .filter { it.isNotBlank() }
                .map { File(root, it).absolutePath }
                .filter { it.startsWith(modulePath) && File(it).exists() }
        } catch (e: Exception) {
            null
        }
    }
}
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
val fssDir: File = rootProject.projectDir.parentFile?.parentFile ?: rootProject.projectDir

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
    val isLintStagedProvider = providers.gradleProperty("lintStaged").map { it.toBoolean() }.orElse(false)
    val stagedFilesProvider = providers.of(StagedFilesValueSource::class) {
        parameters.rootDir.set(fssDir)
        parameters.moduleDir.set(projectDir)
        val indexFile = File(fssDir, ".git/index")
        if (indexFile.exists()) {
            parameters.gitIndexFile.set(indexFile)
        }
    }
    tasks.withType<SourceTask>().configureEach {
        if (this is Checkstyle || this is Pmd) {
            val dynamicSource = isLintStagedProvider.flatMap { isStaged ->
                if (isStaged) {
                    stagedFilesProvider.map { paths ->
                        project.files(paths).asFileTree
                    }
                } else {
                    provider {
                        project.fileTree(projectDir) { include("src/*/java/**/*.java") }
                    }
                }
            }
            setSource(dynamicSource)
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
            dependency("com.mybatis-flex:mybatis-flex-spring-boot3-starter:1.11.6")
            dependency("com.mybatis-flex:mybatis-flex-codegen:1.11.6")
            dependency("com.mybatis-flex:mybatis-flex-processor:1.11.6")
            //jwt
            dependency("io.jsonwebtoken:jjwt-api:0.13.0")
            dependency("io.jsonwebtoken:jjwt-impl:0.13.0")
            dependency("io.jsonwebtoken:jjwt-jackson:0.13.0")
            //文档
            dependency("com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter:4.5.0")
            //数据库迁移
            dependency("org.flywaydb:flyway-core:11.20.3")
            dependency("org.flywaydb:flyway-database-postgresql:11.20.3")
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
