package com.qa.api.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserWithDeserializationTest extends BaseTest {

	@Test
	public void getAllUsersTest() {

		Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users", null, null, AuthType.BEARER_TOKEN,
				ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);

		User[] user = JsonUtils.deserialize(response, User[].class);

		System.out.println(Arrays.toString(user));
		System.out.println("Total number of users: "+ user.length);

		for (User p : user) {
			System.out.println("ID : " + p.getId());
			System.out.println("Email: " + p.getEmail());
			System.out.println("Gender: " + p.getGender());
			System.out.println("Status: " + p.getStatus());
			System.out.println("------------");

		}

	}

}
