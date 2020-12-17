package com.casestudy.target.product;

import com.casestudy.target.PrintableJson;

public class AggregatedProduct  extends PrintableJson{
	int id;
	String name;
	CurrentPrice current_price;
	
	public AggregatedProduct() {
		
	}
	
	public AggregatedProduct(int id, String name, CurrentPrice current_price) {
		super();
		this.id = id;
		this.name = name;
		this.current_price = current_price;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CurrentPrice getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(CurrentPrice current_price) {
		this.current_price = current_price;
	}
	
	
}
