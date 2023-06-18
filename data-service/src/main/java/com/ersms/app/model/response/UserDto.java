package com.ersms.app.model.response;

import com.ersms.app.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String email;
    private String firstname;
    private String lastname;

    public static UserDto from(UserEntity entity){
        return UserDto.builder()
                .email(entity.getEmail())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .build();
    }
}