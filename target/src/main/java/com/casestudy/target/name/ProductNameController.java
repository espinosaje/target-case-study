package com.casestudy.target.name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/product")
public class ProductNameController {
	@Autowired
    private ProductName name;
	@Autowired
    private List<ProductName> namesList;
	@Autowired
	private ProductNameRepository nameRepository;
	
	 @GetMapping("/names/allNames")
	 public ResponseEntity<List<ProductName>> getAllQuotes() {

		 namesList = nameRepository.findAll();
	        if (namesList.isEmpty())
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	        return ResponseEntity.status(HttpStatus.CREATED).body(namesList);
	 }
	 
	 @GetMapping("/names/{name}")
	 public Optional<ProductName> retrieveExchangeValue(@PathVariable String id) {
		 Optional<ProductName> productName = nameRepository.findById(id);

		return productName;
	}
	 
	 @PostMapping("/names/addProductName")	
	    public ResponseEntity<ProductName> addName(@RequestBody ProductName productName) {

		 name = nameRepository.save(productName);
	       // log.info("Saved quote="+name.toString());
		   System.out.println("Saved quote="+name.toString());
	        if (name != null)
	            return ResponseEntity.status(HttpStatus.CREATED).body(name);

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	    }
	 
	 @GetMapping("/names/addName")
	    public ResponseEntity<ProductName> addName(@RequestParam String name
				, @RequestParam String id) {

			ProductName n = new ProductName();
			n.setName(name);
			n.setId(id);
			
			nameRepository.save(n);
			if (name != null)
	            return ResponseEntity.status(HttpStatus.CREATED).body(n);
			
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    } 
}
