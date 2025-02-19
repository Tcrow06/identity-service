package com.example.identity_service.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.example.identity_service.entity.Role;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<Role> roles;
}
