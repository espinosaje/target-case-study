package com.casestudy.target.price;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class PriceController {
	@Autowired
    private Price price; //TODO, not sure if it needs to be auto-wired
	
	@Autowired
	private PriceRepository priceRepository;
	
	 @GetMapping("/price/{id}")
	 public Optional<Price> getPrice(@PathVariable String id) {
		 Optional<Price> price = priceRepository.findById(id);

		return price;
	}

	 @PostMapping("/{id}")	
	    public ResponseEntity<Price> addName(@RequestBody Price productName) {

		 price = priceRepository.save(productName);
	     
		   System.out.println("Saved price="+price.toString());
	        if (price != null)
	            return ResponseEntity.status(HttpStatus.CREATED).body(price);

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	    }
	 
	 @GetMapping("/price/addPrice")
	    public ResponseEntity<Price> addName(@RequestParam String name
										, @RequestParam String id
										, @RequestParam String price
										, @RequestParam String currency) {

			Price n = new Price();
			n.setName(name);
			n.setId(id);
			n.setPrice(price);
			n.setCurrency(currency);
			
			priceRepository.save(n);
			if (name != null)
	            return ResponseEntity.status(HttpStatus.CREATED).body(n);
			
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    } 

}
