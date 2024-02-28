package com.shoppeapp.shoppe.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
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

    public static boolean isUserAlreadyExist(String name){
        return userRepository.existsByName(name);
    }

    public static void changeUserPassword(String name, String password) {
        Optional<User> user  = userRepository.findByName(name);
        User updatedUser = new User(
                user.get().getUser_id(),
                user.get().getName(),
                password,
                user.get().getDate_registered(),
                user.get().getEmail(),
                user.get().getPhoneNumber()
        );
        userRepository.save(updatedUser);
    }

}
