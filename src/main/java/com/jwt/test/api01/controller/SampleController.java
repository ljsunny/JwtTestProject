package com.jwt.test.api01.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

    @Operation
    @RequestMapping("/doA")
    public List<String> doA(){
        return Arrays.asList("AAA","BBB","ccc");
    }

}
