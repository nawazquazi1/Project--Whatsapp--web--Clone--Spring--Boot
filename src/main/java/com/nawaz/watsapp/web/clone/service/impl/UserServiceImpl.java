package com.nawaz.watsapp.web.clone.service.impl;

import com.nawaz.watsapp.web.clone.config.AppConstants;
import com.nawaz.watsapp.web.clone.entity.Role;
import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.payloads.UserDto;
import com.nawaz.watsapp.web.clone.repository.RoleRepo;
import com.nawaz.watsapp.web.clone.repository.UserRepo;
import com.nawaz.watsapp.web.clone.security.JwtTokenHelper;
import com.nawaz.watsapp.web.clone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final JwtTokenHelper tokenHelper;

    @Override
    public UserDto registerNewUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);

        User userDto = this.userRepo.save(user);
        return this.mapper.map(userDto, UserDto.class);
    }

    @Override
    public User extractHeaderFromRequest(HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        Long id = this.tokenHelper.getIdFromToken(token);
        return this.userRepo.findById(id).orElseThrow();
    }

    @Override
    public List<UserDto> searchUsersByUsername(String username) {
        return null;
    }
}
