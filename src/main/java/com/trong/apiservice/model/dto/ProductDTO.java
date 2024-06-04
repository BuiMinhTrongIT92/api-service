package com.trong.apiservice.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @Id
    private Object _id;
    private String productCode;
    private String productName;
    private String productLine;
    private String productScale;
    private String productVendor;
    private String productDescription;
    private Integer quantityInStock;
    private Double buyPrice;
    @JsonAlias("MSRP")
    private Double msrp;

    public String getRedisCode() {
        return String.format("%s:%s", productCode, productName);
    }
}
