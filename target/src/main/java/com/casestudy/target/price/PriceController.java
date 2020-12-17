package com.casestudy.target.price;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.target.TargetConstants;

@CrossOrigin
@RestController
@EnableCaching
@RequestMapping("/products")
public class PriceController {
	private Price price;
	private static final Logger LOG = LoggerFactory.getLogger(PriceController.class);

	@Autowired
	private PriceRepository priceRepository;

	/* TODO: Add error handling, return ApiError object if Price is not found */
	@GetMapping("/price/{id}")
	@Cacheable(key="#id", value="Price")
	public Optional<Price> getPrice(@PathVariable int id) {
		Optional<Price> price = priceRepository.findById(id);
		if (price.isPresent()) {
			LOG.info(TargetConstants.SERVICE_MSG_NOSQL_RETRIEVE + id);
		}
		else {
			LOG.info(TargetConstants.SERVICE_MSG_NOSQL_NOT_FOUND + id);
		}
		
		return price;
	}

	@PostMapping("/{id}")
	@CacheEvict(value = "Price", key = "#productName.id")
	public ResponseEntity<Price> postPrice(@RequestBody Price productName) {

		price = priceRepository.save(productName);

		if (price != null) {
			LOG.info(TargetConstants.SERVICE_MSG_NOSQL_PRICE_UPDATED + productName.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(price);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	
	//**** for unit testing only ****//
	@CacheEvict(key = "#id", value = "Price", condition = "#id!=null")
	public Price addPrice(String name, int id, float price,
			String currency) {

		Price priceToAdd = new Price();
		priceToAdd.setName(name);
		priceToAdd.setId(id);
		priceToAdd.setPrice(price);
		priceToAdd.setCurrency(currency);

		priceRepository.save(priceToAdd);
		LOG.info("Created Price record:  "+priceToAdd.toString());
		return priceToAdd;
		
	}
	@CacheEvict(value = "Price", key = "#price.id")
	public void deletePrice(Price price) {
		//priceRepository.deleteById(id);
		priceRepository.delete(price);
		LOG.info("Deleted record "+price.getId()+" from DB (NoSQL)");
	}

}
