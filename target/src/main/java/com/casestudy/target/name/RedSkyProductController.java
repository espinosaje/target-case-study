package com.casestudy.target.name;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.casestudy.target.ApiError;
import com.casestudy.target.TargetConstants;


@CrossOrigin
@RestController
@EnableCaching
@RequestMapping("/products")
public class RedSkyProductController {
	HttpEntity<String> entityReq;
	@Value("${redsky.endpoint}")
	private String request;

	public RedSkyProductController() {
		// set the headers for authentication
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		// headers.set("x-api-key", "2f7b7e46-b66a-4281-8ac4-821cf1a03efb");
		entityReq = new HttpEntity<String>(headers);
	}

	@GetMapping("/redsky/getAllProducts")
	public ResponseEntity<RedSkyProductWrapper> getAllNames() {

		RestTemplate restTemplate = new RestTemplate();

		// external service call
		ResponseEntity<RedSkyProductWrapper> productEntity = (ResponseEntity<RedSkyProductWrapper>) restTemplate.exchange(request,
				HttpMethod.GET, entityReq, RedSkyProductWrapper.class);
		
		// get the main JSON object from the entity
		RedSkyProductWrapper productWrapper = (RedSkyProductWrapper) productEntity.getBody();
		

		if (productWrapper != null) {
			return ResponseEntity.status(HttpStatus.OK).body(productWrapper);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	/*
	 * No filter was provided for the RedSky endpoint, so it is implemented inside
	 * the method.
	 * Data is cached for the ID
	 */
	@GetMapping("/redsky/getProduct/{id}")
	@Cacheable(key="#id", value="RedSkyProductWrapper")
	public ResponseEntity<RedSkyProductWrapper> getName(@PathVariable String id) {

		RestTemplate restTemplate = new RestTemplate();

		// external service call
		ResponseEntity<RedSkyProductWrapper> productEntity = (ResponseEntity<RedSkyProductWrapper>) restTemplate.exchange(request,
				HttpMethod.GET, entityReq, RedSkyProductWrapper.class);
		
		// get the main JSON object from the entity
		RedSkyProductWrapper productWrapper = (RedSkyProductWrapper) productEntity.getBody();
		if(productWrapper != null
		&& productWrapper.getProduct() != null
		&& productWrapper.getProduct().getAvailable_to_promise_network() != null
		&& productWrapper.getProduct().getAvailable_to_promise_network().getProduct_id().equals(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(productWrapper);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/redsky/refreshCache/{id}")
	@Cacheable(key="#id", value="RedSkyProductWrapper")
	public ResponseEntity<ApiError> refreshNameCache(@PathVariable String id) {
		ApiError error = new ApiError(HttpStatus.OK, TargetConstants.CACHE_REFRESHED+id);
		return ResponseEntity.status(HttpStatus.OK).body(error);
	}
	
}
