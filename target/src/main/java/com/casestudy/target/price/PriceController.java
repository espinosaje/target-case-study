package com.casestudy.target.price;

import java.util.Optional;

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

@CrossOrigin
@RestController
@EnableCaching
@RequestMapping("/products")
public class PriceController {
	private Price price;

	@Autowired
	private PriceRepository priceRepository;

	@GetMapping("/price/{id}")
	@Cacheable(key="#id", value="Price")
	public Optional<Price> getPrice(@PathVariable int id) {
		Optional<Price> price = priceRepository.findById(id);
		System.out.println("################%%%%%%%%% Access PRice DB"+id);
		return price;
	}

	@PostMapping("/{id}")
	@CacheEvict(value = "Price", key = "#productName.id")
	public ResponseEntity<Price> postPrice(@RequestBody Price productName) {
		if (validatePrice(productName))
		
		price = priceRepository.save(productName);

		if (price != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(price);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	}

	// want to add some validations
	private boolean validatePrice(Price productName) {
		
		return true;
	}
	
	// for internal testing only
	@CacheEvict(key = "#id", value = "Price", condition = "#id!=null")
	public Price addPrice(String name, int id, float price,
			String currency) {

		Price priceToAdd = new Price();
		priceToAdd.setName(name);
		priceToAdd.setId(id);
		priceToAdd.setPrice(price);
		priceToAdd.setCurrency(currency);

		priceRepository.save(priceToAdd);
		
		return priceToAdd;
		
	}
	@CacheEvict(value = "Price", key = "#price.id")
	public void deletePrice(Price price) {
		//priceRepository.deleteById(id);
		priceRepository.delete(price);
	}

}
