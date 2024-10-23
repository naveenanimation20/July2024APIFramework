package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest{
	
	
	@Test
	public void deleteUserWithBuilderTest() {
		
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
				
		
		//3. DELETE: update the same user using the same user id
		Response responseDelete = restClient.delete(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseDelete.getStatusCode(), 204);
		
		//4. GET: recheck and fetch the user with the same user id
		Response responseGetAfterDelete = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGetAfterDelete.getStatusCode(), 404);
		Assert.assertEquals(responseGetAfterDelete.jsonPath().getString("message"), "Resource not found");
		
	}

}
