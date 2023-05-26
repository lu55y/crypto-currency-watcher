package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.dto.UserRequest;
import com.example.crypto_currency_watcher.dto.UserResponse;
import com.example.crypto_currency_watcher.excepton.CurrencyNotFoundException;
import com.example.crypto_currency_watcher.model.Currency;
import com.example.crypto_currency_watcher.model.User;
import com.example.crypto_currency_watcher.repository.CurrencyRepository;
import com.example.crypto_currency_watcher.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional(readOnly = true)
    public void addUserNotify(UserRequest userRequest) throws CurrencyNotFoundException {
        User user = new User();
        Currency currency = currencyRepository
                .findBySymbol(userRequest.getSymbol())
                .orElseThrow(() -> new CurrencyNotFoundException(userRequest.getSymbol()));

        user.setUsername(userRequest.getUsername());
        user.setCurrencySymbol(currency.getSymbol());
        user.setCurrencyPrice_usd(currency.getPrice_usd());

        userRepository.save(user);
    }

    public List<UserResponse> findAllUsersByUsername(List<String> username) {
        return userRepository.findAllByUsernameIn(username).stream()
                .map(this::userToUserResponse)
                .toList();
    }

/*    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::userToUserResponse)
                .toList();
    }*/

    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }

    private UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .currencySymbol(user.getCurrencySymbol())
                .currencyPrice_usd(user.getCurrencyPrice_usd())
                .build();
    }
}
