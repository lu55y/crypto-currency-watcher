package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.model.User;
import com.example.crypto_currency_watcher.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {

        return userRepository.getReferenceById(id);
    }

    @Override
    public User getByName(String userName) {

        return userRepository.findUserByUserName(userName);
    }

    @Override
    public void save(User user) {

        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {

        userRepository.deleteById(id);
    }


}
