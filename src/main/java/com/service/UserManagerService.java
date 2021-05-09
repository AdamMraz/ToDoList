package com.service;

import com.model.User;
import com.service.exceptions.UserIsException;
import com.service.exceptions.UserIsNotException;

import java.util.List;

public interface UserManagerService {
    User findByUsername(String username) throws UserIsNotException, Exception;

    User findByApiKey(String apiKey) throws UserIsNotException;

    User findUserById(int id) throws Exception;

    List<User> allUsers ();

    void saveUser(User user) throws UserIsException;

    void deleteUser(int id) throws UserIsException;
}
