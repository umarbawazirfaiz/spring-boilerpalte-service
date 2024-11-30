package id.iztechnology.boilerplate.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import id.iztechnology.boilerplate.application.port.output.UserOutputPort;
import id.iztechnology.boilerplate.domain.model.User;
import id.iztechnology.boilerplate.infrastructure.adapters.input.rest.dto.AddUserRequest;
import id.iztechnology.boilerplate.infrastructure.config.logging.LogWrapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserOutputPort userOutputPort;

    private final LogWrapper log;

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
        log.info("test njing");
        List<User> users = userOutputPort.getAllUsers();
        log.info("ini hasil {}", users);
        return userOutputPort.getAllUsers();
    }
    
}
