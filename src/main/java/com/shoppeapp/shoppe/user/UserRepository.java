package com.shoppeapp.shoppe.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByNameAndPassword(String name, String password);

    boolean existsUserByNameAndPassword(String name, String password);

    boolean existsByName(String name);

    Optional<User> findByName(String name);

}
