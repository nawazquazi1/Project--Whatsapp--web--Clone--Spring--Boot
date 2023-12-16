package com.nawaz.watsapp.web.clone.service;

import com.nawaz.watsapp.web.clone.entity.Message;
import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.payloads.SendMessageRequest;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MessageService {

    Message saveMessage(SendMessageRequest messageRequest);

    List<Message> getChatMessages(Long chatId,User reqUser);

    Message findMessageBYId(Long messageId);

    void deleteMessage(Long messageId, User reqUser);


}
