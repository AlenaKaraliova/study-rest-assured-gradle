package org.study.tests.allure;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import org.junit.jupiter.api.BeforeAll;
import org.study.config.EnvConfigProvider;
import org.study.utils.RestAssuredRequestFilter;
import org.study.utils.RestAssuredResponseFilter;

import java.util.ArrayList;
import java.util.List;

public class RestAssuredWithAllureTestsBase {

    @BeforeAll
    protected static void configure() {
        EnvConfigProvider.init();
        
        List<Filter> filters = new ArrayList<>();

        filters.add(new AllureRestAssured()
            .setRequestTemplate("rest-assured-allure-http-request.ftl")
            .setResponseTemplate("rest-assured-allure-http-response.ftl"));

        filters.add(new RestAssuredRequestFilter());
        filters.add(new RestAssuredResponseFilter());

        RestAssured.filters(filters);
    }

}
