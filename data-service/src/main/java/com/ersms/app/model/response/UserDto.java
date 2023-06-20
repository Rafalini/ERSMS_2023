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

    public static UserDto from(UserEntity entity){
        return UserDto.builder()
                .email(entity.getEmail())
                .build();
    }
}