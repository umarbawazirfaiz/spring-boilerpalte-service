package id.iztechnology.boilerplate.application.port.input;

import java.util.List;

import id.iztechnology.boilerplate.domain.model.User;

public interface UserInputPort {
    User createUser(AddUserCommand addUserCommand);
    User getUserById(String id);
    List<User> getAllUsers();
}