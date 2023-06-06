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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    public void addUserNotify(UserRequest userRequest) throws CurrencyNotFoundException {
        Currency currency = currencyRepository
                .findBySymbol(userRequest.getCurrencySymbol())
                .orElseThrow(() -> new CurrencyNotFoundException(userRequest.getCurrencySymbol()));
        User user = userRequestToUser(userRequest, currency);
        userRepository.save(user);
    }

    public List<UserResponse> findAllUsersByUsername(String username) {
        return userRepository.findAllByUsername(username).stream()
                .map(this::userToUserResponse)
                .toList();
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::userToUserResponse)
                .toList();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .currencySymbol(user.getCurrencySymbol())
                .currencyPrice_usd(user.getCurrencyPrice_usd())
                .build();
    }

    private User userRequestToUser(UserRequest request, Currency currency){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setCurrencySymbol(currency.getSymbol());
        user.setCurrencyPrice_usd(currency.getPrice_usd());
        return user;
    }
}
