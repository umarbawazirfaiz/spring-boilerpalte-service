package id.iztechnology.boilerplate.application.port.output;

import java.util.List;
import java.util.Optional;

import id.iztechnology.boilerplate.domain.model.User;

public interface UserOutputPort {
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    Optional<User> createUser(User user);
    Optional<User> updateUser(User user);
}
