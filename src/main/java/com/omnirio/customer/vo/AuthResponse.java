package com.omnirio.customer.vo;

import java.io.Serial;
import java.io.Serializable;

public class AuthResponse implements Serializable {
    private final String jwtToken;

    public AuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
