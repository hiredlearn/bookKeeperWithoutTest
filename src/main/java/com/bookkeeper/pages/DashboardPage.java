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

public class DashboardPage extends TestBase {

	@FindBy(xpath = "//div[@id='sidenav-collapse-main']//a/span")
	List<WebElement> sideNav;

	@FindBy(name = "email")
	WebElement email;

	@FindBy(name = "password")
	WebElement password;

	@FindBy(xpath = "//*[@type='submit']")
	WebElement submitButton;

	@FindBy(xpath = "//div[@id='navbarSupportedContent']//img[@class='user-img']")
	WebElement profileIcon;

	@FindBy(xpath = "//div[@class='dropdown-menu dropdown-menu-right show']/a/span")
	List<WebElement> profileDropdown;

	@FindBy(xpath = "//div[@id='app']//td/a")
	List<WebElement> roles;

	public DashboardPage() {
		PageFactory.initElements(driver, this);
	}

	public void login() {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.elementToBeClickable(email));
		System.out.println("login with userId: " + userId + " and Pwd : " + pwd);
		email.sendKeys(userId);
		password.sendKeys(pwd);
		submitButton.click();
	}

	public List<String> getSideNavText() {
		return sideNav.stream().map(ele -> ele.getText()).collect(Collectors.toList());
	}

	public void navigateToSideMenuItem(String sideNavOption) {
		sideNav.stream().filter(ele -> ele.getText().contains(sideNavOption)).findFirst().get().click();
	}

	public void profileDropDown(String option) {
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.elementToBeClickable(profileIcon));
		profileIcon.click();

		new WebDriverWait(driver, Constants.EXPLICIT_WAIT)
				.until(ExpectedConditions.visibilityOf(profileDropdown.get(0)));
		profileDropdown.stream().filter(ele -> ele.getText().equals(option)).findFirst().get().click();
	}

	public List<String> getRoles() {
		profileDropDown("Roles");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new WebDriverWait(driver, Constants.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(roles.get(0)));
		return roles.stream().map(ele -> ele.getText()).collect(Collectors.toList());
	}

}
