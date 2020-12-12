package com.casestudy.target.name.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private Item item;

    public Product() {

    }

	public Product(Item item) {
		super();
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
 
	@Override
    public String toString() {
        return "{" +
                "\"item\":\"" + item + "\"" +
                //"\"name\":\"" + name + "\"" +
                '}';
    }
}