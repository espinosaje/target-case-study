package com.casestudy.target.price;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.target.name.ProductName;

@Repository
public interface PriceRepository extends MongoRepository<Price,String> {


}
