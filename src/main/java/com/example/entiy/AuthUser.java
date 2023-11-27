package com.example.entiy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUser {
    int id;
    String name;
    String password;
    String role;
}
