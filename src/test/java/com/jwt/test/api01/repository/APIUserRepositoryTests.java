package com.jwt.test.api01.repository;

import com.jwt.test.api01.domain.APIUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class APIUserRepositoryTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private APIUserRepository apiUserRepository;

    @Test
    public void testInserts(){
        IntStream.rangeClosed(1,100).forEach(i->{
            APIUser apiUser= APIUser.builder()
                    .mid("apiuser"+i)
                    .mpw(passwordEncoder.encode("1111"))
                    .build();

            apiUserRepository.save(apiUser);
        });
    }
}
