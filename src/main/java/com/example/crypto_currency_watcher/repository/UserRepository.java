package com.example.crypto_currency_watcher.repository;

import com.example.crypto_currency_watcher.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUserName(String userName);
}
