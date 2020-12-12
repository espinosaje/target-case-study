package com.casestudy.target.name;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

@Repository
@Document(collection = "ProductName")
public class ProductName {

    private String id;
    private String name;

    public ProductName() {

    }

    public ProductName(String id, String author) {
        this.id = id;
        this.name = author;
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

   
}