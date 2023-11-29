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

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<Purchase> purchases = new ArrayList<>();

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<Sale> sales = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Report> reports = new ArrayList<>();

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

    public User(String name) {
        this.name = name;
    }
}
