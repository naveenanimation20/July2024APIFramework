package com.qa.api.schemavalidation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.SchemaValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPISchemaTest extends BaseTest{

	@Test
	public void productsAPISchemaTest() {
		
//		RestAssured.given()
//				.baseUri("https://fakestoreapi.com")
//				.when()
//				.get("/products")
//				.then()
//				.assertThat()
//				.statusCode(200)
//				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/product-schema.json"));
		
		
		
		Response response = restClient.get(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		Assert.assertEquals(SchemaValidator.validateSchema(response, "schema/product-schema.json"), true);
		
	}
	
	
	
	
}
