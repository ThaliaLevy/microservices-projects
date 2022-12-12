package com.levythalia.microservices.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.levythalia.microservices.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

}
