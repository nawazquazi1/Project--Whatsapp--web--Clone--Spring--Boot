package com.nawaz.watsapp.web.clone.controller;

import com.nawaz.watsapp.web.clone.entity.Message;
import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.payloads.ApiResponse;
import com.nawaz.watsapp.web.clone.payloads.SendMessageRequest;
import com.nawaz.watsapp.web.clone.service.ChatService;
import com.nawaz.watsapp.web.clone.service.MessageService;
import com.nawaz.watsapp.web.clone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/chat/messages")
public class MessageController {

    private MessageService messageService;
    private ChatService chatService;
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<Message> saveMessage(@RequestBody SendMessageRequest request, HttpServletRequest httpServletRequest) {
        User user = this.userService.extractHeaderFromRequest(httpServletRequest);
        request.setUserId(user.getId());
        Message message = this.messageService.saveMessage(request);
        return ResponseEntity.ok(message);
    }


    @GetMapping("/{chatId}")
    public ResponseEntity<List<Message>> getChatsMessage(@PathVariable("chatId") Long chatId, HttpServletRequest request) {
        User user = this.userService.extractHeaderFromRequest(request);
        List<Message> message = this.messageService.getChatMessages(chatId, user);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessage(@PathVariable Long messageId, HttpServletRequest request) {
        User user = this.userService.extractHeaderFromRequest(request);
        this.messageService.deleteMessage(messageId, user);
        ApiResponse apiResponse = new ApiResponse("Message deleted successfully", false);
        return ResponseEntity.ok(apiResponse);

    }
}
