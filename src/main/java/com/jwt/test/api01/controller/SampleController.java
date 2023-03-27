package com.jwt.test.api01.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/sample")
public class SampleController {
    @ApiOperation("Sample Get doA")
    @RequestMapping("/doA")
    public List<String> doA(){
        return Arrays.asList("AAA","BBB","ccc");
    }

}
