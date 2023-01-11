package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable{
    private static final long serialVersionUID = 5926468583005150707L;
    private String token;
    private String id;
    private String type = "Bearer";
    private String username;
    private String password;
    private String email;
    private List roles;

    public JwtResponse(String jwt, String id, String username, String password,  String email, List roles) {
        this.token = this.type +  " " + jwt;
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
    }
}
