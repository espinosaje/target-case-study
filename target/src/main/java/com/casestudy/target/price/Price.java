package com.casestudy.target.price;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

@Repository
@Document(collection = "Price")
public class Price {

    private String id;
    private String name;
    private String price;
    private String currency;

    public Price() {

    }

    public Price(String id, String author) {
        this.id = id;
        this.name = author;
    }
    
	public Price(String id, String name, String price, String currency) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.currency = currency;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}