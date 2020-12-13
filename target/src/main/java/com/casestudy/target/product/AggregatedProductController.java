package com.casestudy.target.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
		
	@GetMapping("/{id}")
	public AggregatedProduct retrieveExchangeValue(@PathVariable String id) {

		// get PRICE info from MongoDB
		price = priceController.getPrice(id).get();
		// get NAME from external API, need extract internal objects
		String name = getProductName(id);
		
		//aggregate data
		//create current Price
		CurrentPrice currentPrice = new CurrentPrice(price.getPrice(), "USD");
		//create aggregated product
		AggregatedProduct aggregatedProduct = new AggregatedProduct(id, name, currentPrice);	
		
		System.out.println("###### productEntity.name: " + aggregatedProduct.getName());
		System.out.println("###### productEntity.price: " + aggregatedProduct.getCurrent_price().getValue());
		return aggregatedProduct;
	}
	
	// get name from the RedSky call, assigns a default value if not found
	private String getProductName(String id) {
		String name = null;
		// call the external API (redsky)
		ResponseEntity<RedSkyProductWrapper> redSkyProductResponse = 
				// nameController.getAllNames();
				nameController.getName(id);
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
		
		if (name == null) {
			name = price.getName();
		}
		return name;
	}
}
