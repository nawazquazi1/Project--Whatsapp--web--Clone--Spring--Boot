package com.nawaz.watsapp.web.clone.service;

import com.nawaz.watsapp.web.clone.entity.Chat;
import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.payloads.GroupChatRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {

    Chat createChat(User user1, Long user2);

    List<Chat> findAllChatById(Long id);
    Chat findChatById(Long id);

    Chat createGroup(GroupChatRequest request, User reqUser);

    Chat addUserToGroup(Long userId, Long chatId,User reqUser);

    Chat reNameGroup(Long chatId, String groupName,User reqUser);

    Chat removeFromGroup(Long chatId, Long userId,User reqUser);

      void deletChat(Long chatId, Long UserId);
}
