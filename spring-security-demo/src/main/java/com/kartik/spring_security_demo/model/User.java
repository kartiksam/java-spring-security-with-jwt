package com.kartik.spring_security_demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Data
@Entity
//here we have a table name User but we alkready created and that table name is "users" so mention that
@Table(name="users")
public class User {
    @Id
//    hibernate will generate the id itself no nedd to pass id

    private int id;
    private String username;
    private String password;
}
