package com.trong.apiservice.repository;

import com.trong.apiservice.model.Product;
import com.trong.apiservice.repository.custom.ProductRepoCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, String>, ProductRepoCustom {
}
