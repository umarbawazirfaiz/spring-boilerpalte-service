package id.iztechnology.boilerplate.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import id.iztechnology.boilerplate.application.model.user.AddUserCommand;
import id.iztechnology.boilerplate.application.repository.user.UserRespository;
import id.iztechnology.boilerplate.common.logging.LogWrapper;
import id.iztechnology.boilerplate.domain.model.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRespository userRespository;

    private final LogWrapper log;

    public User createUser(AddUserCommand addUserCommand) {
        User user = User.builder()
            .name(addUserCommand.getName())
            .email(addUserCommand.getEmail())
            .password(addUserCommand.getPassword())
            .build();

        user = userRespository.createUser(user).orElse(null);

        return user;
    }

    public User getUserById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<User> getAllUsers() {
        log.setMessage("test message user service");

        List<User> users = userRespository.getAllUsers();
        return userRespository.getAllUsers();
    }

}
