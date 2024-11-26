package id.iztechnology.boilerplate.infrastructure.adapters.input.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import id.iztechnology.boilerplate.domain.model.User;
import id.iztechnology.boilerplate.infrastructure.adapters.input.rest.dto.UserResponse;

@Mapper
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default UserResponse toUserResponse(User user) {
        return UserResponse.builder()
            .name(user.getName())
            .email(user.getEmail())
            .build();
    }

}
