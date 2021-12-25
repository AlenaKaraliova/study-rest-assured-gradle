Let's create an automation framework for testing Restful API. Tools used:
- RestAssured,
- JUnit5,
- Allure,
- Gradle.

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
6. The initial project structure will be:
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

7. For testing we will not need `src/main` directory. Delete it.
8. Create file `.gitignore` in the project root with the following context, so it will be on the same level as `build.gradle` file:
   ```
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
   Let's create an automation framework for testing Restful API. Tools used:
   - RestAssured,
   - JUnit5,
   - Allure,
   - Gradle.
   ```
10. 

 
