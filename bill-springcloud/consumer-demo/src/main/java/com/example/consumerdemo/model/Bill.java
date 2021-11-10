package com.example.consumerdemo.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Enumerated;
import java.util.Date;

public @Data class Bill {
    private Long id;

    private String title;

    private Date date;

    @Enumerated
    private BillType type;

    private double amount;

    private String info;

}
