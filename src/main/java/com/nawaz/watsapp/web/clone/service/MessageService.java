package com.nawaz.watsapp.web.clone.service;

import com.nawaz.watsapp.web.clone.entity.Message;
import com.nawaz.watsapp.web.clone.payloads.SendMessageRequest;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    Message sendMessage(SendMessageRequest messageRequest);

    List<Message> getChatMessages(Long chatId);

    Message findMessageBYId(Long messageId);

    void deleteMessage(Long messageId);


}
