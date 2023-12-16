package com.nawaz.watsapp.web.clone.controller;

import com.nawaz.watsapp.web.clone.entity.Chat;
import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.exceptions.ApiException;
import com.nawaz.watsapp.web.clone.exceptions.ResourceNotFoundException;
import com.nawaz.watsapp.web.clone.payloads.ApiResponse;
import com.nawaz.watsapp.web.clone.payloads.GroupChatRequest;
import com.nawaz.watsapp.web.clone.payloads.JwtAuthenticationRequest;
import com.nawaz.watsapp.web.clone.repository.ChatRepo;
import com.nawaz.watsapp.web.clone.repository.UserRepo;
import com.nawaz.watsapp.web.clone.security.JwtTokenHelper;
import com.nawaz.watsapp.web.clone.service.ChatService;
import com.nawaz.watsapp.web.clone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {

    private UserRepo userRepo;
    private ChatService chatService;
    private JwtTokenHelper tokenHelper;
    private UserService userService;

    @PostMapping("/single/{userId}")
    private ResponseEntity<Chat> createChat(@PathVariable("userId") Long userId, HttpServletRequest request) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        User user1=this.userService.extractHeaderFromRequest(request);
        Chat chat = this.chatService.createChat(user1, user.getId());
        return ResponseEntity.ok(chat);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroup(@RequestBody GroupChatRequest request, HttpServletRequest httpServletRequest) {
        User user = this.userService.extractHeaderFromRequest(httpServletRequest);
        Chat chat = this.chatService.createGroup(request, user);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("{chatId}")
    public ResponseEntity<Chat> findChatByChatId(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(this.chatService.findChatById(chatId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<Chat>> findAllUserChat(HttpServletRequest request) {
        User user = this.userService.extractHeaderFromRequest(request);
        List<Chat> chats = this.chatService.findAllChatById(user.getId());
        return ResponseEntity.ok(chats);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUsersToGroup(@PathVariable Long chatId, @PathVariable Long userId, HttpServletRequest request) {
        User reqUser = this.userService.extractHeaderFromRequest(request);
        Chat chat = this.chatService.addUserToGroup(userId, chatId, reqUser);
        return ResponseEntity.ok(chat);
    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUsersToGroup(@PathVariable Long chatId, @PathVariable Long userId, HttpServletRequest request) {
        User reqUser = this.userService.extractHeaderFromRequest(request);
        Chat chat = this.chatService.removeFromGroup(chatId, userId, reqUser);
        return ResponseEntity.ok(chat);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChat(@PathVariable Long chatId, HttpServletRequest request) {
        User user = this.userService.extractHeaderFromRequest(request);
        this.chatService.deletChat(chatId, user.getId());
        ApiResponse response = new ApiResponse("Chat is deleted successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
