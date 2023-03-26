package com.jwt.test.api01.test;

import com.jwt.test.api01.util.JWTUtil;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@Log4j2
@SpringBootTest
public class JWTUtilTests {
    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerate(){
        Map<String,Object> claimMap = Map.of("mid","ABCDE");

        String jwtStr = jwtUtil.generateToken(claimMap,1);
        log.info(jwtStr);
    }

    @Test
    public void testValidate(){
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzk4NDcxNjgsIm1pZCI6IkFCQ0RFIiwiaWF0IjoxNjc5ODQ3MTA4fQ.C5dmpXeLSL5tHAh_wtNdt5ofxJ8tWhWKAiNfbOlgAKg";
        Map<String,Object> claim = jwtUtil.validateToken(jwtStr);
        log.info(claim);
    }

    @Test
    public void testAll(){
        Map<String,Object> claimMap = Map.of("mid","ABCDE","email","aaaa@bbb.com");

        String jwtStr = jwtUtil.generateToken(claimMap,1);
        log.info(jwtStr);

        Map<String,Object> claim = jwtUtil.validateToken(jwtStr);
        log.info("mid:"+claim.get("mid"));
        log.info("email:"+claim.get("email"));
    }
}
