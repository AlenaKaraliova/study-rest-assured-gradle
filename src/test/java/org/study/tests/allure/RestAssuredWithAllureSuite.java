package org.study.tests.allure;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages("org.study.tests.allure")
@IncludeClassNamePatterns("^.*RestAssured.*Tests.*$")
@SuiteDisplayName("REST-assured tests with Allure reporting")
public class RestAssuredWithAllureSuite {
}
