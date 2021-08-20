package com.bookkeeper.testCases;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bookkeeper.pages.DashboardPage;
import com.bookkeeper.pages.Homepage;
import com.bookkeeper.pages.LoginPage;
import com.bookkeeper.pages.RegisterPage;
import com.bookkeeper.testBase.TestBase;
import com.bookkeeper.utils.TestUtility;

public class SampleTest extends TestBase {
	Homepage homepage;
	LoginPage loginPage;
	RegisterPage registerPage;
	DashboardPage dashboardPage;
	TestUtility testUtil;

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		testUtil = new TestUtility();
		initialization("manager");
		log.info("Application Launched Successfully");
		dashboardPage = new DashboardPage();
		dashboardPage.login();
	}


}
