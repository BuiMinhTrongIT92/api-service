//package com.trong.apiservice.service.impl;
//
//import com.trong.apiservice.model.CustomUserDetails;
//import com.trong.apiservice.model.SQLEntity.SQLUser;
//import com.trong.apiservice.repository.SQLUserRepo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class CustomUserDetailService implements UserDetailsService {
//    @Autowired
//    private SQLUserRepo sqlUserRepo;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("Entering in loadUserByUsername Method...");
//        SQLUser sqlUser = sqlUserRepo.findOneByUserName(username);
//        if (null == sqlUser || !username.trim().equals(sqlUser.getUserName().trim())) {
//            log.error("User not found: " + username);
//            throw new UsernameNotFoundException("Could not found user..!!");
//        } else {
//            return new CustomUserDetails(sqlUser);
//        }
//    }
//}
