package com.bookkeeper.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bookkeeper.constants.Constants;
import com.bookkeeper.testBase.TestBase;

public class RegisterPage extends TestBase {

	@FindBy(id = "register-form-first-name")
	WebElement name;

	@FindBy(id = "register-form-email")
	WebElement email;

	@FindBy(id = "register-form-password")
	WebElement password;

	@FindBy(name = "agreement")
	WebElement agreement;

	@FindBy(id = "register-form-submit")
	WebElement register;

	@FindBy(xpath = "//div[@class='alert alert-danger fade in alert-dismissable']")
	List<WebElement> errormessages;

	public RegisterPage() {
		PageFactory.initElements(driver, this);
	}

	public void addName(String firstName) {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(name));
		name.clear();
		name.sendKeys(firstName);
	}

	public void addEmail(String emailId) {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(email));
		email.clear();
		email.sendKeys(emailId);
	}

	public void enterPassword(String pwd) {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(password));
		password.clear();
		password.sendKeys(pwd);
	}

	public void updateAgreement(boolean isAgree) {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(agreement));
		System.out.println(agreement.isSelected());
		if (isAgree) {
			if (!agreement.isSelected())
				agreement.click();
		} else {
			if (agreement.isSelected())
				agreement.click();
		}
	}

	public void submitForm() {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.elementToBeClickable(register));
		Actions act = new Actions(driver);
		act.moveToElement(register);
		register.click();
	}

	public void clickOnRecaptcha() {
		driver.switchTo().frame("a-9wt0e8vkopnm");
		driver.findElement(By.xpath("//span[@id='recaptcha-anchor']")).click();
	}

	public List<String> getErrorMessage() {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(errormessages.get(0)));
		return errormessages.stream().map(msg -> msg.getText()).collect(Collectors.toList());
	}
}
