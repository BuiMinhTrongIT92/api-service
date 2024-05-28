package com.trong.apiservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    private Object _id;
    private Number employeeNumber;
    private String lastName;
    private String firstName;
    private String extension;
    private String email;
    private Number officeCode;
    private String reportsTo;
    private String jobTitle;
}
