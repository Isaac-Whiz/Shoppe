package com.shoppeapp.shoppe.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> authenticateUser(String name, String password) {
        return userRepository.findUserByNameAndPassword(name, password);
    }

    public long count() {
        return userRepository.count();
    }

}
