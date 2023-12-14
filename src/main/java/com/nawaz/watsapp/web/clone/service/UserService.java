package com.nawaz.watsapp.web.clone.service;

import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.payloads.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto registerNewUser(User user);
    User extractHeaderFromRequest(HttpServletRequest request);

//    boolean sendVerificationToEmail(String username, String email);
//    boolean verifyConfirmationCode(String email, String password);

    List<UserDto> searchUsersByUsername(String username);




}
