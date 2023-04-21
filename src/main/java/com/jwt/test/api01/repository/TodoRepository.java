package com.jwt.test.api01.repository;

import com.jwt.test.api01.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository
        extends JpaRepository<Todo, Long> {
}
