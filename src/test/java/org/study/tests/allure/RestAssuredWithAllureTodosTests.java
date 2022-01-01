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
        return given()
            .when().body(todoRequest).header("Content-Type", ContentType.JSON).post(url, userId)
            .then();
    }

}
