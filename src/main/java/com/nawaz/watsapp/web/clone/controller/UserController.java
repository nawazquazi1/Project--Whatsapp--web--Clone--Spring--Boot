package com.nawaz.watsapp.web.clone.controller;

import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.payloads.UserDto;
import com.nawaz.watsapp.web.clone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/search-user")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String userName) {
        List<User> searchUsers = this.userService.searchUsersByUsername(userName);
        return new ResponseEntity<>(searchUsers, HttpStatus.OK);
    }
}
