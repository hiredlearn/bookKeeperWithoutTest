package com.bookkeeper.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bookkeeper.constants.Constants;
import com.bookkeeper.testBase.TestBase;

public class LoginPage extends TestBase {

	@FindBy(id = "email")
	WebElement email;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "login-form-submit")
	WebElement submitButton;

	@FindBy(xpath = "//div[@class='divcenter']/div[@class='alert alert-danger fade in alert-dismissable']")
	List<WebElement> errorMsg;

	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	public Homepage performLogin() {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(email));
		email.sendKeys(userId);
		password.sendKeys(pwd);
		System.out.println("Solve captcha manually in 1 min");
		// for captcha
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		submitButton.click();
		return new Homepage();
	}

	public List<String> getErrorMessage() {
		return errorMsg.stream().map(msg -> msg.getText()).collect(Collectors.toList());
	}

	public void submit() {
		submitButton.click();
	}
}
