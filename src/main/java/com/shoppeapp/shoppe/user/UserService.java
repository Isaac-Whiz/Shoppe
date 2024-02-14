package com.shoppeapp.shoppe.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        UserService.userRepository = userRepository;
    }


    public static void save(User user) {
        userRepository.save(user);
    }


    public static boolean authenticateUser(String name, String password) {
        return userRepository.existsUserByNameAndPassword(name, password);
    }

    public long count() {
        return userRepository.count();
    }

    public static boolean isUserAlreadyExist(String name){
        return userRepository.existsByName(name);
    }

}
