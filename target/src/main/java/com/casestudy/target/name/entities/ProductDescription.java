package com.casestudy.target.name.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDescription {
	String title;
	String downstream_description;
	
	public ProductDescription() {
		
	}
	
	public ProductDescription(String title, String downstream_description) {
		super();
		this.title = title;
		this.downstream_description = downstream_description;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDownstream_description() {
		return downstream_description;
	}
	public void setDownstream_description(String downstream_description) {
		this.downstream_description = downstream_description;
	}
	
	
}
