package com.trong.apiservice.repository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
public class Demo {
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode originalData = mapper.readTree(new File("/Users/trongbui/Documents/ProjectStudy/Microservice/api-service/src/main/java/com/trong/apiservice/repository/customers.json"));

        ArrayNode newData = mapper.createArrayNode();

        for (int i = 0; i < 50; i++) {
            for (JsonNode customer : originalData) {
                ObjectNode newCustomer = generateRandomCustomer(mapper);
                newData.add(newCustomer);
            }
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("/Users/trongbui/Documents/ProjectStudy/Microservice/api-service/src/main/java/com/trong/apiservice/utils/customers.json"), newData);
    }

    private static ObjectNode generateRandomCustomer(ObjectMapper mapper) {
        ObjectNode customer = mapper.createObjectNode();

        customer.put("_id", UUID.randomUUID().toString().replace("-", ""));
        customer.put("addressLine1", generateRandomString(10) + " St.");
        customer.put("addressLine2", (String) null);
        customer.put("city", generateRandomString(6));
        customer.put("contactFirstName", generateRandomString(6));
        customer.put("contactLastName", generateRandomString(6));
        customer.put("country", generateRandomString(6));
        customer.put("creditLimit", generateRandomCreditLimit());
        customer.put("customerName", generateRandomString(15));
        customer.put("customerNumber", generateRandomString(3));
        customer.put("phone", generateRandomPhone());
        customer.put("postalCode", generateRandomString(5));
        customer.put("salesRepEmployeeNumber", generateRandomString(4));
        customer.put("state", generateRandomString(2));

        return customer;
    }

    private static String generateRandomString(int length) {
        return random.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static String generateRandomPhone() {
        return random.ints(10, 0, 10)
                .mapToObj(String::valueOf)
                .reduce("", String::concat);
    }

    private static String generateRandomCreditLimit() {
        return String.valueOf(random.nextInt(1000000) + 1000);
    }
}
