package com.shoppeapp.shoppe.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "User")
@Table(name = "\"user\"")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "user_id_sequence", sequenceName = "user_id_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    @Column(name = "user_id", updatable = false)
    private long user_id;

    @Getter
    @Setter
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Getter
    @Setter
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Getter
    @Setter
    @Column(name = "date_registered", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime date_registered;

    public User(String name, String password, LocalDateTime date_registered) {
        this.name = name;
        this.password = password;
        this.date_registered = date_registered;
    }
}
