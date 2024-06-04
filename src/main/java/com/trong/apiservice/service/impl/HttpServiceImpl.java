package com.trong.apiservice.service.impl;

import com.trong.apiservice.configuration.WebClientConfig;
import com.trong.apiservice.configuration.aspect.UsingAspect;
import com.trong.apiservice.configuration.handler.CustomException;
import com.trong.apiservice.configuration.handler.ErrorCode;
import com.trong.apiservice.configuration.handler.HttpException;
import com.trong.apiservice.service.IHttpService;
import com.trong.apiservice.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class HttpServiceImpl implements IHttpService {
    @Autowired
    private WebClientConfig webClientConfig;

    @Override
    @UsingAspect
    public Object postWithAuthentication(String baseUrl, String url, Object body, String authentication) {
        WebClient.RequestBodySpec requestSpec = webClientConfig.getWebClient().baseUrl(baseUrl).build().post().uri(url);
        try {
            if (body != null) {
                requestSpec.bodyValue(body);
            }
            requestSpec.headers(h -> h.setBearerAuth(authentication));
            return executeRequest(requestSpec.retrieve());
        } catch (Exception ex) {
            log.error(String.join(":", "ERROR POST Request with authentication ", url, ex.getMessage()));
            return new HttpException(ErrorCode.SYSTEM_ERROR, "ERROR: POST Request with authentication");
        }
    }

    @Override
    @UsingAspect
    public Object getWithAuthentication(String baseUrl, String url, String authentication) {
        WebClient.RequestHeadersSpec<?> requestSpec = webClientConfig.getWebClient().baseUrl(baseUrl).build().get().uri(url);
        try {
            requestSpec.headers(h -> h.setBearerAuth(authentication));
            return executeRequest(requestSpec.retrieve());
        } catch (Exception ex) {
            log.error(String.join(": ", "ERROR GET Request with authentication: ", url, ex.getMessage()));
            return new HttpException(ErrorCode.SYSTEM_ERROR, "ERROR: GET Request with authentication");
        }
    }

    private Object executeRequest(WebClient.ResponseSpec requestSpec) {
        ResponseEntity<String> responseEntity = requestSpec.onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new WebClientResponseException(
                                        "ERROR " + body,
                                        response.statusCode().value(), null,
                                        response.headers().asHttpHeaders(),
                                        null,
                                        null
                                )))
                )
                .toEntity(String.class)
                .block();
        if (responseEntity != null) {
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return CommonUtils.objectToJson(responseEntity.getBody());
            } else {
                throw new CustomException(ErrorCode.SYSTEM_ERROR, "Execute Request Fail, Status Code From Request: " + responseEntity.getStatusCode().value());
            }
        } else {
            throw new CustomException(ErrorCode.SYSTEM_ERROR, "Execute Request Fail");
        }
    }
}
