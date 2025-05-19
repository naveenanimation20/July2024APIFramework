package com.qa.api.amadeus.tests;

import java.util.Map;

import org.apache.groovy.util.Maps;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest{
	
	private String token;
	
	@BeforeMethod
	public String getOAuth2Token() {
		token =  RestAssured.given()
				.formParam("client_id", ConfigManager.get("clientId"))
				.formParam("client_secret", ConfigManager.get("clientSecret"))
				.formParam("grant_type", ConfigManager.get("grantType"))
				.post(BASE_URL_AMADEUS+AMADEUS_TOKEN_ENDPOINT)
				.then()
				.extract()
				.path("access_token");
		System.out.println("token -->"+ token);
		ConfigManager.set("bearerToken", token);
		return token;
	}
	
	
	
	
	@Test
	public void getFlightDetailsTest() {
		
				
		Map<String, String> queryParams = Maps.of("origin", "PAR", "maxPrice", "200");
		Response response = restClient.get(BASE_URL_AMADEUS, "/v1/shopping/flight-destinations", queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	

}
