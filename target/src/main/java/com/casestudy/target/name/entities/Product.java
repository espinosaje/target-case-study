package com.casestudy.target.name.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product{

    private Item item;
    private AvailableToPromiseNetwork available_to_promise_network;

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
 
	public AvailableToPromiseNetwork getAvailable_to_promise_network() {
		return available_to_promise_network;
	}

	public void setAvailable_to_promise_network(AvailableToPromiseNetwork available_to_promise_network) {
		this.available_to_promise_network = available_to_promise_network;
	}

	@Override
    public String toString() {
        return "{" +
                "\"item\":\"" + item + "\"" +
                //"\"name\":\"" + name + "\"" +
                '}';
    }
}