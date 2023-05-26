plugins {
	java
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	id("idea")
}

group = "com.niliusjulius"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

sourceSets {
	create("testIntegration") {
		compileClasspath += sourceSets.main.get().output
		runtimeClasspath += sourceSets.main.get().output
		compileClasspath += sourceSets.test.get().output
		runtimeClasspath += sourceSets.test.get().output
	}
}

val testIntegrationImplementation by configurations.getting {
	extendsFrom(configurations.implementation.get())
}
configurations["testIntegrationRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

idea {
	module {
		testSources.from(sourceSets["testIntegration"].java.srcDirs)
		testSources.from(sourceSets["testIntegration"].resources.srcDirs)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://jitpack.io") }
}

val springShellVersion by extra {"3.0.0"}

dependencies {
	// Spring
	implementation("org.springframework.shell:spring-shell-starter")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	// Riot API wrapper
	implementation("com.github.stelar7:R4J:2.2.9")
	// SSH Shell
	implementation("com.github.fonimus:ssh-shell-spring-boot-starter:2.0.3")
	implementation("net.java.dev.jna:jna:5.13.0")
	implementation("org.jline:jline-terminal-jna:3.22.0")
	implementation("ch.qos.logback:logback-classic:1.4.5")
	implementation("javax.annotation:javax.annotation-api:1.3.2")

	compileOnly("org.projectlombok:lombok")

	annotationProcessor("org.projectlombok:lombok")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.google.jimfs:jimfs:1.2")

	testIntegrationImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.shell:spring-shell-dependencies:${property("springShellVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	useJUnitPlatform {
		excludeTags("ManualTest")
	}
}

tasks.register<Test>("manualTest") {
	group = "verification"
	description = "Runs tests with the ManualTest tag"
	useJUnitPlatform {
		includeTags("ManualTest")
	}
}

tasks.register<Test>("integrationTest") {
	group = "verification"
	description = "Runs integration tests"
	mustRunAfter(tasks.test)

	testClassesDirs = sourceSets["testIntegration"].output.classesDirs
	classpath = sourceSets["testIntegration"].runtimeClasspath

	useJUnitPlatform {
		excludeTags("ManualTest")
	}
}

tasks.register<Test>("integrationManualTest") {
	group = "verification"
	description = "Runs integration tests with the ManualTest tag"

	testClassesDirs = sourceSets["testIntegration"].output.classesDirs
	classpath = sourceSets["testIntegration"].runtimeClasspath

	useJUnitPlatform {
		includeTags("ManualTest")
	}
}

tasks.check {
	dependsOn("integrationTest")
}