package com.trong.apiservice.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.MessageDigest;
import java.util.Map;

public class CommonUtils {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static String checkEmpty(Object param) {
        try {
            if (param != null && !String.valueOf(param).trim().isEmpty()) {
                return objectMapper.writeValueAsString(param);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isCheckEmpty(Object param) {
        if (checkEmpty(param).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static String checkEmptyReturnNull(Object param) {
        if (checkEmpty(param).isEmpty()) {
            return null;
        } else {
            return String.valueOf(param);
        }
    }

    public static <T> T objectToJson(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<T>() {
            });
        } catch (Exception ex) {
            return null;
        }
    }

    public static String objectToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Map<String, Object> objectToMap(Object object) {
        try {
            return objectMapper.convertValue(object, Map.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String hashMD5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}
