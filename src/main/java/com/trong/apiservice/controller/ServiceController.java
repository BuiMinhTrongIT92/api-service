package com.trong.apiservice.controller;

import com.trong.apiservice.model.Customer;
import com.trong.apiservice.model.Employee;
import com.trong.apiservice.repository.CustomerRepo;
import com.trong.apiservice.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
