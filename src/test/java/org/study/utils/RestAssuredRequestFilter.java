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