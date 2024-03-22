package com.mynta;
import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StudioEthnicWear {
	public static WebDriver driver;
	public static Actions actions;
	public static WebElement ethnicOption ;
	
	@Given("Launch the Application")
	public void launch_the_application() {
	  WebDriverManager.edgedriver().setup();
	  EdgeOptions options=new EdgeOptions();
	  options.addArguments("start-maximized");
	  driver =new EdgeDriver(options);
	  driver.get("https://www.myntra.com");
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  actions=new Actions(driver);
	  
	}

	@When("users Click on the Studio")
	public void click_on_the_studio() {
		WebElement studio = driver.findElement(By.xpath("//a[text()='Studio']"));
        studio.click();
	}

	@Then("Verify the Ethnic Option is displayed")
	public void verify_the_ethnic_option_is_displayed() throws Exception {
		Thread.sleep(7000);
		ethnicOption = driver.findElement(By.xpath("//img[contains(@src,'Eathnic')]"));
		WebElement popup = driver.findElement(By.xpath("//div[@class='ripple']/parent::div"));
		popup.click();
		Assert.assertTrue(ethnicOption.isDisplayed());
	}

	@When("User click on the Ethnics")
	public void user_click_on_the_ethnics() {
	    ethnicOption.click();
	}

	@Then("Validate the Ethnics wears in that page")
	public void validate_the_ethnics_wears_in_that_page() throws InterruptedException {
		WebElement EthnicPage = driver.findElement(By.xpath("//div[text()=' Followers ']/parent::div/parent::div/div"));
		String Page = EthnicPage.getText();
		Assert.assertTrue(Page.contains("Ethnic"));
		List<WebElement> elements1 = driver.findElements(By.xpath("//div[contains(text(),'ago')]"));
		System.out.println(elements1.size());
		List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@class,'1tsbwee')]"));
			for(int i=0;i<elements.size();i++) {
				String ele = elements.get(i).getText();
				String element = ele.toLowerCase();
				if(ele.contains("...See more")){
					WebElement seemoreOption = driver.findElement(By.xpath("//span[contains(text(),'"+ele.substring(0, 10)+"')]/following-sibling::span[contains(text(),'more')]"));
					actions.moveToElement(seemoreOption).build().perform();
					Thread.sleep(2000);
					seemoreOption.click();
					Thread.sleep(2000);
					WebElement text = driver.findElement(By.xpath("//span[contains(text(),'"+ele.substring(0, 10)+"')]/parent::div"));
					String text1 = text.getText();
					String text2 = text1.toLowerCase();
					if(text2.contains("ethnic") || text2.contains("kurta") || text2.contains("saree")) {
						System.out.println(text1+": Valid Result\n");
					}
					else if (!ele.isEmpty()) {
						System.out.println(text1+": Invalid Result\n");
					}
				}
				else {
					if(element.contains("ethnic") || element.contains("kurta") || element.contains("saree")) {
						System.out.println(ele+": Valid Result\n");
					}
					else if (!ele.isEmpty()) {
						System.out.println(ele+": Invalid Result\n");
					}
				}
		}
	}
}
