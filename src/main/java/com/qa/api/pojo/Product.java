package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private Integer id;
	private String title;
	private double price;
	private String description;
	private String category;
	private String image;
	private Rating rating;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Rating {
		private double rate;
		private int count;
	}

}
