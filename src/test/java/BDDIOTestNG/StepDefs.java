package BDDIOTestNG;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepDefs {

	public static WebDriver driver;
	public static WebDriverWait wait;

	@Given("^user is already on Login Page$")
	public void user_already_on_login_page() {
		String currDir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", currDir + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 40, 250);
		driver.get("https://shasha-quality-assurance.web.app/campaigns");
	}

	@Then("^user enters \"(.*)\" and \"(.*)\" and clicks on login$")
	public void user_enters_username_and_password(String username, String password) {
		waitForElementVisibility(By.id("user-email")).click();
		waitForElementVisibility(By.id("user-email")).sendKeys(username);
		waitForElementVisibility(By.id("user-password")).click();
		waitForElementVisibility(By.id("user-password")).sendKeys(password);
		waitForElementVisibility(By.xpath("//button[@type='submit']")).click();
	}

	@Then("^user is on home page$")
	public void user_is_on_hopme_page() {
		String headerTitle = waitForElementVisibility(By.xpath("//div[text()='Campaigns']")).getText();
		Assert.assertEquals("Campaigns", headerTitle);
		String listTitleContent = waitForElementVisibility(By.xpath("//div[text()='My Campaigns']")).getText();
		Assert.assertEquals("My Campaigns", listTitleContent);
	}

	@Given("^click Create Campaign redirected to Campaign page$")
	public void click_Create_Campaign_redirected_to_Campaign_page() {
		waitForElementVisibility(By.xpath("//a[contains(@class, 'v-btn--large')]/div/i")).click();
		waitForJSToLoad();
		waitForElementVisibility(By.xpath("//a/div[text()='Cancel']"));
	}

	@Then("^page have save and cancel buttons$")
	public void page_have_save_and_cancel_buttons() {
		Assert.assertTrue(isElementPresent(By.xpath("//a/div[text()='Cancel']")));
		Assert.assertTrue(isElementPresent(By.xpath("//button/div[text()='Save']")));
	}

	@Then("^period click have week start date with list of upcoming weeks showing weeknumber, start, end dates$")
	public void period_click_have_week_start_date_with_list_of_upcoming_weeks() {
		waitForElementVisibility(By.xpath("//div[@class='v-select__slot']")).click();
		String[] formatDate = formattedDates();
		Assert.assertTrue(isElementPresent(By.xpath(
				"//div[@id='schedule-container']//div[@role='listitem']/a//div[@class='schedule-name'][span[text()='Next Week'] and span[text()='"
						+ formatDate[0] + "']]")));
		Assert.assertTrue(isElementPresent(By.xpath(
				"//div[@id='schedule-container']//div[@role='listitem']/a//div[@class='schedule-name'][span[contains(text(),'Week 2')] and span[text()='"
						+ formatDate[1] + "']]")));
		Assert.assertTrue(isElementPresent(By.xpath(
				"//div[@id='schedule-container']//div[@role='listitem']/a//div[@class='schedule-name'][span[contains(text(), 'Week 3')] and span[text()='"
						+ formatDate[2] + "']]")));
		Assert.assertTrue(isElementPresent(By.xpath(
				"//div[@id='schedule-container']//div[@role='listitem']/a//div[@class='schedule-name'][span[contains(text(),'Week 4')] and span[text()='"
						+ formatDate[3] + "']]")));
	}

	@Then("select the schedules")
	public void select_the_schedules() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitForElementVisibility(By.xpath(
				"//div[@class='schedule-name'][span[text()='Next Week']]/ancestor::a//div[@class='v-input__control']"))
						.click();
		;
		waitForElementVisibility(By.xpath(
				"//div[@class='schedule-name'][span[contains(text(),'Week 2')]]/ancestor::a//div[@class='v-input__control']"))
						.click();
		waitForElementVisibility(By.cssSelector("div.v-btn__content .green--text")).click();

	}

	@Then("click the media button")
	public void click_the_media_button() {
		waitForElementVisibility(By.xpath("//span[text()='Media']")).click();
	}

	@Then("assign images to my campaign")
	public void assign_images_to_my_campaign() {
		waitForElementVisibility(By.xpath("//div[@class='v-responsive v-image primary']")).click();
		waitForElementVisibility(By.xpath("//*[@class='v-badge__badge green']"));
		Assert.assertTrue(isElementPresent(By.xpath("//*[@class='v-badge__badge green']")));
	}

	@Then("click save the campaign")
	public void click_save_the_campaign() {
		String campaignName = waitForElementVisibility(
				By.xpath("//input[@aria-label='Campaign Name (only you can see it)']")).getAttribute("value");
		waitForElementVisibility(By.xpath("//button/div[text()='Save']")).click();
		waitForElementVisibility(By
				.xpath("//a[@data-testid='itemRow']//div[@class='v-list__tile__content'][div[normalize-space(text())='"
						+ campaignName + "']]/following-sibling::div//div[text()='New']"));
		Assert.assertTrue(isElementPresent(By
				.xpath("//a[@data-testid='itemRow']//div[@class='v-list__tile__content'][div[normalize-space(text())='"
						+ campaignName + "']]/following-sibling::div//div[text()='New']")));
	}

	public boolean isElementPresent(By locator) {
		return driver.findElements(locator).size() > 0;
	}

	public WebElement waitForElementVisibility(WebElement element) {
		return wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
	}

	public WebElement waitForElementVisibility(final By by) {

		return wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(by);
					return element.isDisplayed() ? element : null;
				} catch (StaleElementReferenceException detachedFromDom) {
					throw new NotFoundException("Element detached from DOM: " + detachedFromDom, detachedFromDom);
				}
			}
		});
	}

	public void clickElementThroughJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public void waitForJSToLoad() {
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		});
	}

	public String[] formattedDates() {
		LocalDate dt = LocalDate.now();
		LocalDate edate1 = null;
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("MMM dd")
				.toFormatter();
		String[] formattedDates = new String[4];
		for (int i = 0; i < 4; i++) {
			dt = dt.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
			edate1 = dt.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
			formattedDates[i] = formatter.format(dt) + " - " + formatter.format(edate1);
		}
		return formattedDates;
	}

}