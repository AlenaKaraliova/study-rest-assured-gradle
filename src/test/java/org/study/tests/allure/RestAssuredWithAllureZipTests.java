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
import org.study.model.ZipByCityResponse;
import org.study.model.ZipResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;

@Epic("REST-assured")
@Feature("ZIP Code Application")
public class RestAssuredWithAllureZipTests extends RestAssuredWithAllureTestsBase {

    private static final String ZIP_URL = "https://api.zippopotam.us/us/{zip}";
    private static final String ZIP_BY_CITY_URL = "https://api.zippopotam.us/us/{state}/{city}";

    @Story("Positive Tests")
    @Description("Places by ZIP Code with Json Path Validations")
    @Test
    void validZipTest() {
        String zip = "33162";
        executeGetRequest(ZIP_URL, zip)
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("'post code'", equalTo(zip))
            .body("places[0]", aMapWithSize(5))
            .body("places[0].state", equalTo("Florida"))
            .body("places[0].'state abbreviation'", equalTo("FL"));
    }

    @Story("Positive Tests")
    @Description("Places by ZIP Code with JUnit5 Validations")
    @Test
    void validZipPojoTest() {
        String zip = "33162";
        ZipResponse zipResponse = executeGetRequest(ZIP_URL, zip)
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract().as(ZipResponse.class);

        Assertions.assertEquals(zip, zipResponse.postCode(), "post code");
        Assertions.assertEquals(1, zipResponse.places().size(), "places list size");
        Assertions.assertEquals("Florida", zipResponse.places().get(0).state(), "state");
        Assertions.assertEquals("FL", zipResponse.places().get(0).stateAbbreviation(), "state abbreviation");
    }

    @Story("Negative Tests")
    @Description("Not Found")
    @Test
    void notFoundTest() {
        executeGetRequest(ZIP_URL, "").statusCode(404);
    }

    @Story("Positive Tests")
    @Description("ZIP Codes by City with JUnit5 Validations")
    @Test
    void validZipByCityTest() {
        String state = "ma";
        String city = "belmont";
        ZipByCityResponse zipByCityResponse = executeGetRequest(ZIP_BY_CITY_URL, state, city)
            .statusCode(200)
            .contentType(ContentType.JSON)
            .contentType(ContentType.JSON).extract().as(ZipByCityResponse.class);

        Assertions.assertEquals("Massachusetts", zipByCityResponse.state(), "state");
        Assertions.assertEquals(2, zipByCityResponse.places().size(), "places list size");
    }

    @Step("Execute GET to {url} with parameters {parameters}")
    private ValidatableResponse executeGetRequest(String url, Object... parameters) {
        return given().when().get(url, parameters).then();
    }

}