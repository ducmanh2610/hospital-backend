package com.hospital.dto;

import com.hospital.validations.PasswordMatches;

public class PasswordRequest {
    private String oldPassword;
    private String token;
    private String newPassword;
}
