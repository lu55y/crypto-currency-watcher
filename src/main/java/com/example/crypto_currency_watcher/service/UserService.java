package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.model.User;

public interface UserService {
    User findById(Long id);
    User getByName(String userName);
    void save(User user);
    void delete(Long id);
}
