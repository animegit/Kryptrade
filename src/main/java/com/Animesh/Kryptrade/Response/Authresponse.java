package com.Animesh.Kryptrade.Response;

import lombok.Data;

@Data
public class Authresponse {
    private String jwt;
    private boolean status;
    private String message;
    private boolean isTwoFactorAuth;
    private String session;
}
