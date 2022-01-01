package org.study.tests.junit5;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(JUnit5Tests.class)
public class JUnit5Suite {
}
