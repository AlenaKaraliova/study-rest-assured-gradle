package org.study.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JUnit5Tests {

    @Test
    void testPassing() {
        Assertions.assertEquals(6, 2*3, "multiply operation");
    }

    @Test
    void testFailing() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(7, 2*3, "multiply operation"),
                () -> Assertions.assertTrue(true, "true statement"),
                () -> Assertions.fail("fail here")
        );
    }
}
