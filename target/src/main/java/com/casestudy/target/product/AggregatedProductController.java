package com.casestudy.target.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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


@RestController
@RequestMapping("/product")
public class AggregatedProductController {
	
	@Autowired
	PriceController priceController;
	@Autowired
	RedSkyProductController nameController;
	
	Price price;
	RedSkyProductWrapper productWrapper;
		
	@GetMapping("/aggregatedProduct/{id}")
	public AggregatedProduct retrieveExchangeValue(@PathVariable String id) {

		// get Price info from MongoDB
		price = priceController.getPrice(id).get();
		// get name from external API, need extract internal objects
		productWrapper = nameController.getAllNames().getBody();
		Product product = productWrapper.getProduct();
		Item item = product.getItem();
		ProductDescription productDescription =  item.getProduct_description();
		String name = productDescription.getTitle();
		
		//aggregate data
		//create current Price
		CurrentPrice currentPrice = new CurrentPrice(price.getPrice(), "USD");
		//create aggregated product
		AggregatedProduct aggregatedProduct = new AggregatedProduct(id, name, currentPrice);	
		
		System.out.println("###### productEntity.name: " + aggregatedProduct.getName());
		System.out.println("###### productEntity.price: " + aggregatedProduct.getCurrent_price().getValue());
		return aggregatedProduct;
	}
}
