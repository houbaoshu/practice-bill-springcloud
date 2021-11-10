package com.example.personalAccount.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Entity
public @Data class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Enumerated
    private BillType type;

    private double amount;

    private String info;

}
