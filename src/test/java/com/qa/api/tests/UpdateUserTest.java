package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest{
	
	
	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{"Naveen", "male", "active", "inactive", "NaveenAutomation" },
			{"Abhi", "male", "inactive", "active", "abhiautomation"},
			{"Kanchan", "female", "active", "inactive", "kanchanautomation"}
		};
	}
	
	
	@Test(dataProvider = "getUserData")
	public void UpdateUserWithBuilderTest(String name, String gender, String status, String updatedStatus, String updatedName ) {
		
		//1. POST: create a user
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
		
		
		//2. GET: fetch the same user using the user id
		Response responseGet = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		
		//update the user details using the setter:
		user.setStatus(updatedStatus);
		user.setName(updatedName);
		
		//3. PUT: update the same user using the same user id
		Response responsePUT = restClient.put(BASE_URL_GOREST, "/public/v2/users/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePUT.getStatusCode(), 200);
		Assert.assertEquals(responsePUT.jsonPath().getString("id"), userId);
		Assert.assertEquals(responsePUT.jsonPath().getString("status"), user.getStatus());
		Assert.assertEquals(responsePUT.jsonPath().getString("gender"), user.getGender());
		
	}
	
	
	

}
