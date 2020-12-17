package com.casestudy.target.price;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;
import com.casestudy.target.PrintableJson;

@Repository
@Document(collection = "Price")
public class Price extends PrintableJson{

    private int id;
    private String name;
    private float price;
    private String currency;

    public Price() {

    }

    
	public Price(int id, String name, float price, String currency) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.currency = currency;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}