package tutorialsninja.register;

import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_RF_001 {

	@Test
	public void registerWithMandatoryFields() {
	

		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get("https://tutorialsninja.com/demo/");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.xpath("//a[text()='Register']")).click();
		
		driver.findElement(By.id("input-firstname")).sendKeys("Yogesh");
		driver.findElement(By.id("input-lastname")).sendKeys("Pashankar");
		driver.findElement(By.id("input-email")).sendKeys(generateNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys("8855223311");
		driver.findElement(By.id("input-password")).sendKeys("Yogesh@123");
		driver.findElement(By.id("input-confirm")).sendKeys("Yogesh@123");
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		Assert.assertTrue(driver.findElement(By.linkText("Logout")).isDisplayed());
		
		String expectedHeading = "Your Account Has Been Created!";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='common-success']//h1")).getText(), expectedHeading);
		
		String properDetails1="Congratulations! Your new account has been successfully created!";
		String properDetails2="You can now take advantage of member privileges to enhance your online shopping experience with us.";
		String properDetails3="If you have ANY questions about the operation of this online shop, please e-mail the store owner.";
		String properDetails4="contact us";
		
		String expectedProperDetails = driver.findElement(By.id("content")).getText();
		
		Assert.assertTrue(expectedProperDetails.contains(properDetails1));
		Assert.assertTrue(expectedProperDetails.contains(properDetails2));
		Assert.assertTrue(expectedProperDetails.contains(properDetails3));
		Assert.assertTrue(expectedProperDetails.contains(properDetails4));
		
		driver.findElement(By.xpath("//a[text()='Continue']")).click();
		
		Assert.assertTrue(driver.findElement(By.linkText("Edit your account information")).isDisplayed());
		
		driver.quit();
	}

	public String generateNewEmail() {
		
		Date date = new Date();
		
		
		String dateString = date.toString();// To convert date into string because date is in class format
		String noSpaceStringDate = dateString.replaceAll("\\s", "");//Removing spaces 
		
		String npSpaceAndNoColonsString = noSpaceStringDate.replaceAll("\\:", "");
		System.out.println(npSpaceAndNoColonsString);
		
		String emailwithTimestamp = npSpaceAndNoColonsString+"@gmail.com";
		System.out.println(emailwithTimestamp);
		return emailwithTimestamp; 
	}
}
