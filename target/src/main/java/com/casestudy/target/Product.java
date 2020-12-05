package com.casestudy.target;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
  
  @Id
  private Long id;
  
  @Column(name="name")	// former: currency_from
  private String name;
  
  
  public Product() {
    
  }
  

  public Product(Long id, String name) {
    super();
    this.id = id;
    this.name = name;
   
  }

  public Long getId() {
    return id;
  }

  public String getFrom() {
    return name;
  }
}