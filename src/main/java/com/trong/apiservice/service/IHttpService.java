package com.trong.apiservice.service;

public interface IHttpService {
    <T> T postWithAuthentication(String baseUrl, String url, Object body, String authentication);

    <T> T getWithAuthentication(String baseUrl, String url, String authentication);
}
