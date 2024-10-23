package com.qa.api.schemavalidation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserAPISchemaTest extends BaseTest{
	
	
	@Test
	public void userAPISchemaTest() {
		
//		RestAssured.given()
//				.baseUri("https://gorest.co.in")
//				.header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
//				.when()
//				.get("/public/v2/users/7484359")
//				.then()
//				.assertThat()
//				.statusCode(200)
//				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user-schema.json"));
		
		//POST
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
		
		
		Response responseGET = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		
		Assert.assertEquals(SchemaValidator.validateSchema(responseGET, "schema/user-schema.json"), true);

				
	}

}
