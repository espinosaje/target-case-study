package com.casestudy.target;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.junit.Assert;
import org.springframework.web.client.RestTemplate;

import com.casestudy.target.name.RedSkyProductWrapper;
import com.casestudy.target.name.entities.Item;
import com.casestudy.target.name.entities.Product;
import com.casestudy.target.price.PriceController;
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
	
	@Autowired
	PriceController priceController;

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
		// get the main JSON object from the entity
		Item item = p.getItem();

		System.out.println("###### Item: " + item.getBuy_url());
	}
	
	@Test
	public void getValidAggregatedProduct() {
		System.out.println("@@@ Starting getValidAggregatedProduct Test @@@ ");
		int id = 13860428;
		
		//String request_ap = localhost+aggregatedproductUri+aggregatedproductId;
		ResponseEntity<AggregatedProduct> productEntity = aggregatedProductController.retrieveProduct(id);
		AggregatedProduct product = productEntity.getBody();
		if (product != null) {
			System.out.println("## getValidAggregatedProduct.name: " + product.getName());
			System.out.println("## getValidAggregatedProduct.price: " + product.getCurrent_price().getValue());
		}
		//Assert.assertNotNull(product.getCurrent_price().getValue());
	}
	
	@Test
	public void getMissingNameAggregatedProduct() {
		System.out.println("@@@ Starting getMissingNameAggregatedProduct Test @@@ ");
		int id = 10002;
		ResponseEntity<AggregatedProduct> productEntity = aggregatedProductController.retrieveProduct(id);
		AggregatedProduct product = productEntity.getBody();
		if (product != null) {
			System.out.println("## getMissingNameAggregatedProduct.name: " + product.getName());
			System.out.println("## getMissingNameAggregatedProduct.price: " + product.getCurrent_price().getValue());
		}
		//Assert.assertNotNull(product.getName());
	}
	
	@Test
	public void changePrice() {
		float price = (float) 100.0;
		priceController.addPrice("test", 111, price , "TEST");
		// Make sure the object was stored in the DB
		Assert.assertTrue(priceController.getPrice(111).get().getPrice() == price);
		// Delete it from the DB
		priceController.deletePrice(111);
		// Check that value is no longer in the DB
		Assert.assertFalse(priceController.getPrice(111).isPresent());
	}
	
	@Test
	public void getNoRecodsFoundAggregatedProduct() {
		System.out.println("@@@ Starting getMissingPriceAggregatedProduct Test @@@ ");
		int id = 010101;
		
		//TODO: Remove record
		
		ResponseEntity<AggregatedProduct> productEntity = aggregatedProductController.retrieveProduct(id);
		AggregatedProduct product = productEntity.getBody();

		Assert.assertNull(product);
	}

}
