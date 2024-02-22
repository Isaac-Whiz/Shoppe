package com.shoppeapp.shoppe.user;

import com.shoppeapp.shoppe.purchase.Purchase;
import com.shoppeapp.shoppe.report.Report;
import com.shoppeapp.shoppe.sale.Sale;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "\"user\"")
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "user_id_sequence", sequenceName = "user_id_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    @Column(name = "user_id", updatable = false)
    @Getter
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


    @Getter
    @Setter
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Getter
    @Setter
    @Column(name = "phone_number", nullable = false, columnDefinition = "TEXT")
    private String phoneNumber;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String password, LocalDateTime date_registered) {
        this.name = name;
        this.password = password;
        this.date_registered = date_registered;
    }

    public User(String name, String password, LocalDateTime date_registered, String email, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.date_registered = date_registered;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
