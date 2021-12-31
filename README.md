# Description
Let's create an automation framework for Restful API testing. Tools:
- Java `11`,
- Lombok `6.3.0`,
- REST-assured `4.4.0`,
- JUnit5 `5.8.2`,
- Allure,
- Gradle `7.3.3`.

# Instructions

## Initial Setup

1. Install Java.
2. Install [IntelliJ IDEA](https://www.jetbrains.com/help/idea/installation-guide.html).
3. Create your [GitHub](https://github.com/join) account.
4. Create `study-rest-assured-gradle` repository there.
5. In IDE choose **File** - **New** - **Project...** - **Gradle**:
    - ensure that you **SDK** corresponds to your Java version,
    - use **Java** framework,
    - click **Next** button,
    - enter **Name** `study-rest-assured-gradle`,
    - **Location** will be changed, correct it in case it is needed,
    - click **Finish** button.
6. The project structure will look like:
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
    - Lombok,
    - REST-assured,
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
13. Add [Lombok](https://projectlombok.org/features/all) Gradle plugin `id 'io.freefair.lombok' version '6.3.0'`.
14. Add [Jackson](https://github.com/FasterXML/jackson-docs) dependencies for working with json:
    ```groovy
    testImplementation 'com.fasterxml.jackson.core:jackson-core:2.13.1'
    testImplementation 'com.fasterxml.jackson.core:jackson-annotations:2.13.1'
    testImplementation 'com.fasterxml.jackson.core:jackson-databind:2.13.1'
    ```

## JUnit5 Tests

* [Junit5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

1. Change JUnit5 dependencies version to more recent `5.8.2` and rebuild the project:
    1. click **Gradle** tab, and there **Reload All Gradle Projects**,
    2. ensure that corresponding libraries have correct version.
2. In `test/java` create package `org.study.tests`.
3. In this package create Java class `JUnit5Tests`.
4. Add two tests in the above class:
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
5. Run the tests with Gradle:
    1. ensure that in `build.gradle` you have a task `test` with `useJUnitPlatform()` which means Gradle will use JUnit5 for test execution,
    2. right-click on the class name and choose **Run** 'JUnit5Tests',
    3. one test will pass,
    4. other test will fail in two out of three assertions,
    5. explore **Run Configurations** for this test - it will be running with `:test --tests "org.study.tests.JUnit5Tests"`.

## JUnit5 Suites

* [JUnit5 Suite](https://junit.org/junit5/docs/current/user-guide/#junit-platform-suite-engine)

1. Add dependencies into `build.gradle` and re-build the project:
    ```groovy
    testImplementation 'org.junit.platform:junit-platform-suite-api:1.8.2'
    testRuntimeOnly 'org.junit.platform:junit-platform-suite-engine:1.8.2'
    ```
2. In package `org.study.tests` create a Java class `JUnit5Suite` with a JUnit5 suite:
    ```java
    package org.study.tests;
    
    import org.junit.platform.suite.api.SelectClasses;
    import org.junit.platform.suite.api.Suite;
    
    @Suite
    @SelectClasses(JUnit5Tests.class)
    public class JUnit5Suite {
    }
    ```
3. Run the suite as a usual test and ensure that all `JUnit5Tests` tests were executed.

## REST-assured

* [REST-assured Documentation](https://rest-assured.io/)

* [REST-assured User Guide](https://github.com/rest-assured/rest-assured/wiki/Usage)

1. Add Rest-assured library: 
    1. in `build.gradle` file, `dependencies` section, add `testImplementation 'io.rest-assured:rest-assured:4.4.0'`,
    2. rebuild the project **Gradle** - **Reload All Gradle Projects**,
    3. ensure that the library has appeared in **External Libraries**.
2. Add Java class `RestAssuredZipTests` in `tests` package:
    ```java
    package org.study.tests;
    
    import io.restassured.http.ContentType;
    import org.junit.jupiter.api.Test;
    
    import static io.restassured.RestAssured.given;
    import static org.hamcrest.Matchers.aMapWithSize;
    import static org.hamcrest.Matchers.equalTo;
    
    public class RestAssuredZipTests {
    
        private static final String ZIP_URL = "https://api.zippopotam.us/us/{zip}";
    
        @Test
        void validZipTest() {
            String zip = "33162";
            given().log().all()
                .when().get(ZIP_URL, zip)
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
3. Run the test, it should pass.
    1. Let's create a test that deserializes response json into a POJO:
        1. first, we need to implement response model, for that create `org.study.model` package,
        2. create class `ZipResponse` with the necessary fields:
            ```java
            public class ZipResponse {
                private String postCode;
                private String country;
                private String countryAbbreviation;
                private List<Places> places;
                public static class Places {
                    private String placeName;
                    private String longitude;
                    private String latitude;
                    private String state;
                    private String stateAbbreviation;
                }
            }
            ```
        3. add lombok annotations to provide getters, setters, constructors,
            ```java
            package org.study.model; 

            import com.fasterxml.jackson.annotation.JsonProperty;
            import lombok.AllArgsConstructor;
            import lombok.Data;
            import lombok.NoArgsConstructor;
            import lombok.experimental.Accessors;

            import java.util.List;

            @Data
            @Accessors(fluent = true)
            @NoArgsConstructor
            @AllArgsConstructor
            public class ZipResponse {
                //
            }
            ```
        4. add annotations for proper json parsing:
            ```java
            package org.study.model;
    
            import com.fasterxml.jackson.annotation.JsonProperty;
            import lombok.AllArgsConstructor;
            import lombok.Data;
            import lombok.NoArgsConstructor;
            import lombok.experimental.Accessors;
    
            import java.util.List;
    
            @Data
            @Accessors(fluent = true)
            @NoArgsConstructor
            @AllArgsConstructor
            public class ZipResponse {
    
                @JsonProperty("post code")
                private String postCode;
    
                @JsonProperty("country")
                private String country;
    
                @JsonProperty("country abbreviation")
                private String countryAbbreviation;
    
                @JsonProperty("places")
                private List<Places> places;
    
                @Data
                @Accessors(fluent = true)
                @NoArgsConstructor
                @AllArgsConstructor
                public static class Places {
    
                    @JsonProperty("place name")
                    private String placeName;
    
                    @JsonProperty("longitude")
                    private String longitude;
    
                    @JsonProperty("latitude")
                    private String latitude;
    
                    @JsonProperty("state")
                    private String state;
    
                    @JsonProperty("state abbreviation")
                    private String stateAbbreviation;
    
                }
    
            }
            ```
        5. now we are ready to create one more test in `RestAssuredZipTests`:
            ```java
            public class RestAssuredZipTests {

                private static final String ZIP_URL = "https://api.zippopotam.us/us/{zip}";

                @Test
                void validZipPojoTest() {
                    String zip = "33162";
                    ZipResponse zipResponse = given().log().all()
                        .when().get(ZIP_URL, zip)
                        .then().log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().as(ZipResponse.class);

                    Assertions.assertEquals(zip, zipResponse.postCode(), "post code");
                    Assertions.assertEquals(1, zipResponse.places().size(), "places list size");
                    Assertions.assertEquals("Florida", zipResponse.places().get(0).state(), "state");
                    Assertions.assertEquals("FL", zipResponse.places().get(0).stateAbbreviation(), "state abbreviation");
                }
            }
            ```
4. Create a test with request body and HTTP method POST:
    1. review API documentation on `http://jsonplaceholder.typicode.com/guide/`,
    2. create another test class `RestAssuredTodosTests`,
    3. implement model classes:
        ```java
            @Data
            @Accessors(fluent = true)
            @NoArgsConstructor
            @AllArgsConstructor
            public class TodoRequest {
    
                @JsonProperty
                private Integer userId;
                
                @JsonProperty
                private Integer id;
       
                @JsonProperty
                private String title;
       
                @JsonProperty
                private boolean completed;
    
            }
        ```
        ```java
            @Data
            @Accessors(fluent = true)
            @NoArgsConstructor
            @AllArgsConstructor
            public class TodoResponse {
    
                @JsonProperty
                private Integer userId;
                
                @JsonProperty
                private Integer id;
       
                @JsonProperty
                private String title;
       
                @JsonProperty
                private boolean completed;
    
            }
        ```
    4. the test with [soft assertions](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions) will look like this:
        ```java
        public class RestAssuredTodosTests {

            private static final String TODO_URL = "https://jsonplaceholder.typicode.com/users/{userId}/todos";

            @Test
            void validZipPojoTest() {
                Integer userId = 1;
                String title = "todo #1";
                boolean completed = false;
                TodoRequest todoRequest = new TodoRequest().userId(userId).title(title).completed(completed);
                TodoResponse todoResponse = given().log().all()
                    .when().body(todoRequest).header("Content-Type", ContentType.JSON).post(TODO_URL, userId)
                    .then().log().all()
                    .statusCode(201)
                    .contentType(ContentType.JSON)
                    .extract().as(TodoResponse.class);

                Assertions.assertAll(
                    () -> Assertions.assertEquals(201, todoResponse.id(), "id"),
                    () -> Assertions.assertEquals(1, todoResponse.userId(), "userId"),
                    () -> Assertions.assertEquals("todo #1", todoResponse.title(), "title"),
                    () -> Assertions.assertEquals(completed, todoResponse.completed(), "completed")
                );
            }

        }
        ```
5. Task #1: create a test for HTTP status code 404 using `https://api.zippopotam.us/us/` URL.
6. Task #2: create tests for another model - zip by city using `http://api.zippopotam.us/us/ma/belmont`.
7. Create a suite for REST-assured tests:
    ```java
    package org.study.tests;
    
    import org.junit.platform.suite.api.IncludeClassNamePatterns;
    import org.junit.platform.suite.api.SelectPackages;
    import org.junit.platform.suite.api.Suite;
    import org.junit.platform.suite.api.SuiteDisplayName;
    
    @Suite
    @SelectPackages("org.study.tests")
    @IncludeClassNamePatterns("^.*RestAssured.*Tests.*$")
    @SuiteDisplayName("REST-assured tests")
    public class RestAssuredSuite {
    }
    ```

## Allure

TODO

## Enhancements

### Dependencies Versions

To better control dependencies versions used in the project, extract them into a separate file:

1. `study-rest-assured-gradle/gradle.properties`:
    ```properties
    junit_version = 5.8.2
    junit_platform_version = 1.8.2
    rest_assured_version = 4.4.0
    jackson_version = 2.13.1
    ```
2. `study-rest-assured-gradle/build.gradle`:
    ```groovy
    dependencies {
        testImplementation "org.junit.jupiter:junit-jupiter-api:$junit_version"
        testRuntimeOnly "org.junit.jupiter:junit-jupiter-engine:$junit_version"
        testImplementation "org.junit.platform:junit-platform-suite-api:$junit_platform_version"
        testRuntimeOnly "org.junit.platform:junit-platform-suite-engine:$junit_platform_version"

        testImplementation "io.rest-assured:rest-assured:$rest_assured_version"
        testImplementation "com.fasterxml.jackson.core:jackson-core:$jackson_version"
        testImplementation "com.fasterxml.jackson.core:jackson-annotations:$jackson_version"
        testImplementation "com.fasterxml.jackson.core:jackson-databind:$jackson_version"
    }
    ```
   
### TODO