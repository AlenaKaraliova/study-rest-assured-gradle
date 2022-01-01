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