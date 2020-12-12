package com.casestudy.target.name;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductNameRepository extends MongoRepository<ProductName,String> {

   // Optional<ProductName> findById(String id);

   // List<ProductName> findByQuoteContainsAllIgnoreCase(String author);

}