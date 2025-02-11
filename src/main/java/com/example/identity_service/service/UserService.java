package com.example.identity_service.service;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.UserMapper;
import com.example.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;
    public UserResponse createRequest(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers(){
        return userMapper.toListUserResponse(userRepository.findAll());
    }
    public UserResponse getUser(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return userMapper.toUserResponse(user);
    }
    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException(("User not found")));
        userMapper.updateUser(user,request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
