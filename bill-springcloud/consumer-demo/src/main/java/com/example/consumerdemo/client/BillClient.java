package com.example.consumerdemo.client;

import com.example.consumerdemo.model.Bill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "bill-service", fallback = BillClientFallBack.class)
public interface BillClient {
    @GetMapping("/bills/{id}")
    Bill queryById(@PathVariable Long id);
}
