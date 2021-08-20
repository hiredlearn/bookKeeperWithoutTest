package com.bookkeeper.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bookkeeper.constants.Constants;
import com.bookkeeper.testBase.TestBase;

public class Homepage extends TestBase {

	@FindBy(xpath = "//div[@id='logo']/a")
	WebElement logo;

	@FindBy(xpath = "//div[@id='top-account']/a")
	WebElement profileIcon;

	@FindBy(linkText = "Login")
	WebElement login;

	@FindBy(linkText = "Register")
	WebElement register;

	@FindBy(linkText = "Dashboard")
	WebElement dashboard;

	@FindBy(xpath = "//div[@id='top-download']/a")
	WebElement getStarted;

	@FindBy(xpath = "//span[contains(text(),'Start')]")
	WebElement startCloud;

	@FindBy(xpath = "//div[@id='top-account']/ul[@class='dropdown-menu dropdown-menu-right']/li/a")
	List<WebElement> profileDropdown;

	public Homepage() {
		PageFactory.initElements(driver, this);
	}

	public void reloadPage() {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(logo));
		logo.click();
	}

	public LoginPage navigateToLoginPage() {
		clickOnProfileIcon();

		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(login));
		login.click();
		return new LoginPage();
	}

	public RegisterPage navigateToRegisterPage() {
		clickOnProfileIcon();

		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(register));
		register.click();
		return new RegisterPage();
	}

	public DashboardPage navigateToDashboardPage(String CompanyName) {
		clickOnProfileIcon();

		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(dashboard));
		dashboard.click();

		driver.findElements(
				By.xpath("//a[contains(text(),'" + CompanyName + "')]/../following-sibling::td[2]/button[1]")).stream()
				.findFirst().get().click();
		return new DashboardPage();
	}

	public void getStarted() {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.elementToBeClickable(getStarted));
		getStarted.click();
	}

	public void launchCloud() {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.elementToBeClickable(startCloud));
		startCloud.click();
	}

	public void clickOnProfileIcon() {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.elementToBeClickable(profileIcon));
		profileIcon.click();
	}

	public boolean verifyDropdownDisplayed() {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT)
				.until(ExpectedConditions.visibilityOf(profileDropdown.get(0)));
		return profileDropdown.get(0).isDisplayed();
	}

	public List<String> getDropdownList() {
		return profileDropdown.stream().map(ele -> ele.getText()).collect(Collectors.toList());
	}

}
