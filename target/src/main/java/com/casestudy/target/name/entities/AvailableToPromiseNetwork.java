package com.casestudy.target.name.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailableToPromiseNetwork {
	String product_id;
	String id_type;
	public AvailableToPromiseNetwork(String product_id, String id_type) {
		super();
		this.product_id = product_id;
		this.id_type = id_type;
	}
	public AvailableToPromiseNetwork() {
		super();
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getId_type() {
		return id_type;
	}
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	
}
