package com.nawaz.watsapp.web.clone.controller;

import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.exceptions.ApiException;
import com.nawaz.watsapp.web.clone.exceptions.ErrorResponse;
import com.nawaz.watsapp.web.clone.payloads.JwtAuthenticationRequest;
import com.nawaz.watsapp.web.clone.payloads.JwtAuthenticationResponse;
import com.nawaz.watsapp.web.clone.payloads.UserDto;
import com.nawaz.watsapp.web.clone.repository.UserRepo;
import com.nawaz.watsapp.web.clone.security.JwtTokenHelper;
import com.nawaz.watsapp.web.clone.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat/authentication")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenHelper jwtTokenHelper;
    private final ModelMapper mapper;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, build a response with error messages
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
        }

        User user = this.mapper.map(userDto, User.class);
        this.userService.registerNewUser(user);
        String token = this.jwtTokenHelper.generateToken(user);

        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> loginUser(@RequestBody JwtAuthenticationRequest request) throws Exception {
        this.authenticate(request.getUsername(), request.getPassword());
        User user=this.userRepo.findByUserName(request.getUsername()).orElseThrow();
        String token=this.jwtTokenHelper.generateToken(user);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }


    public void authenticate(String userName, String password) throws Exception{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid Username or password !!");
        }
    }


}
