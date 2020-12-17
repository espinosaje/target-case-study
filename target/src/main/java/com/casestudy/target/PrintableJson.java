package com.casestudy.target;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class PrintableJson {

	public String toString() {
		String jsonString = "";
		// Creating the ObjectMapper object
		ObjectMapper mapper = new ObjectMapper();
		// Converting the Object to JSONString
		try {
			jsonString = mapper.writeValueAsString(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsonString;
	}

}
