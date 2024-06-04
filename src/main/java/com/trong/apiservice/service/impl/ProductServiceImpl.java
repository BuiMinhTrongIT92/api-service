package com.trong.apiservice.service.impl;

import com.trong.apiservice.configuration.handler.HttpException;
import com.trong.apiservice.model.Product;
import com.trong.apiservice.model.dto.ProductDTO;
import com.trong.apiservice.repository.ProductRepo;
import com.trong.apiservice.service.IHttpService;
import com.trong.apiservice.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IHttpService httpService;
    @Autowired
    private ProductRepo productRepo;
    @Value("${redis-cache.base.url}")
    private String baseUrl;

    @Value("${redis.url.get-product}")
    private String getProducts;
    @Value("${redis.url.save-product}")
    private String saveProducts;
    @Override
    public Object getAllProducts(String keyword, String id, PageRequest pageRequest) {
        Object response = httpService.getWithAuthentication(baseUrl, getProducts, null);
        if (response instanceof HttpException || response == null) {
            List<Product> products = productRepo.findAll();
            if (products != null && products.size() > 0) {
                Object saveResponse = httpService.postWithAuthentication(baseUrl, saveProducts, products, null);
                if (saveResponse instanceof HttpException) {
                    log.warn("Save cache products fail");
                }
            }
            return products;
        }
        return response;
    }

    public Boolean updateProduct(ProductDTO productDTO, String id) {
        if (null != productDTO) {
            Update update = new Update();
            update.set("productCode", productDTO.getProductCode());
            update.set("productName", productDTO.getProductName());
            update.set("productLine", productDTO.getProductLine());
            update.set("productScale", productDTO.getProductScale());
            update.set("productVendor", productDTO.getProductVendor());
            update.set("productDescription", productDTO.getProductDescription());
            update.set("quantityInStock", productDTO.getQuantityInStock());
            update.set("buyPrice", productDTO.getBuyPrice());
            update.set("MSRP", productDTO.getMsrp());
            productRepo.updateDocument(update, productDTO.get_id().toString());
//            new Thread(() -> {
//                Object response = httpService.postWithAuthentication(baseUrl, saveProducts, update, null);
//                if (response instanceof HttpException) {
//                    log.warn("Update product fail");
//                }
//            });
        }
        return true;
    }

    @Override
    public Product getProduct(ProductDTO productDTO, String id, PageRequest pageRequest) {
        Object response = httpService.getWithAuthentication(baseUrl, getProducts, null);
        return null;
    }
}
