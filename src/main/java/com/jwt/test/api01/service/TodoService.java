package com.jwt.test.api01.service;

import com.jwt.test.api01.dto.TodoDTO;
import jakarta.transaction.Transactional;

@Transactional
public interface TodoService {
    Long register(TodoDTO todoDTO);
}
