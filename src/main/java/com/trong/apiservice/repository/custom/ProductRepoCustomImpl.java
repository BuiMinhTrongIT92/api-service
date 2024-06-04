package com.trong.apiservice.repository.custom;

import com.trong.apiservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepoCustomImpl implements ProductRepoCustom{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateDocument(Update update, String id) {
        mongoTemplate.updateMulti(
                new Query().addCriteria(Criteria.where("_id").is(id)),
                update,
                Product.class
        );
    }
}
