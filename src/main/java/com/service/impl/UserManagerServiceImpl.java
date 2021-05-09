package com.service.impl;

import com.model.User;
import com.repository.UserRepo;
import com.service.UserManagerService;
import com.service.exceptions.UserIsException;
import com.service.exceptions.UserIsNotException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagerServiceImpl implements UserDetailsService, UserManagerService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Object[] roles = user.getRoles().toArray();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities());
    }

    public User findByUsername(String username) throws Exception {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UserIsNotException();
        }
        return user;
    }

    public User findByApiKey(String apiKey) throws UserIsNotException {
        User user = userRepo.findByApiKey(apiKey);
        if (user == null) {
            throw new UserIsNotException();
        }
        return user;
    }

    public User findUserById(int id) throws Exception {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new UserIsNotException();
        }
        return user.orElse(new User());
    }

    public List<User> allUsers () {
        return userRepo.findAll();
    }

    public void saveUser(User user) throws UserIsException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserIsException();
        }
        user.setId((int)userRepo.count() + 1);
        user.setApiKey(user.getApiKey() + user.getId());
        userRepo.save(user);
    }

    public void deleteUser(int id) throws UserIsException {
        if (userRepo.findById(id).isPresent()) {
            userRepo.deleteById(id);
        }
        else {
            throw new UserIsException();
        }
    }
}
