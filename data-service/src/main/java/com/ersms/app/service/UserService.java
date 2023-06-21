package com.ersms.app.service;

import com.ersms.app.exception.RuntimeExceptionWithHttpStatus;
import com.ersms.app.model.response.UserDto;
import com.ersms.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getUser(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeExceptionWithHttpStatus("User with given email cannot be found", HttpStatus.NOT_FOUND));

        return UserDto.from(user);
    }

    public String getUserRole(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeExceptionWithHttpStatus("User with given email cannot be found", HttpStatus.NOT_FOUND));

        return String.valueOf(user.getRole());
    }
}






