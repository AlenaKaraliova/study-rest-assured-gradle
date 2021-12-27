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
