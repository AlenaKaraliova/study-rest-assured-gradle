# Description
Let's create an automation framework for Restful API testing. Tools:
- Java `11`,
- RestAssured,
- JUnit5 `5.8.2`,
- Allure,
- Gradle.

# Instructions

## Initial Setup
1. Install Java.
2. Install IntelliJ IDEA.
3. Create your GitHub account.
4. Create `study-rest-assured-gradle` repository there.
5. In IDE choose **File** - **New** - **Project...** - **Gradle**:
   - ensure that you SDK corresponds to your Java version,
   - use **Java** framework,
   - click **Next** button,
   - enter name `study-rest-assured-gradle`,
   - location will be changed, correct it in case it is needed,
   - click **Finish** button.
6. The project structure will be:
   ```
   study-rest-assured-gradle
   |-- gradle
   |   |-- wrapper
   |   |   |-- gradle-wrapper.jar
   |   |   |-- gradle-wrapper.properties
   |-- src
   |   |-- main
   |   |   |-- java
   |   |   |-- resources
   |   |-- test
   |   |   |-- java
   |   |   |-- resources
   |-- build.gradle
   |-- gradlew
   |-- gradlew.bat
   |-- settings.gradle
   ```
7. For testing, we will not need `src/main` directory. Delete it.
8. Create file `.gitignore` in the project root with the following context, so it will be on the same level as `build.gradle` file:
   ```gitignore
   # Ignore all gradle related files
   .gradle/
   
   # Ignore all eclipse related files
   .settings/
   .classpath
   .project
   
   # Ignore all IntelliJ related files
   *.iml
   *.ipr
   *.iws
   .idea*
   
   /bin/
   */build/
   */target/
   */out/
   */allure-results/
   
   *.tmp
   ```
9. Create file `README.md` in the project root:
   ```
   Let's create an automation framework for Restful API testing. Tools:
   - Java,
   - RestAssured,
   - JUnit5,
   - Allure,
   - Gradle.
   ```
10. Commit your project into your repository - follow the instructions on your GitHib repository page. Example:
   ```shell
   git init
   git add .
   git commit -m "first commit"
   git branch -M main
   git remote add origin https://github.com/AlenaKaraliova/study-rest-assured-gradle.git
   git push -u origin main
   ```
11. Initially your `build.gradle` file has the following content:
   ```groovy
   plugins {
       id 'java'
   }
   
   group 'org.example'
   version '1.0-SNAPSHOT'
   
   repositories {
       mavenCentral()
   }
   
   dependencies {
       testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0'
       testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0'
   }
   
   test {
       useJUnitPlatform()
   }
   ```
12. Rename group to more specific one, e.g. `org.study`.

## JUnit5 Tests

13. Change JUnit5 dependencies version to more recent `5.8.2` and rebuild the project:
    1. click **Gradle** tab, and there **Reload All Gradle Projects**,
    2. ensure that corresponding libraries have correct version.
14. In `test/java` create package `org.study.tests`.
15. In this package create Java class `JUnit5Tests`.
16. Add two tests in the above class:
    ```java
    package org.study.tests;
    
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.Test;
    
    public class JUnit5Tests {
    
        @Test
        void testPassing() {
            Assertions.assertEquals(6, 2*3, "multiply operation");
        }
    
        @Test
        void testFailing() {
            Assertions.assertAll(
                    () -> Assertions.assertEquals(7, 2*3, "multiply operation"),
                    () -> Assertions.assertTrue(true, "true statement"),
                    () -> Assertions.fail("fail here")
            );
        }
    }
    ```
17. Run the tests with **Gradle**:
    1. ensure that in `build.gradle` you have a task `test` with `useJUnitPlatform()` which means Gradle will use JUnit5 for test execution,
    2. right-click on the class name and choose **Run** 'JUnit5Tests',
    3. one test will pass,
    4. other test will fail in two out of three assertions,
    5. explore **Run Configurations** for this test - it will be running with `:test --tests "org.study.tests.JUnit5Tests"`.

## JUnit5 Suites

[JUnit5 Suite](https://junit.org/junit5/docs/current/user-guide/#junit-platform-suite-engine)

18. Add dependencies into `build.gradle` and re-build the project:
    ```groovy
    testImplementation 'org.junit.platform:junit-platform-suite-api:1.8.2'
    testRuntimeOnly 'org.junit.platform:junit-platform-suite-engine:1.8.2'
    ```
19. In package `org.study.tests` create a Java class `JUnit5Suite` with a JUnit5 suite:
    ```java
    package org.study.tests;
    
    import org.junit.platform.suite.api.SelectClasses;
    import org.junit.platform.suite.api.Suite;
    
    @Suite
    @SelectClasses(JUnit5Tests.class)
    public class JUnit5Suite {
    }
    ```
20. Run the suite as a usual test and ensure that all `JUnit5Tests` tests were executed.

## RestAssured

[REST-assured Documentation](https://rest-assured.io/)
[REST-assured User Guide](https://github.com/rest-assured/rest-assured/wiki/Usage)

14. Add RestAssured library: 
    1. in `build.gradle` file, `dependencies` section, add `testImplementation 'io.rest-assured:rest-assured:4.4.0'`,
    2. rebuild the project **Gradle** - **Reload All Gradle Projects**,
    3. ensure that the library has appeared in **External Libraries**.
15. Add Java class `ZipTests` in `tests` package:
    ```java
    package org.study.tests;
    
    import io.restassured.http.ContentType;
    import org.junit.jupiter.api.Test;
    
    import static io.restassured.RestAssured.given;
    import static org.hamcrest.Matchers.aMapWithSize;
    import static org.hamcrest.Matchers.equalTo;
    
    public class ZipTests {
    
        private static final String URL = "https://api.zippopotam.us/us/{zip}";
    
        @Test
        void validZipTest() {
            String zip = "33162";
            given().log().all()
                    .when().get(URL, zip)
                    .then().log().all()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("'post code'", equalTo(zip))
                    .body("places[0]", aMapWithSize(5))
                    .body("places[0].state", equalTo("Florida"))
                    .body("places[0].'state abbreviation'", equalTo("FL"));
        }
    
    }
    ```
16. Run the test, it should pass.
17. Let's create a test that deserializes response json into a POJO:
    1. create `org.study.model` package,
    2. create class `ZipResponse`:
       ```java
...lombok...
       ```
    3. create one more test in `ZipTests`:
       ```java

       ```
18. 
    `
 
