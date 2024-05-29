package com.trong.apiservice.controller;

import com.trong.apiservice.configuration.aspect.UsingAspect;
import com.trong.apiservice.configuration.handler.ErrorCode;
import com.trong.apiservice.configuration.handler.HttpException;
import com.trong.apiservice.model.AuthRequestDTO;
import com.trong.apiservice.model.Customer;
import com.trong.apiservice.model.Employee;
import com.trong.apiservice.model.SQLEntity.SQLUser;
import com.trong.apiservice.repository.CustomerRepo;
import com.trong.apiservice.repository.EmployeeRepo;
import com.trong.apiservice.repository.SQLUserRepo;
import com.trong.apiservice.service.IHttpService;
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
import java.util.List;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

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

    @GetMapping("/customers")
    @UsingAspect
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @GetMapping("/employees")
    @UsingAspect
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/users")
    @UsingAspect
    public ResponseEntity<Iterable<SQLUser>> getUser() {
        Iterable<SQLUser> users = sqlUserRepo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
