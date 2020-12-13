package com.casestudy.target;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.casestudy.target.name.RedSkyProductWrapper;
import com.casestudy.target.name.entities.Item;
import com.casestudy.target.name.entities.Product;
import com.casestudy.target.product.AggregatedProduct;
import com.casestudy.target.product.AggregatedProductController;

@SpringBootTest
class TargetApplicationTests {
	
	@Value("${redsky.endpoint}")
	private String request;
	
	@Value("${localhost.endpoint}")
	private String localhost;
	
	@Value("${localhost.aggregatedproduct}")
	private String aggregatedproductUri;
	
	@Value("${test.aggregatedproduct.id}")
	private String aggregatedproductId;
	
	@Autowired
	AggregatedProductController aggregatedProductController;

	@Test
	void contextLoads() {
		
	}
	
	@Test
	public void getNamesFromRedSky() {

		RestTemplate restTemplate = new RestTemplate();
		
		//String request = "https://redsky.target.com/v3/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate";

		System.out.println("@@@ Starting getNamesFromRedSky Test @@@ ");
		// actual service call
		RedSkyProductWrapper productEntity = (RedSkyProductWrapper) restTemplate.getForObject(request,RedSkyProductWrapper.class);
		Product p = productEntity.getProduct();
		System.out.println("###### product: " + p);

		// get the main JSON object from the entity
		Item item = p.getItem();

		System.out.println("###### Item: " + item.getBuy_url());
		//System.out.println("@@@ Item_buyURL: " + item.getBuy_url());
		//System.out.println("@@@ Product_desc-Title: " + redSkyProduct.getItem().getProduct_description().getTitle());
	}
	
	@Test
	public void getValidAggregatedProduct() {
		System.out.println("@@@ Starting getValidAggregatedProduct Test @@@ ");
		
		RestTemplate restTemplate = new RestTemplate();
		String id = "13860428";
		
		//String request_ap = localhost+aggregatedproductUri+aggregatedproductId;
		AggregatedProduct product = aggregatedProductController.retrieveExchangeValue(id);
		System.out.println("## getValidAggregatedProduct.name: " + product.getName());
		System.out.println("## getValidAggregatedProduct.price: " + product.getCurrent_price().getValue());
	}
	
	@Test
	public void getMissingNameAggregatedProduct() {
		System.out.println("@@@ Starting getMissingNameAggregatedProduct Test @@@ ");
		
		RestTemplate restTemplate = new RestTemplate();
		String id = "10002";
		
		//String request_ap = localhost+aggregatedproductUri+aggregatedproductId;
		AggregatedProduct product = aggregatedProductController.retrieveExchangeValue(id);
		System.out.println("## getMissingNameAggregatedProduct.name: " + product.getName());
		System.out.println("## getMissingNameAggregatedProduct.price: " + product.getCurrent_price().getValue());
	}

}
