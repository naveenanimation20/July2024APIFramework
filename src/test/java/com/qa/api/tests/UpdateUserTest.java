package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest{
	
	
	@Test
	public void UpdateUserWithBuilderTest() {
		
		//1. POST: create a user
		User user = User.builder()
				.name("apiname")
				.email(StringUtility.getRandomEmailId())
				.status("active")
				.gender("female")
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
		user.setStatus("inactive");
		user.setGender("male");
		
		//3. PUT: update the same user using the same user id
		Response responsePUT = restClient.put(BASE_URL_GOREST, "/public/v2/users/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePUT.getStatusCode(), 200);
		Assert.assertEquals(responsePUT.jsonPath().getString("id"), userId);
		Assert.assertEquals(responsePUT.jsonPath().getString("status"), user.getStatus());
		Assert.assertEquals(responsePUT.jsonPath().getString("gender"), user.getGender());
		
	}
	
	
	

}
