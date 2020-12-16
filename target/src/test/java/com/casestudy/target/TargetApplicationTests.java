package com.casestudy.target;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.junit.Assert;
import com.casestudy.target.name.RedSkyProductController;
import com.casestudy.target.name.RedSkyProductWrapper;
import com.casestudy.target.name.entities.Product;
import com.casestudy.target.price.Price;
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
	
	@Autowired
	RedSkyProductController redSkyProductController;

	private static final Logger LOG = LoggerFactory.getLogger(TargetApplicationTests.class);
	
	@Test
	public void testNamesFromRedSky() {

		String id = "13860428";
		//String request = "https://redsky.target.com/v3/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate";

		LOG.info("@@@ Starting getNamesFromRedSky Test @@@ ");
		// actual service call
		//RedSkyProductWrapper productEntity = (RedSkyProductWrapper) restTemplate.getForObject(request,RedSkyProductWrapper.class);
		ResponseEntity<RedSkyProductWrapper> res = redSkyProductController.getName(id);
		RedSkyProductWrapper productEntity = res.getBody();
		if (productEntity != null) {
			Assert.assertTrue(productEntity.getProduct().getClass() == Product.class);
		}
		LOG.info("## Success getNamesFromRedSky ");
		redSkyProductController.getName(id);

	}
	
	@Test
	public void testValidAggregatedProduct() {
		LOG.info("@@@ Starting getValidAggregatedProduct Test @@@ ");
		int id = 13860428;
		
		//String request_ap = localhost+aggregatedproductUri+aggregatedproductId;
		AggregatedProduct product = null;
		ResponseEntity<Object> productEntity = aggregatedProductController.retrieveProduct(id);
		if (productEntity.getBody().getClass() == AggregatedProduct.class)
			product = (AggregatedProduct) productEntity.getBody();
		if (product != null) {
			Assert.assertNotNull(product.getCurrent_price().getValue());
		}
	}
	
	@Test
	public void testMissingNameAggregatedProduct() {
		LOG.info("@@@ Starting getMissingNameAggregatedProduct Test @@@ ");
		int id = 10002;
		AggregatedProduct product = null;
		ResponseEntity<Object> productEntity = aggregatedProductController.retrieveProduct(id);
		if (productEntity.getBody().getClass() == AggregatedProduct.class)
			product = (AggregatedProduct) productEntity.getBody();
		if (product != null) {
			Assert.assertNotNull(product.getName());
		}
	}
	
	@Test
	public void testChangePrice() {
		LOG.info("@@@ Starting changePrice Test @@@ ");
		float test_price = (float) 100.0;
		int id = 1010101;
		priceController.addPrice("test", id, test_price , "TEST");
		// Make sure the object was stored in the DB
		Assert.assertTrue(priceController.getPrice(id).get().getPrice() == test_price);
		// Should NOT call cache
		Price p = priceController.getPrice(id).get(); 
		// Delete it from the DB
		priceController.deletePrice(p);
		// Check that value is no longer in the DB
		Assert.assertFalse(priceController.getPrice(id).isPresent());
	}
	
	@Test
	public void testNoRecodsFoundAggregatedProduct() {
		LOG.info("@@@ Starting testNoRecodsFoundAggregatedProduct Test @@@ ");
		int id = 1010101; // give an ID that doesn't exist
		ApiError error = null;
		ResponseEntity<Object> productEntity = aggregatedProductController.retrieveProduct(id);
		if (productEntity.getBody().getClass() == ApiError.class) {
			error = (ApiError) productEntity.getBody();
			Assert.assertTrue(error.getMessage().equals(TargetConstants.RECORD_NOT_FOUND));
			
		}
	}

}
