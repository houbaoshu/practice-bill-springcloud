package com.example.consumerdemo.controller;

import com.example.consumerdemo.client.BillClient;
import com.example.consumerdemo.model.Bill;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cf")
public class ConsumerFeignController {

    private BillClient billClient;

    public ConsumerFeignController(BillClient billClient) {
        this.billClient = billClient;
    }

    @GetMapping("/{id}")
    public Bill queryById(@PathVariable Long id) {
        return this.billClient.queryById(id);
    }
}
