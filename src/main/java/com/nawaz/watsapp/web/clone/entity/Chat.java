package com.nawaz.watsapp.web.clone.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "chats")
@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String chatImage;
    private String chatName;
    private boolean isGroup;

    @ManyToOne
    private User createBy;

//    @ManyToOne
//    private User user;

    @ManyToMany
    private Set<User> users=new HashSet<>();

@ManyToMany
    private Set<User> admins=new HashSet<>();

    @OneToMany
    private List<Message> messages=new ArrayList<>();


}
