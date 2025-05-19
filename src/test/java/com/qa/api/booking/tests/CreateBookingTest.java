package com.qa.api.booking.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Booking;
import com.qa.api.pojo.Booking.BookingDates;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingTest extends BaseTest{
	private static final String BASE_URL_BOOKING = "https://restful-booker.herokuapp.com";

	private static final String BOOKING_ENDPOINT = "/booking";

	private String bookingId;



	@Test

	public void createBookingAPITest() {

		BookingDates dates = BookingDates.builder()
	            .checkin("2019-01-01")
	            .checkout("2020-05-05")
	            .build();

	    Booking createBooking = Booking.builder()
	            .firstname("Nilesh")
	            .lastname("Bhuajng")
	            .totalprice(500)
	            .depositpaid(true)
	            .bookingdates(dates)
	            .additionalneeds("Dinner")
	            .build();

		Response response = restClient.post(BASE_URL_BOOKING, BOOKING_ENDPOINT, createBooking, null, null, AuthType.NO_AUTH, ContentType.JSON);

		bookingId = response.jsonPath().getString("bookingid");

		System.out.println("Print the New Created Booking ID : " + bookingId);

		Assert.assertEquals(response.statusCode(), 200);

	}


	
	
	
}
