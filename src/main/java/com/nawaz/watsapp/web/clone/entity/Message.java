package com.nawaz.watsapp.web.clone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;


    @Column(nullable = false, length = 500)
    private String messageText;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}
