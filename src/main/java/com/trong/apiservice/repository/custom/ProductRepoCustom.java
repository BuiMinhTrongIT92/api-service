package com.trong.apiservice.repository.custom;

import org.springframework.data.mongodb.core.query.Update;

public interface ProductRepoCustom {
    void updateDocument(Update update, String id);
}
