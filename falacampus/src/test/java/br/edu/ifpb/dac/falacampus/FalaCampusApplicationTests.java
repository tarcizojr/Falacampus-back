package br.edu.ifpb.dac.falacampus;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

class FalaCampusApplicationTests {

	@Test
	void contextLoads() {
		System.out.print("aqui");
		System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:3000");
	}

}
