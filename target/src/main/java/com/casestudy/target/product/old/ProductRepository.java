package com.casestudy.target.product.old;

import org.springframework.data.jpa.repository.JpaRepository;

// we are using Long because Product ID is Long
public interface ProductRepository extends JpaRepository<Product, Long>{
	Product findByName(String name);
}