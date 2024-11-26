package id.iztechnology.boilerplate.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import id.iztechnology.boilerplate.application.port.output.UserOutputPort;
import id.iztechnology.boilerplate.domain.model.User;
import id.iztechnology.boilerplate.infrastructure.adapters.input.rest.dto.AddUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserOutputPort userOutputPort;

    public User addUsers(AddUserRequest userRequest) {
        User user = User.builder()
            .name(userRequest.getName())
            .email(userRequest.getEmail())
            .password(userRequest.getPassword())
            .build();

        user = userOutputPort.createUser(user).orElse(null);

        return user;
    }

    public List<User> getAllUsers() {
        return userOutputPort.getAllUsers();
    }
    
}
