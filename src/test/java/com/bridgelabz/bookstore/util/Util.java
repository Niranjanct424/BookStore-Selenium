package com.bridgelabz.bookstore.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

@Component
public class Util {
	
	public WebDriver getWebdriver()
	{
		return new ChromeDriver();
	}

}
