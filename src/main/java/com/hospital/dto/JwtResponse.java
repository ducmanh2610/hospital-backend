package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtResponse implements Serializable{
    private static final long serialVersionUID = 5926468583005150707L;
    private String token;
    private String username;
    private String email;
    private List<String> roles = new ArrayList<>();

}
