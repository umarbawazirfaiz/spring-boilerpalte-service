package id.iztechnology.boilerplate.infrastructure.adapters.http_server.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import id.iztechnology.boilerplate.domain.model.User;
import id.iztechnology.boilerplate.infrastructure.adapters.http_server.user.dto.UserResponse;

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
