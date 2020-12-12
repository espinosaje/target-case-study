package com.casestudy.target.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductRepository repository;

	@GetMapping("/name/{name}")
	public Product retrieveExchangeValue(@PathVariable String name) {

		Product product = repository.findByName(name);

		return product;
	}
}
