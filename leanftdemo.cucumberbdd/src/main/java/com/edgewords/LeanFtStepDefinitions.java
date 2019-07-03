package com.edgewords;

import static org.junit.Assert.assertTrue;

import com.hp.lft.report.*;
import com.hp.lft.sdk.*;
import com.hp.lft.sdk.WaitUntilTestObjectState.WaitUntilEvaluator;
import com.hp.lft.sdk.web.*;
import com.hp.lft.verifications.Verify;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LeanFtStepDefinitions {

	Browser browser;
	
	public LeanFtStepDefinitions() {
	}
	
	
	@Before
	public void setUp() throws GeneralLeanFtException {
		browser = BrowserFactory.launch(BrowserType.FIREFOX);
	}
	
	@After
	public void tearDown() throws GeneralLeanFtException {
		browser.closeAllTabs();
	}
	// Implementation of feature’s steps

	@Given("^I am on the Edgewords Shop Demo homepage$")
	public void i_am_on_the_Edgewords_Shop_Demo_homepage() throws Throwable {
		browser.sync();

		browser.navigate("http://www.edgewordstraining.co.uk/demo-site/");


	}

	@When("^I search for \"([^\"]*)\"$")
	public void i_search_for(String arg1) throws Throwable {

		EditField sEditField = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.accessibilityName("")
				.name("s")
				.placeholder("Search products…")
				.tagName("INPUT")
				.type("search")
				.index(0).build());
		sEditField.setValue("cap");

		Button searchButton = browser.describe(Button.class, new ButtonDescription.Builder()
				.accessibilityName("")
				.buttonType("submit")
				.name("Search")
				.role("")
				.tagName("BUTTON")
				.index(0).build());
		searchButton.click();

		
	}

	@Then("^a \"([^\"]*)\" shown shown in the results$")
	public void a_shown_shown_in_the_results(String arg1) throws Throwable {
		
		//Wait for results
		WebElement breadCrumbNav = browser.describe(WebElement.class, new WebElementDescription.Builder()
				.className("woocommerce-breadcrumb")
				.tagName("NAV").build());
		
		boolean breadCrumbNavExists = breadCrumbNav.waitUntilExists(3000);
		if(!breadCrumbNavExists) {
			Reporter.reportEvent("Nav error", "Didnt find breadrumbs - Did we make it to search results", Status.Failed);
			assertTrue("Breadcrumbs not loaded - didn't make it to search results?", breadCrumbNavExists);
		}
		
		

		
//		WebElement capWebElement = browser.describe(WebElement.class, new WebElementDescription.Builder()
//				.innerText("Cap")
//				.tagName("H1").build());
		
		//Identifying the product returned by the inner text wont work if something incorrect is returned. grab the first H1 inside MAIN instead.
		WebElement topResult = browser.describe(WebElement.class, new WebElementDescription.Builder()
				.cssSelector("main#main h1:nth-of-type(1)")
				.build());
		
		
		Verify.areEqual("Cap", topResult.getInnerText(), "Verification", "Verify property: innerText");
	}
}