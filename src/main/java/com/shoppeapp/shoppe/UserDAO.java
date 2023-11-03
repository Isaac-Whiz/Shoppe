package com.shoppeapp.shoppe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {

   User findUserById(long id);
}
