package SDtest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class prueba2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.setProperty("driver.chrome.driver", "C:\\Selenium\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
			String url = "http://demo.guru99.com/test/newtours/";
	        driver.get(url);
	}
	}


