package com.jwt.test.api01.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class JWTUtil {

    @Value("${com.jwt.test.secret}")
    private String key;

    public String generateToken(Map<String,Object> valueMap, int days){
        log.info("generateKey..."+key);
        //헤더
        Map<String,Object> headers = new HashMap<>();
        headers.put("typ","JWT");
        headers.put("alg","HS256");
        //페이로드
        Map<String,Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);
        //테스트 시 짧은 유효기간
        //int time = (1) * days;//테스트는 분단위로 나중에 60*24 (일) 단위 변경
        int time = ( 60 * 24 )* days;

        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(SignatureAlgorithm.HS256,key.getBytes())
                .compact();

        return jwtStr;
    }

    public Map<String,Object> validateToken(String token) throws JwtException{
        Map<String,Object> claims = null;

        claims = Jwts.parser()
                .setSigningKey(key.getBytes()) //Set Key
                .parseClaimsJws(token) // 파싱 및 검증, 실패시 에러
                .getBody();
        return claims;
    }
}
