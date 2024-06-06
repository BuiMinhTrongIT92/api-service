package com.trong.apiservice.repository.custom;

import com.trong.apiservice.model.Product;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface ProductRepoCustom {
    void updateDocument(Update update, String id);

    <T> T findAllByCondition(List<Criteria> criteriaList);
}
