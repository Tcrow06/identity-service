package com.example.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    @ElementCollection(fetch = FetchType.EAGER) // Danh dau day la cac gia tri don gian
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))// tao bang phu user_roles để lưu các giá trị roles, liên kết với bảng User thông qua user_id
    @Column(name = "role")
    Set<String> roles ;
}
