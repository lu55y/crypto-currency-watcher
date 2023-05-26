package com.example.crypto_currency_watcher.repository;


import com.example.crypto_currency_watcher.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteUserByUsername(String username);

    List<User> findAllByUsernameIn(List<String> username);
}
