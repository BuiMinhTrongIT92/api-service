package com.trong.apiservice.service;

import com.trong.apiservice.model.Product;
import com.trong.apiservice.model.dto.ProductDTO;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

public interface IProductService {
    Object getAllProducts(Map<String, Object> params, PageRequest pageRequest);

    Boolean updateProduct(ProductDTO productDTO, String id);

    Product getProduct(ProductDTO productDTO, String id, PageRequest pageRequest);
}
