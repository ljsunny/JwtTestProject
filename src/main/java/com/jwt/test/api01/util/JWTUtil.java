package com.jwt.test.api01.util;

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Log4j2
@Component
public class JWTUtil {

    @Value("${com.jwt.test.secret}")
    private String key;

    public String generateToken(Map<String,Object> valueMap, int days){
        log.info("generateKey..."+key);
        return null;
    }

    public Map<String,Object> validateToken(String token) throws JwtException{
        Map<String,Object> claim = null;

        return claim;
    }
}
