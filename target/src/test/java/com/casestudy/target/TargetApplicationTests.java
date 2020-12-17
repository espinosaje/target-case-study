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

	private final float test_price = TargetConstants.UNIT_TEST_PRICE;
	private final int test_id = TargetConstants.UNIT_TEST_ID;
	private final String test_name = TargetConstants.UNIT_TEST_NAME;
	private final int valid_id = TargetConstants.UNIT_TEST_VALID_ID;
	
	private static final Logger LOG = LoggerFactory.getLogger(TargetApplicationTests.class);
	
	@Test
	public void testNamesFromRedSky() {

		String id = String.valueOf(valid_id);
		LOG.info("@@@ Starting getNamesFromRedSky Test @@@ ");
		// actual service call
		
		ResponseEntity<RedSkyProductWrapper> res = redSkyProductController.getName(id);
		RedSkyProductWrapper productEntity = res.getBody();
		if (productEntity != null) {
			Assert.assertTrue(productEntity.getProduct().getClass() == Product.class);
			LOG.info("Successfully retrieve from Redsky: "+id);
		}
		else {
			LOG.info("ID not found on Redsky: "+id);
		}
		
		redSkyProductController.getName(id);

	}
	
	/*
	 * Fetches a record from BOTH sources and aggregates the data
	 */
	@Test
	public void testValidAggregatedProduct() {
		LOG.info("@@@ Starting getValidAggregatedProduct Test @@@ ");

		//String request_ap = localhost+aggregatedproductUri+aggregatedproductId;
		AggregatedProduct product = null;
		ResponseEntity<Object> productEntity = aggregatedProductController.retrieveProduct(valid_id);
		if (productEntity.getBody().getClass() == AggregatedProduct.class)
			product = (AggregatedProduct) productEntity.getBody();
		if (product != null) {
			Assert.assertNotNull(product.getCurrent_price().getValue());
			LOG.info("Successfully retrieve aggregated product ID: "+valid_id);
		}
		else {
			LOG.info("Could not build aggregated product: "+valid_id);
		}
	}

	/*
	 * Fetches a record that ONLY exists in the Price DB (NoSQL)
	 */
	@Test
	public void testMissingNameAggregatedProduct() {
		LOG.info("@@@ Starting getMissingNameAggregatedProduct Test @@@ ");
		AggregatedProduct product = null;
		// insert a NoSQL record to make sure it is there
		Price priceAdded = priceController.addPrice(test_name, test_id, test_price , TargetConstants.DEAULT_CURRENCY);

		ResponseEntity<Object> productEntity = aggregatedProductController.retrieveProduct(test_id);
		if (productEntity.getBody().getClass() == AggregatedProduct.class) {
			product = (AggregatedProduct) productEntity.getBody();
			if (product != null) {
				Assert.assertNotNull(product.getName());
				LOG.info("Aggregated product from NoSQL : "+test_id);
				// Delete it newly created record from the NoSQL DB
				priceController.deletePrice(priceAdded);
			}
		}	
	}

	/*
	 * Inserts a new record in the Price database, it verifies that the value comes
	 * back from the DB then deletes the record and checks that no value is returned
	 */
	@Test
	public void testChangePrice() {
		LOG.info("@@@ Starting changePrice Test @@@ ");
		
		priceController.addPrice(test_name, test_id, test_price , TargetConstants.DEAULT_CURRENCY);
		// Make sure the object was stored in the DB
		Assert.assertTrue(priceController.getPrice(test_id).get().getPrice() == test_price);
		// Should NOT call cache
		Price p = priceController.getPrice(test_id).get(); 
		// Delete it from the DB
		priceController.deletePrice(p);
		// Check that value is no longer in the DB
		Assert.assertFalse(priceController.getPrice(test_id).isPresent());
	}
	
	/*
	 * looks for a record which doesn't exist in either source
	 */
	@Test
	public void testNoRecodsFoundAggregatedProduct() {
		LOG.info("@@@ Starting testNoRecodsFoundAggregatedProduct Test @@@ ");
		ApiMessage error = null;
		ResponseEntity<Object> productEntity = aggregatedProductController.retrieveProduct(test_id);
		if (productEntity.getBody().getClass() == ApiMessage.class) {
			error = (ApiMessage) productEntity.getBody();
			Assert.assertTrue(error.getMessage().equals(TargetConstants.SERVICE_MSG_RECORD_NOT_FOUND));
			
		}
	}

}
