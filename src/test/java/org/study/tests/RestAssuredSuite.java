package org.study.tests;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages("org.study.tests")
@IncludeClassNamePatterns("^.*RestAssured.*Tests.*$")
@SuiteDisplayName("REST-assured tests")
public class RestAssuredSuite {
}
