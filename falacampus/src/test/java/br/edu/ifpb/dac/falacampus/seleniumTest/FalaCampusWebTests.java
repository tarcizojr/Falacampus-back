package br.edu.ifpb.dac.falacampus.seleniumTest;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

class FalaCampusWebTests {
	
	private WebDriver driver;
	
	@BeforeEach
	void setup() throws Exception{
		System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
		driver = new ChromeDriver();	
	}
	
	@AfterEach
	void tearDown() {
		driver.quit();
	}
	
	@Test
	@DisplayName("Open web")
	void home() throws InterruptedException {	
		driver.get("http://localhost:3000");
		assertTrue(driver.getTitle().contentEquals("Fala Campus"));
		Thread.sleep(3000);

		//driver.navigate().to("http://localhost:3000");
	}

}
