package id.iztechnology.boilerplate.application.repository.user;

import java.util.List;
import java.util.Optional;

import id.iztechnology.boilerplate.domain.model.User;

public interface UserRespository {
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    Optional<User> createUser(User user);
    Optional<User> updateUser(User user);
}
