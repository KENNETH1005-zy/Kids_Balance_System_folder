package com.kidbalance.demo;

import lombok.Data;
import java.util.List;

@Data
public class KidAndTrans {
    private String name;
    private int newBalance;
    private String KID;
    private List<Transaction> transactions;

    // --- MANUAL GETTERS & SETTERS ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getNewBalance() { return newBalance; }
    public void setNewBalance(int newBalance) { this.newBalance = newBalance; }

    public String getKID() { return KID; }
    public void setKID(String KID) { this.KID = KID; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}