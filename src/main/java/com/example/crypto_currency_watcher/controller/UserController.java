package com.example.crypto_currency_watcher.controller;

import com.example.crypto_currency_watcher.dto.UserRequest;
import com.example.crypto_currency_watcher.dto.UserResponse;
import com.example.crypto_currency_watcher.excepton.CurrencyNotFoundException;
import com.example.crypto_currency_watcher.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String notify(@RequestBody UserRequest userRequest) {
        try {
            userService.addUserNotify(userRequest);
            return "Added User Notification";
        } catch (CurrencyNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency Not Found");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> findUsers(@RequestParam List<String> username) {
        return userService.findAllUsersByUsername(username);
    }

/*    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> findAllUsers() {
        return userService.findAllUsers();
    }*/

    @DeleteMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
    }
}
