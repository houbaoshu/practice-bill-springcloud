package com.example.personalAccount.model;

import lombok.Data;

import java.util.Date;

public @Data class QueryBill extends Bill {
    private Date startDate;
    private Date endDate;
}
