package com.example.personalAccount.repository;

import com.example.personalAccount.model.Bill;
import com.example.personalAccount.model.BillType;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {

    List<Bill> findByType(BillType type);

    List<Bill> findByDateBetween(Date startDate, Date endDate);

    List<Bill> findByDateGreaterThanEqual(Date startDate);

    List<Bill> findByDateLessThanEqual(Date endDate);

}
