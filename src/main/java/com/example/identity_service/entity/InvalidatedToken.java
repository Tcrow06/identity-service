package com.example.identity_service.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class InvalidatedToken {
    @Id
    String id;

    Date expiryTime; // chính là thời gian expire của token, tạo những cái job chạy định kì để remove đi
    // để làm cho bảng token giảm bớt đi , nhỏ gọn không bị phình ra

}
