package com.jwt.test.api01.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class APIUser {
    @Id
    private String mid;
    private String mpw;

    public void changePw(String mpw){
        this.mpw = mpw;
    }
}
