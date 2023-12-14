package com.nawaz.watsapp.web.clone.service.impl;

import com.nawaz.watsapp.web.clone.entity.Chat;
import com.nawaz.watsapp.web.clone.entity.Message;
import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.exceptions.ResourceNotFoundException;
import com.nawaz.watsapp.web.clone.payloads.SendMessageRequest;
import com.nawaz.watsapp.web.clone.repository.MessageRepo;
import com.nawaz.watsapp.web.clone.repository.UserRepo;
import com.nawaz.watsapp.web.clone.service.ChatService;
import com.nawaz.watsapp.web.clone.service.MessageService;
import com.nawaz.watsapp.web.clone.service.UserService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private MessageRepo messageRepo;
    private ChatService chatService;
    private UserService userService;
    private UserRepo userRepo;

    @Override
    public Message sendMessage(SendMessageRequest messageRequest) {

        User user = this
                .userRepo
                .findById(messageRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", messageRequest.getUserId()));

        Chat chat = this
                .chatService
                .findChatById(messageRequest.getChatId());
        Message message = new Message();
        message.setChat(chat);
        message.setSender(user);
        message.setMessageText(messageRequest.getContent());
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    @Override
    public List<Message> getChatMessages(Long chatId) {
        return this.messageRepo.findByChatId(chatId).orElseThrow(() -> new ResourceNotFoundException("chat", "id", chatId));
    }
    
    @Override
    public Message findMessageBYId(Long messageId) {
        return this.messageRepo.findById(messageId).orElseThrow(() -> new ResourceNotFoundException("message", "id", messageId));
    }

    @Override
    public void deleteMessage(Long messageId) {
        this.messageRepo.deleteById(messageId);
    }
}
