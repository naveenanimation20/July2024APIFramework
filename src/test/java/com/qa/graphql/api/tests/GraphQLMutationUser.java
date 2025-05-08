package com.qa.graphql.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class GraphQLMutationUser {
	
	private static final String GRAPHQL_ENDPOINT = "https://gorest.co.in/public/v2/graphql";

	private String getRandomEmailId() {
		return "graphqlautomation"+ System.currentTimeMillis() + "@graph.com";
	}
	
	
	@Test
	public void mutateUserWithGraphQLTest() {
		String newEmailId = getRandomEmailId();
		String mutation = "mutation CreateUser {\n"
				+ "    createUser(\n"
				+ "        input: {\n"
				+ "            name: \"naveen\"\n"
				+ "            email: \""+newEmailId+"\"\n"
				+ "            gender: \"male\"\n"
				+ "            status: \"active\"\n"
				+ "        }\n"
				+ "    ) {\n"
				+ "        user {\n"
				+ "            email\n"
				+ "            gender\n"
				+ "            id\n"
				+ "            name\n"
				+ "            status\n"
				+ "        }\n"
				+ "    }\n"
				+ "}";
		
		
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("query", mutation);
		
		given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
		.body(requestBody)
	.when()
		.post(GRAPHQL_ENDPOINT)
	.then().log().all()
		.assertThat()
			.statusCode(200)
			.body("data.createUser.user.id", notNullValue())
			.body("data.createUser.user.name", equalTo("naveen"))
			.body("data.createUser.user.gender", equalTo("male"))
			.body("data.createUser.user.status", equalTo("active"))
			.body("data.createUser.user.email", equalTo(newEmailId));
		
	}
	
	
	private String readGraphQLQuery(String filePath) {
		try {
			return new String (Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Test
	public void mutateUserWithGraphQLAndFileTest() {
		
		//1. mutate(create) a suer using mutation:
		String newEmailId = getRandomEmailId();
		
		String mutationQuery = readGraphQLQuery("src/test/resources/graphql/createUserMutation.graphql");
		String mutation = String.format(mutationQuery, newEmailId);
				
		
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("query", mutation);
		
		Response response = given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
		.body(requestBody)
	.when()
		.post(GRAPHQL_ENDPOINT);
		
		int userId = response.jsonPath().getInt("data.createUser.user.id");
		System.out.println("user id is====>" + userId);
		
		response.then().log().all()
		.assertThat()
			.statusCode(200)
			.body("data.createUser.user.id", notNullValue())
			.body("data.createUser.user.name", equalTo("naveen"))
			.body("data.createUser.user.gender", equalTo("male"))
			.body("data.createUser.user.status", equalTo("active"))
			.body("data.createUser.user.email", equalTo(newEmailId));
		
		
		System.out.println("=================================");
		
		//2. query the user with the same userId and validate the user:
		
		String graphQLFile = "src/test/resources/graphql/getUserQuery.graphql";
		String query = readGraphQLQuery(graphQLFile);
				
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("userId", userId);
		
		
		Map<String, Object> requestBodyQuery = new HashMap<String, Object>();
		requestBodyQuery.put("query", query);
		requestBodyQuery.put("variables", variables);
		
		given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
			.body(requestBodyQuery)
		.when()
			.post(GRAPHQL_ENDPOINT)
		.then().log().all()
			.assertThat()
				.statusCode(200)
				.body("data.user.id", equalTo(userId))
				.body("data.user.name", equalTo("naveen"))
				.body("data.user.status", equalTo("active"))
				.body("data.user.email", equalTo(newEmailId));
		
		
	}
	

}
