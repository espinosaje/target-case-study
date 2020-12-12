package com.casestudy.target.name;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
	
	ProductDescription product_description;
	String tcin;
	String buy_url;
	
	public Item() {

    }

	public Item(ProductDescription product_description, String tcin, String buy_url) {
		super();
		this.product_description = product_description;
		this.tcin = tcin;
		this.buy_url = buy_url;
	}


	public ProductDescription getProduct_description() {
		return product_description;
	}

	public void setProduct_description(ProductDescription product_description) {
		this.product_description = product_description;
	}

	public String getTcin() {
		return tcin;
	}

	public void setTcin(String tcin) {
		this.tcin = tcin;
	}

	public String getBuy_url() {
		return buy_url;
	}

	public void setBuy_url(String buy_url) {
		this.buy_url = buy_url;
	}
	
	

}
