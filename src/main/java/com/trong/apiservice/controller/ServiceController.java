package com.trong.apiservice.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trong.apiservice.configuration.aspect.UsingAspect;
import com.trong.apiservice.configuration.handler.ErrorCode;
import com.trong.apiservice.configuration.handler.HttpException;
import com.trong.apiservice.model.AuthRequestDTO;
import com.trong.apiservice.model.dto.ProductDTO;
import com.trong.apiservice.repository.CustomerRepo;
import com.trong.apiservice.repository.EmployeeRepo;
import com.trong.apiservice.repository.SQLUserRepo;
import com.trong.apiservice.service.IProductService;
import com.trong.apiservice.service.impl.JWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ServiceController {

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private SQLUserRepo sqlUserRepo;
    @Autowired
    private IProductService productService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/login")
    @UsingAspect
    public ResponseEntity<Object> login(@RequestBody @Valid AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                Map<String, Object> response = new HashMap<>();
                response.put("token", jwtService.generateToken(authRequestDTO.getUsername()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new UsernameNotFoundException("User info invalid");
            }
        } catch (Exception e) {
            String message = "";
            if (e instanceof BadCredentialsException) {
                message = ErrorCode.BAD_CREDENTIALS_EXCEPTION.getMessage();
            }
            return new ResponseEntity<>(new HttpException(ErrorCode.UNAUTHORIZED, message), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/get-products")
    @UsingAspect
    public ResponseEntity<Object> getProducts() {
        Object products = productService.getAllProducts(null, null, null);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/update-products")
    @UsingAspect
    public ResponseEntity<Object> updateProduct(@RequestBody Map<String, Object> productDTO) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ProductDTO product = objectMapper.convertValue(productDTO, ProductDTO.class);
        productService.updateProduct(product, productDTO.get("_id").toString());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
