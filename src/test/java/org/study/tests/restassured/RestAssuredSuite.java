package org.study.tests.restassured;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages("org.study.tests.restassured")
@IncludeClassNamePatterns("^.*RestAssured.*Tests.*$")
@SuiteDisplayName("REST-assured tests")
public class RestAssuredSuite {
}
