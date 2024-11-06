package com.qa.api.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest{
	
	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{"Naveen", "male", "active"},
			{"Abhi", "male", "inactive"},
			{"Kanchan", "female", "active"}
		};
	}
	
	@Test(dataProvider = "getUserData")
	public void createUserTest(String name, String gender, String status) {
		User user = new User(null, name, StringUtility.getRandomEmailId(), gender, status);
		Response response = restClient.post(BASE_URL_GOREST, "/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
	}
	
	
	@Test(dataProvider = "getUserData")
	public void createUserWithBuilderTest(String name, String gender, String status) {
		//POST
		User user = User.builder()
				.name(name)
				.email(StringUtility.getRandomEmailId())
				.status(status)
				.gender(gender)
				.build();
		
		Response response = restClient.post(BASE_URL_GOREST, "/public/v2/users", user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
		
		//fetch userid:
		String userId = response.jsonPath().getString("id");
		System.out.println("user id ===>" + userId);
		
		//GET:
		Response responseGet = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		Assert.assertEquals(responseGet.jsonPath().getString("name"), user.getName());
		Assert.assertEquals(responseGet.jsonPath().getString("email"), user.getEmail());
	}
	
	
	
	
	
	@Test(enabled = false)
	public void createUserUsingJsonFileTest() {
		File userJsonFile = new File("./src/test/resources/jsons/user.json");
		Response response = restClient.post(BASE_URL_GOREST, "/public/v2/users", userJsonFile, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
		
	}
	
	

}
