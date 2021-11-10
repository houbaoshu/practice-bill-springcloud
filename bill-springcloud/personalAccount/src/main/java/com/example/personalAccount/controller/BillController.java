package com.example.personalAccount.controller;

import com.example.personalAccount.model.Bill;
import com.example.personalAccount.model.QueryBill;
import com.example.personalAccount.repository.BillRepository;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
@RefreshScope
public class BillController {
    //declare reference to bill repository
    private BillRepository billRepository;

    public BillController(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    //find all bills order by time
    @GetMapping
    public Iterable<Bill> findAll() {
        return this.billRepository.findAll();
    }

    @GetMapping("/{id}")
    public Bill findById(@PathVariable Long id) {
        Optional<Bill> optionalBill = this.billRepository.findById(id);
        if (optionalBill.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Bill bill = optionalBill.get();
        return bill;
    }
    //find bills by condition
    @GetMapping("/search")
    public Page<Bill> findByType(@RequestBody QueryBill bill,
                                 @RequestBody Pageable pageable) {
        //get bill list by condition from database
        List<Bill> billList = getBillList(bill);

        // initialization Page<Bill>
        return new PageImpl<Bill>(billList, pageable, billList.size());
    }

    private List<Bill> getBillList(QueryBill bill) {
        if (ObjectUtils.isEmpty(bill)) {
            return (List<Bill>) this.billRepository.findAll();
        }
        if (ObjectUtils.isEmpty(bill.getType())) {
            return this.billRepository.findByType(bill.getType());
        }
        if (ObjectUtils.isEmpty(bill.getStartDate())
                && ObjectUtils.isEmpty(bill.getEndDate())) {
            return this.billRepository.findByDateBetween(bill.getStartDate(), bill.getEndDate());
        }
        if (ObjectUtils.isEmpty(bill.getStartDate())) {
            return this.billRepository.findByDateGreaterThanEqual(bill.getStartDate());
        }
        if (ObjectUtils.isEmpty(bill.getEndDate())) {
            return this.billRepository.findByDateLessThanEqual(bill.getEndDate());
        }
        return new ArrayList<>();
    }


    //add bill
    @PostMapping
    public Bill addBill(@RequestBody Bill bill) {
        //validate bill
        validatedBill(bill);

        //find bill if bill exist，forget it

        return this.billRepository.save(bill);
    }

    // update bill
    @PutMapping("/{id}")
    public Bill updateBill(@PathVariable Long id, @RequestBody Bill updatedBill) {
        // find bill if bill exist
        Optional<Bill> optionalBill = this.billRepository.findById(id);
        if (optionalBill.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // if bill exist get the bill
        Bill existingBill = optionalBill.get();

        //copy information from existing bill, need it ?
        copyBillFrom(updatedBill, existingBill);

        //update bill in database
        this.billRepository.save(existingBill);
        return existingBill;
    }

    // update bill (new)
    @PutMapping()
    public Bill updateBill(@RequestBody Bill updatedBill) {

        // the bill must have id
        if (ObjectUtils.isEmpty(updatedBill.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // find bill if bill exist
        Optional<Bill> optionalBill = this.billRepository.findById(updatedBill.getId());
        if (optionalBill.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // if bill exist get the bill
        Bill existingBill = optionalBill.get();

        //copy information from existing bill, need it ?
        copyBillFrom(updatedBill, existingBill);

        //update bill in database
        this.billRepository.save(existingBill);
        return existingBill;
    }

    // delete bill
    @DeleteMapping("/{id}")
    public Bill deleteBill(@PathVariable Long id) {
        // find bill by id
        Optional<Bill> optionalBill = this.billRepository.findById(id);

        if (optionalBill.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Bill bill = optionalBill.get();

        this.billRepository.delete(bill);
        return bill;
    }

    //前端和后端的数据可能不一样，或许需要这个
    private void copyBillFrom(Bill updatedBill, Bill existingBill) {
        if (!ObjectUtils.isEmpty(updatedBill.getTitle())) {
            existingBill.setTitle(updatedBill.getTitle());
        }
        if (!ObjectUtils.isEmpty(updatedBill.getType())) {
            existingBill.setType(updatedBill.getType());
        }
        if (!ObjectUtils.isEmpty(updatedBill.getAmount())) {
            existingBill.setAmount(updatedBill.getAmount());
        }
        if (!ObjectUtils.isEmpty(updatedBill.getInfo())) {
            existingBill.setInfo(updatedBill.getInfo());
        }
        if (!ObjectUtils.isEmpty(updatedBill.getDate())) {
            existingBill.setDate(updatedBill.getDate());
        }
    }


    private void validatedBill(Bill bill) {
        // the bill is not null
        if (ObjectUtils.isEmpty(bill)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // a bill must have properties for title, type, amount
        if (ObjectUtils.isEmpty(bill.getTitle())
                || ObjectUtils.isEmpty(bill.getType())
                || ObjectUtils.isEmpty(bill.getAmount())
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }


}
