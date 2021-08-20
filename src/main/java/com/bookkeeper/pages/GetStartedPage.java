package com.bookkeeper.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bookkeeper.testBase.TestBase;

public class GetStartedPage extends TestBase{

	@FindBy(id = "company_name")
	WebElement companyName;
	
	@FindBy(id = "create_company")
	WebElement createCompany;
	
	public GetStartedPage() {
		PageFactory.initElements(driver, this);
	}
}
