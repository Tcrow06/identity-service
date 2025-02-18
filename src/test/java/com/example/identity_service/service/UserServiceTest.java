package com.example.identity_service.service;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@Slf4j
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;

    private User user;

    private LocalDate dob;
    @BeforeEach
    void initData(){
        dob = LocalDate.of(2000,12,3);
        request = UserCreationRequest.builder()
                .username("thinh1")
                .lastName("thinh1")
                .firstName("thinh1")
                .password("12345678")
                .dob(dob)
                .build();
        userResponse= UserResponse.builder()
                .id("23121ddas12")
                .username("thinh1")
                .lastName("thinh1")
                .firstName("thinh1")
                .dob(dob)
                .build();
        user = User.builder()
                .id("23121ddas12")
                .username("thinh1")
                .lastName("thinh1")
                .firstName("thinh1")
                .dob(dob)
                .build();
    }
    @Test
    void createUser_validRequest_success(){
        //GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        //WHEN
        var response = userService.createUser(request);

        //THEN

        Assertions.assertThat(response.getId()).isEqualTo("23121ddas12");
        Assertions.assertThat(response.getUsername()).isEqualTo("thinh1");


    }

    @Test
    void createUser_userExisted_fail(){
        //GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        //WHEN
        var exception = assertThrows(AppException.class,()-> userService.createUser(request));

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }
}
