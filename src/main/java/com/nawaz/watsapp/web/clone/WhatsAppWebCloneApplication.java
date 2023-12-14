package com.nawaz.watsapp.web.clone;

import com.nawaz.watsapp.web.clone.config.AppConstants;
import com.nawaz.watsapp.web.clone.entity.Role;
import com.nawaz.watsapp.web.clone.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class WhatsAppWebCloneApplication {


    public static void main(String[] args) {
        SpringApplication.run(WhatsAppWebCloneApplication.class, args);
    }

}

