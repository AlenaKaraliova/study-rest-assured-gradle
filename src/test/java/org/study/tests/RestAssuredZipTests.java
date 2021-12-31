package org.study.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.study.model.ZipByCityResponse;
import org.study.model.ZipResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredZipTests {

    private static final String ZIP_URL = "https://api.zippopotam.us/us/{zip}";
    private static final String ZIP_BY_CITY_URL = "https://api.zippopotam.us/us/{state}/{city}";

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

    @Test
    void notFoundTest() {
        given().log().all()
            .when().get(ZIP_URL, "")
            .then().log().all()
            .statusCode(404);
    }

    @Test
    void validZipByCityTest() {
        String state = "ma";
        String city = "belmont";
        ZipByCityResponse zipByCityResponse = given().log().all()
            .when().get(ZIP_BY_CITY_URL, state, city)
            .then().log().all()
            .statusCode(200)
            .contentType(ContentType.JSON).extract().as(ZipByCityResponse.class);

        Assertions.assertEquals("Massachusetts", zipByCityResponse.state(), "state");
        Assertions.assertEquals(2, zipByCityResponse.places().size(), "places list size");
    }

}