package com.casestudy.target.product;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.casestudy.target.ApiError;
import com.casestudy.target.TargetConstants;
import com.casestudy.target.name.RedSkyProductController;
import com.casestudy.target.name.RedSkyProductWrapper;
import com.casestudy.target.name.entities.Item;
import com.casestudy.target.name.entities.Product;
import com.casestudy.target.name.entities.ProductDescription;
import com.casestudy.target.price.Price;
import com.casestudy.target.price.PriceController;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class AggregatedProductController {
	
	@Autowired
	PriceController priceController;
	@Autowired
	RedSkyProductController nameController;
	
	Price price;
	RedSkyProductWrapper productWrapper;
	
	private static final Logger LOG = LoggerFactory.getLogger(AggregatedProductController.class);
		
	@GetMapping("/{id}")
	public ResponseEntity<Object> retrieveProduct(@PathVariable int id) {

		// get PRICE info from MongoDB
		price = getPrice(id);
		// get NAME from external API, if it doesn't find one it gets the name from the Price
		String name = getProductName(String.valueOf(id));
		
		// if NAME is NULL it means there are no records of Product or Price, there's no response to create
		if (name != null) {
		//aggregate data
			//create current Price object, if Price service didn't return anything assign default value
			double priceValue = TargetConstants.DEAULT_PRICE;
			String currency = TargetConstants.DEAULT_CURRENCY;
			if (price != null) {
				priceValue = price.getPrice();
				currency = price.getCurrency();
				
			}
			CurrentPrice currentPrice = new CurrentPrice(priceValue, currency);
			//create aggregated product
			AggregatedProduct aggregatedProduct = new AggregatedProduct(id, name, currentPrice);	
	
			if (aggregatedProduct != null) {
				LOG.info(TargetConstants.SERVICE_MSG_AGGREGATED_PRODUCT+aggregatedProduct);
				return ResponseEntity.status(HttpStatus.OK).body(aggregatedProduct);
			}
		}
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST, TargetConstants.SERVICE_MSG_RECORD_NOT_FOUND);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

	}
	
	/* 
	 * gets Price from MongoDB, making sure the Optional object is present to about a NULL pointer
	 * if error handling is implemented in Price, we might have to change this to check for obj type 
	 * */
	private Price getPrice(int id) {
		Optional<Price> optionalPrice = priceController.getPrice(id);
		if(!optionalPrice.isPresent()) {
			return null;
		}

		return priceController.getPrice(id).get();
	}
	
	/* 
	 * get name from the RedSky call, assigns a default value if not found 
	 * */
	private String getProductName(String id) {
		String name = null;
		// call the external API (redsky)
		ResponseEntity<RedSkyProductWrapper> redSkyProductResponse = nameController.getName(id);
		// Navigate through all the objects in the response to get to the TITLE
		if (redSkyProductResponse != null) {
			productWrapper = redSkyProductResponse.getBody();
			if (productWrapper != null) {
				Product product = productWrapper.getProduct();
				if (product != null) {
					Item item = product.getItem();
					if (item != null) {
						ProductDescription productDescription =  item.getProduct_description();
						if (productDescription != null) {
							name = productDescription.getTitle();
						}
					}
				}
			}
		}
		
		if (name == null && price != null) {
			name = price.getName();
		}
		return name;
	}
}
