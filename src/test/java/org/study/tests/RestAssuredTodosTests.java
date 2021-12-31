package org.study.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.study.model.TodoRequest;
import org.study.model.TodoResponse;

import static io.restassured.RestAssured.given;

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
