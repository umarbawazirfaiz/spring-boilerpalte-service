package id.iztechnology.boilerplate.application.service;

import java.util.List;

import id.iztechnology.boilerplate.application.model.user.AddUserCommand;
import id.iztechnology.boilerplate.domain.model.User;

public interface IUserService {

    User createUser(AddUserCommand addUserCommand);
    User getUserById(String id);
    List<User> getAllUsers();

}