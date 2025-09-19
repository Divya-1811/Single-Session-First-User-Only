package com.demo.session_expiry.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationRequest {
    private String field;
    private String country;
}
