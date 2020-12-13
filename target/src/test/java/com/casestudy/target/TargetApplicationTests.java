package com.casestudy.target;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

	@Test
	void contextLoads() {
		
	}
	
	@Test
	public void getAllNames() {

		RestTemplate restTemplate = new RestTemplate();
		
		//String request = "https://redsky.target.com/v3/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate";

		System.out.println("@@@ Starting getAllNames Test @@@ ");
		// actual service call
		RedSkyProductWrapper productEntity = (RedSkyProductWrapper) restTemplate.getForObject(request,RedSkyProductWrapper.class);
		System.out.println("###### productEntity.getClass: " + productEntity.getClass());
		System.out.println("###### productEntity.isNull: " + (productEntity == null));
		System.out.println("###### productEntity.getClass: " + productEntity.getProduct().getClass());
		System.out.println("###### productEntity.getProducts.isEmpty: " + (productEntity.getProduct()==null));
		Product p = productEntity.getProduct();
		System.out.println("###### first product: " + p);

		// get the main JSON object from the entity
		Item item = p.getItem();

		System.out.println("@@@ Item: " + item.getBuy_url());
		//System.out.println("@@@ Item_buyURL: " + item.getBuy_url());
		//System.out.println("@@@ Product_desc-Title: " + redSkyProduct.getItem().getProduct_description().getTitle());
	}
	
	@Test
	public void getAggregatedProduct() {
		System.out.println("@@@ Starting getAggregatedProduct Test @@@ ");
		RestTemplate restTemplate = new RestTemplate();
		
		String request_ap = localhost+aggregatedproductUri+aggregatedproductId;
		//AggregatedProduct productEntity = (AggregatedProduct) restTemplate.getForObject(request_ap,AggregatedProduct.class);
		//System.out.println("###### productEntity.getProducts.isEmpty: " + productEntity.getCurrent_price());
	}

}
