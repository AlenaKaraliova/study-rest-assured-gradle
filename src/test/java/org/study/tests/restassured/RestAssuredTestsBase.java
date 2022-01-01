package org.study.tests.restassured;

import org.junit.jupiter.api.BeforeAll;
import org.study.config.EnvConfigProvider;

public class RestAssuredTestsBase {

    @BeforeAll
    protected static void configure() {
        EnvConfigProvider.init();
    }

}
