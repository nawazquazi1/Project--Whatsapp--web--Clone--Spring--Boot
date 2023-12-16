package com.nawaz.watsapp.web.clone.repository;

import com.nawaz.watsapp.web.clone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String username);

    List<User> findByUserNameOrUserNameStartsWith(String userName, String userNameStartWith);

}
