package com.kidbalance.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    // This looks for the 'kidId' field inside the 'Kid' object
    List<Transaction> findByKidKidId(String kidId);
}