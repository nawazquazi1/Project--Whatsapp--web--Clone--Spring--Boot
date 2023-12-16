package com.nawaz.watsapp.web.clone.controller;

import com.nawaz.watsapp.web.clone.entity.Message;
import com.nawaz.watsapp.web.clone.payloads.SendMessageRequest;
import com.nawaz.watsapp.web.clone.service.MessageService;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;

@AllArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate smt;

    private MessageService messageService;

    @MessageMapping("/message")
    public Message sendMessageToUser(@Payload Message message) {
        smt.convertAndSendToUser(message.getUser().getId().toString(), "/queue/messages", message);
        SendMessageRequest request = new SendMessageRequest();
        request.setUserId(message.getUser().getId());
        request.setChatId(message.getChat().getId());
        request.setContent(message.getMessageText());
        this.messageService.saveMessage(request);
        return message;
    }

    @MessageMapping("/message")
    public Message sendMessageToGroup(@Payload Message message) {
        smt.convertAndSend(message.getUser().getId().toString() + "/group/messages", message);
        SendMessageRequest request = new SendMessageRequest();
        request.setUserId(message.getUser().getId());
        request.setChatId(message.getChat().getId());
        request.setContent(message.getMessageText());
        this.messageService.saveMessage(request);
        return message;
    }

}
