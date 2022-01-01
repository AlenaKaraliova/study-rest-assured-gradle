# Description

Let's create an automation framework for Restful API testing. Tools:
- Java `11`,
- Lombok `6.3.0`,
- REST-assured `4.4.0`,
- JUnit5 `5.8.2`,
- Allure `2.9.6`,
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
   
    build
    target
    out
    
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
            Assertions.assertEquals(6, 2 * 3, "multiply operation");
        }
    
        @Test
        void testFailing() {
            Assertions.assertAll(
                () -> Assertions.assertEquals(7, 2 * 3, "multiply operation"),
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
    1. in `build.gradle` file, `dependencies` section, add 
    ```groovy
    testImplementation 'io.rest-assured:rest-assured:4.4.0'
    ```
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
4. Let's create a test that deserializes response json into a POJO:
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
5. Create a test with request body and HTTP method POST:
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
    4. the test with [multiple assertion](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions) will look like this:
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
6. Task: create a test for HTTP status code 404 using `https://api.zippopotam.us/us/` URL.
7. Task: create tests for another model - zip by city using `http://api.zippopotam.us/us/ma/belmont`.
8. Create and execute a suite for REST-assured tests:
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

1. Install Allure Command Line using [instructions](https://docs.qameta.io/allure/#_installing_a_commandline).
2. Check Allure version: `allure --version`.
3. Add and configure Gradle plugin in `build.gradle`:
    ```groovy
    plugins {
        // ...
        id 'io.qameta.allure' version '2.9.6'
    }
   
    allure {
        adapter.autoconfigure = true
        adapter.aspectjWeaver = true
        version = '2.17.1'

        useJUnit5 {
            version = '2.17.1'
        }
    }
    ``` 
4. Rebuild the project and run some tests, for example `RestAssuredSuite`.
5. In Terminal execute `allure serve $replaceWithYourProjectRoot/study-rest-assured-gradle\build\allure-results`.
6. As a result, a new browser window will be opened with a test execution report. It will contain tests and no step details.
7. To create a more informative report, do the following:
    1. create a separate package `allure` in `tests` and copy REST-assured tests there,
    2. rename the tests to comply with `RestAssuredWithAllure*Tests` pattern,
    3. create a test suite `RestAssuredWithAllureSuite` and change package pattern `@SelectPackages("org.study.tests.allure")`,
    4. add a filter for REST-assured HTTP client that generates attachments for Allure:
        * add a dependency `testImplementation 'io.qameta.allure:allure-rest-assured:2.17.1'`,
        * create `RestAssuredWithAllureTestsBase` class with filters configuration:
        ```java
        package org.study.tests.allure;

        import io.qameta.allure.restassured.AllureRestAssured;
        import io.restassured.RestAssured;
        import org.junit.jupiter.api.BeforeAll;

        public class RestAssuredWithAllureTestsBase {

            @BeforeAll
            protected static void configure() {
                RestAssured.filters(new AllureRestAssured());
            }

        }
        ```
        * extend `RestAssuredWithAllure*` tests from this base class,
        * execute `RestAssuredWithAllureSuite` and observe Allure report - now it contains request and response details,
        * more information can be found [here](https://github.com/allure-framework/allure-java).
    5. Task: add Epic, Feature, Story, and Description to the tests, for example:
        ```java
        @Epic("REST-assured")
        @Feature("TODO Application")
        public class RestAssuredWithAllureTodosTests extends RestAssuredWithAllureTestsBase {
    
            @Story("Positive Tests")
            @Description("Add a TODO for a User with JUnit5 Validations")
            @Test
            void validZipPojoTest() {
                // 
            }
        }
        ```  
       Discover the information on **Behaviors** section of Allure report.
    6. Task: add Steps in all tests, for example:
        ```java
        package org.study.tests.allure;
    
        import io.qameta.allure.Description;
        import io.qameta.allure.Epic;
        import io.qameta.allure.Feature;
        import io.qameta.allure.Step;
        import io.qameta.allure.Story;
        import io.restassured.http.ContentType;
        import io.restassured.response.ValidatableResponse;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;
        import org.study.model.TodoRequest;
        import org.study.model.TodoResponse;
    
        import static io.restassured.RestAssured.given;
    
        @Epic("REST-assured")
        @Feature("TODO Application")
        public class RestAssuredWithAllureTodosTests extends RestAssuredWithAllureTestsBase {
    
            private static final String TODO_URL = "https://jsonplaceholder.typicode.com/users/{userId}/todos";
    
            @Story("Positive Tests")
            @Description("Add a TODO for a User with JUnit5 Validations")
            @Test
            void validZipPojoTest() {
                Integer userId = 1;
                String title = "todo #1";
                boolean completed = false;
                TodoRequest todoRequest = new TodoRequest().userId(userId).title(title).completed(completed);
                TodoResponse todoResponse = executePostRequest(TODO_URL, userId, todoRequest)
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
    
            @Step("Execute POST to {url} for user {userId} with TODO title {todoRequest.title} and status {todoRequest.completed}")
            private ValidatableResponse executePostRequest(String url, Integer userId, TodoRequest todoRequest) {
                return given().log().all()
                    .when().body(todoRequest).header("Content-Type", ContentType.JSON).post(url, userId)
                    .then().log().all();
            }
    
        }
        ```
       Generate a new Allure report and find these steps in tests.
8. Task: specify custom templates, which should be placed in `src/main/resources/tpl` folder:
    ```java
    public class RestAssuredWithAllureTestsBase {
   
        @BeforeAll
        protected static void configure() {
        RestAssured.filters(new AllureRestAssured()
            .setRequestTemplate("rest-assured-allure-http-request.ftl")
            .setResponseTemplate("rest-assured-allure-http-response.ftl"));
        }
   
    }
    ```

## Enhancements

### Dependencies Versions

To better control dependencies versions used in the project, extract them into a separate file:

1. `study-rest-assured-gradle/gradle.properties`:
    ```properties
    junit_version = 5.8.2
    junit_platform_version = 1.8.2
    rest_assured_version = 4.4.0
    jackson_version = 2.13.1
    allure_version = 2.17.1
    ```
2. `study-rest-assured-gradle/build.gradle`:
    ```groovy
    allure {
        adapter.autoconfigure = true
        adapter.aspectjWeaver = true
        version = "$allure_version"

        useJUnit5 {
            version = "$allure_version"
        }
    }

    dependencies {
        testImplementation "org.junit.jupiter:junit-jupiter-api:$junit_version"
        testRuntimeOnly "org.junit.jupiter:junit-jupiter-engine:$junit_version"
        testImplementation "org.junit.platform:junit-platform-suite-api:$junit_platform_version"
        testRuntimeOnly "org.junit.platform:junit-platform-suite-engine:$junit_platform_version"

        testImplementation "io.rest-assured:rest-assured:$rest_assured_version"
        testImplementation "com.fasterxml.jackson.core:jackson-core:$jackson_version"
        testImplementation "com.fasterxml.jackson.core:jackson-annotations:$jackson_version"
        testImplementation "com.fasterxml.jackson.core:jackson-databind:$jackson_version"
   
        testImplementation "io.qameta.allure:allure-rest-assured:$allure_version"
    }
    ```
   
### Logging

We will use Lobmok support for logging and create a custom logging template for REST-assured steps.

1. Notice that currently logs in test execution console look like for this request:
    ```text
    Request method:	POST
    Request URI:	https://jsonplaceholder.typicode.com/users/1/todos
    Proxy:			<none>
    Request params:	<none>
    Query params:	<none>
    Form params:	<none>
    Path params:	<none>
    Headers:		Accept=*/*
    Content-Type=application/json
    Cookies:		<none>
    Multiparts:		<none>
    Body:
    {
    "userId": 1,
    "id": null,
    "title": "todo #1",
    "completed": false
    }
    ```
   No timestamp, no pattern, with some information that we might not need.
2. Add this [logback](https://logback.qos.ch/documentation.html) dependency: `implementation "ch.qos.logback:logback-classic:$logback_version"` where `logback_version = 1.2.9`.
3. Add a config file `logback-test.xml` to `resources`:
    ```xml
    <configuration>
        <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
                <pattern>
                    %d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
                </pattern>
            </encoder>
        </appender>

        <logger name="root" level="ERROR"/>
        <logger name="org.study" level="INFO"/>

        <root level="INFO">
            <appender-ref ref="STDOUT"/>
        </root>

    </configuration>
    ```
4. Create `utils` package in `tests`.
5. Create class `RestAssuredRequestFilter`:
    ```java
    package org.study.utils;

    import io.restassured.filter.Filter;
    import io.restassured.filter.FilterContext;
    import io.restassured.response.Response;
    import io.restassured.specification.FilterableRequestSpecification;
    import io.restassured.specification.FilterableResponseSpecification;
    import lombok.extern.slf4j.Slf4j;

    @Slf4j
    public class RestAssuredRequestFilter implements Filter {

        @Override
        public Response filter(
            FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            log.info("Executing {} to URL: {}", requestSpec.getMethod(), requestSpec.getURI());
            if (requestSpec.getHeaders() != null) {
                log.info("Request Headers:\n{}", requestSpec.getHeaders().toString());
            }
            if (requestSpec.getBody() != null) {
                log.info("Request Body:\n{}", requestSpec.getBody().toString());
            }

            return ctx.next(requestSpec, responseSpec);
        }

    }
    ```
   Here, `@Slf4j` Lombok annotation provides `log` object into this class.
6. Create class `RestAssuredResponseFilter`:
    ```java
    package org.study.utils;

    import io.restassured.filter.Filter;
    import io.restassured.filter.FilterContext;
    import io.restassured.response.Response;
    import io.restassured.specification.FilterableRequestSpecification;
    import io.restassured.specification.FilterableResponseSpecification;
    import lombok.extern.slf4j.Slf4j;
    
    @Slf4j
    public class RestAssuredResponseFilter implements Filter {
    
        @Override
        public Response filter(
            FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            Response response = ctx.next(requestSpec, responseSpec);
    
            log.info("Response Status: {} {}", response.getStatusCode(), response.getStatusLine());

            if (response.getBody() != null) {
                log.info("Response Body:\n{}", response.getBody().asPrettyString());
            }
    
            return response;
        }
    
    }
   ```
7. Update `RestAssuredWithAllureTestsBase` to add all necessary filters:
    ```java
    package org.study.tests.allure;

    import io.qameta.allure.restassured.AllureRestAssured;
    import io.restassured.RestAssured;
    import io.restassured.filter.Filter;
    import org.junit.jupiter.api.BeforeAll;
    import org.study.utils.RestAssuredRequestFilter;
    import org.study.utils.RestAssuredResponseFilter;
    
    import java.util.ArrayList;
    import java.util.List;
    public class RestAssuredWithAllureTestsBase {
    
        @BeforeAll
        protected static void configure() {
            List<Filter> filters = new ArrayList<>();
    
            filters.add(new AllureRestAssured()
                .setRequestTemplate("rest-assured-allure-http-request.ftl")
                .setResponseTemplate("rest-assured-allure-http-response.ftl"));
    
            filters.add(new RestAssuredRequestFilter());
            filters.add(new RestAssuredResponseFilter());
    
            RestAssured.filters(filters);
        }
    
    }
    ```
8. Run a test and compare logs format, now they comply with the configured pattern: `%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n`.
    ```text
    12:12:12.000 [Test worker] INFO  o.s.utils.RestAssuredRequestFilter - Executing POST to URL: https://jsonplaceholder.typicode.com/users/1/todos
    12:12:12.000 [Test worker] INFO  o.s.utils.RestAssuredRequestFilter - Request Headers:
    Accept=*/*
    Content-Type=application/json
    12:12:12.000 [Test worker] INFO  o.s.utils.RestAssuredRequestFilter - Request Body:
    {"userId":1,"id":null,"title":"todo #1","completed":false}
    ```
9. But now we have duplicate logging. To remove it - delete `log().all()` from all REST-assured specifications in tests, for example:
    ```java
    public class RestAssuredWithAllureZipTests extends RestAssuredWithAllureTestsBase {
   
        @Step("Execute GET to {url} with parameters {parameters}")
        private ValidatableResponse executeGetRequest(String url, Object... parameters) {
            return given().when().get(url, parameters).then();
        }
    }
    ```
   This refactoring will be easy as we have already extracted these methods in separate steps.
   
### Configuration Properties

1. Let's move environment specific configuration to a properties file.
2. Create file `local-test.properties` in `test/resources`:
    ```properties
    zip.url = https://api.zippopotam.us/us/{zip}
    zip.city.url = https://api.zippopotam.us/us/{state}/{city}
    todo.url = https://jsonplaceholder.typicode.com/users/{userId}/todos
    ```
3. Create class `config/EnvConfigProvider`:
    ```java
    package org.study.config;
    
    import java.io.IOException;
    import java.util.Properties;
    
    public class EnvConfigProvider {
    
        public static String zipURL;
        public static String zipByCityURL;
        public static String todoURL;
    
        private EnvConfigProvider() {
        }
    
        public static void init() {
            Properties properties = new Properties();
            try {
                properties.load(EnvConfigProvider.class.getResourceAsStream("/local-test.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            zipURL = properties.getProperty("zip.url");
            zipByCityURL = properties.getProperty("zip.city.url");
            todoURL = properties.getProperty("todo.url");
        }
   
    }
    ```
4. In `RestAssuredWithAllureTestsBase#configure()` method add line:
    ```java
    public class RestAssuredWithAllureTestsBase {

        @BeforeAll
        protected static void configure() {
            EnvConfigProvider.init();
            // ...
        }
    }
    ```
    This method will init all static configurations and will make them available in all tests that inherit from `RestAssuredWithAllureTestsBase`.
5. Replace URLs in all `RestAssuredWithAllure*` tests, for example:
    ```java
    public class RestAssuredWithAllureZipTests extends RestAssuredWithAllureTestsBase {

        private static final String ZIP_URL = EnvConfigProvider.zipURL;
        private static final String ZIP_BY_CITY_URL = EnvConfigProvider.zipByCityURL;
   
        // ..
    }
    ```
6. Run the improved tests - they should pass.
7. Do the same with `RestAssured*` tests.

### Structure Tests into Packages

1. Create package `junit5` in `tests` and remove corresponding classes `tests/JUnit5*` there.
2. Create package `restassured` in `tests` and remove corresponding classes `tests/RestAssured*` there. 
Do not forget to update package in `RestAssuredSuite`.