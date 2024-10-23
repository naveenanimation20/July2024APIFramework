package com.qa.products.api.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithJsonPathValidator extends BaseTest{
	
	
	@Test
	public void getProductTest() {
		
		Response response = restClient.get(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
		
		Assert.assertEquals(response.statusCode(), 200);
		
		List<Number> prices = JsonPathValidator.readList(response, "$[?(@.price > 50)].price");
		System.out.println(prices);
		
		List<Number> ids = JsonPathValidator.readList(response, "$[?(@.price > 50)].id");
		System.out.println(ids);
		
		List<Double> rates = JsonPathValidator.readList(response, "$[?(@.price > 50)].rating.rate");
		System.out.println(rates);
		
		List<Integer> count = JsonPathValidator.readList(response, "$[?(@.price > 50)].rating.count");
		System.out.println(count);

		
		//get map:
		List<Map<String, Object>> jewleryList= JsonPathValidator.readListOfMaps(response, "$[?(@.category == 'jewelery')].['title','price']");
		
		System.out.println(jewleryList.size());
		
		for(Map<String, Object> product : jewleryList) {
			String title = (String) product.get("title");
			Number price = (Number) product.get("price");
			System.out.println("title:" + title);
			System.out.println("price:" + price);
			System.out.println("-----------");
		}
		
		System.out.println("-------");
		
		//get min price:
		Double minPrice= JsonPathValidator.read(response, "min($[*].price)");
		System.out.println("min price: "+ minPrice);
		
		
	}
	
	
	

}
