package com.kidbalance.demo;

import jakarta.persistence.*;
import lombok.*; // Keeping this for future use, but overriding it now
import java.util.List;

@Entity
@Table(name = "kids")
@Getter @Setter @NoArgsConstructor
public class Kid {
    @Id
    @Column(name = "kid_id")
    private String kidId;

    private String name;
    private int balance;

    @OneToMany(mappedBy = "kid", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    // --- MANUAL GETTERS & SETTERS (The Fix) ---

    public String getKidId() {
        return kidId;
    }

    public void setKidId(String kidId) {
        this.kidId = kidId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}