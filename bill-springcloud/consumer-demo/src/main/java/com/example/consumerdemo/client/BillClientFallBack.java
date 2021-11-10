package com.example.consumerdemo.client;

import com.example.consumerdemo.model.Bill;
import org.springframework.stereotype.Component;

@Component
public class BillClientFallBack implements BillClient {

    @Override
    public Bill queryById(Long id) {
        Bill bill = new Bill();
        bill.setId(id);
        bill.setTitle("账单异常");
        return bill;
    }
}
