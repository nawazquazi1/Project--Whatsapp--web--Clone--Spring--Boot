package com.nawaz.watsapp.web.clone.service.impl;

import com.nawaz.watsapp.web.clone.entity.Chat;
import com.nawaz.watsapp.web.clone.entity.User;
import com.nawaz.watsapp.web.clone.exceptions.ApiException;
import com.nawaz.watsapp.web.clone.exceptions.ResourceNotFoundException;
import com.nawaz.watsapp.web.clone.payloads.GroupChatRequest;
import com.nawaz.watsapp.web.clone.repository.ChatRepo;
import com.nawaz.watsapp.web.clone.repository.UserRepo;
import com.nawaz.watsapp.web.clone.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private ChatRepo chatRepo;
    private UserRepo userRepo;

    @Override
    public Chat createChat(User reqUser, Long user2) {
        User user = this.userRepo.findById(user2)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", user2));
        Chat isChatExist = chatRepo.findSingleChatByUserId(user, reqUser);
        if (isChatExist != null) {
            return isChatExist;
        }
        Chat chat = new Chat();
        chat.setCreateBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);
        return this.chatRepo.save(chat);
    }

    @Override
    public List<Chat> findAllChatById(Long id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        return this.chatRepo.findByUsersId(user.getId());
    }

    @Override
    public Chat findChatById(Long id) {
        return chatRepo
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", id));
    }

    @Override
    public Chat createGroup(GroupChatRequest request, User reqUser) {
        Chat group = new Chat();
        group.setGroup(true);
        group.setChatImage(request.getChatImage());
        group.setChatName(request.getChatName());
        group.setCreateBy(reqUser);
        group.getAdmins().add(reqUser);
        request.getUserIDs().stream()
                .map(userId -> userRepo.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId)))
                .forEach(user -> group.getUsers().add(user));

        return this.chatRepo.save(group);
    }

    @Override
    public Chat addUserToGroup(Long userId, Long chatId, User userID) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        Chat group = this.chatRepo.findById(chatId).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        if (group.getAdmins().contains(userID)) {
            group.getUsers().add(user);
            return this.chatRepo.save(group);
        }
        throw new ApiException("You don't have access to add user");
    }

    @Override
    public Chat reNameGroup(Long chatId, String groupName, User reqUser) {
        Chat group = this.chatRepo.findById(chatId).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        if (group.getAdmins().contains(reqUser)) {
            group.setChatName(groupName);
            return this.chatRepo.save(group);
        }
        throw new ApiException("You don't have access to reName Group");
    }

    @Override
    public Chat removeFromGroup(Long chatId, Long userId, User reqUser) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        Chat group = this.chatRepo.findById(chatId).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        if (group.getAdmins().contains(reqUser)) {
            group.getUsers().remove(user);
            return this.chatRepo.save(group);
        }
        throw new ApiException("You don't have access to remove user");
    }

    @Override
    public void deletChat(Long chatId, Long UserId) {
        Chat chat = this.chatRepo.findById(chatId).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        this.chatRepo.deleteById(chat.getId());
    }
}
