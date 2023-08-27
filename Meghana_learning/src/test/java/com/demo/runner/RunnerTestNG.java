package com.demo.runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions (tags= "@tag1" ,
features = "./src/test/resources/Featurefiles/",
glue= "stepdefinationfile",
plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})

public class RunnerTestNG extends AbstractTestNGCucumberTests{
	
	@Override
	@DataProvider (parallel= true)
	public Object[][] scenarios () {
		return super.scenarios();
	}
	
	
}
