package com.casestudy.target.name;

import com.casestudy.target.PrintableJson;
import com.casestudy.target.name.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedSkyProductWrapper extends PrintableJson{
	Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
