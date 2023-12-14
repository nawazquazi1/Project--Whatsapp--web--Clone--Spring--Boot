package com.nawaz.watsapp.web.clone.repository;

import com.nawaz.watsapp.web.clone.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {

    @Query("select m from Message m join m.chat c where c.id=:chatId")
    public Optional<List<Message>> findByChatId(@Param("chatId") Long chatId);

}
