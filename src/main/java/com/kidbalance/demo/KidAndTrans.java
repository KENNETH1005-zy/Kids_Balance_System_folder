package com.kidbalance.demo;

import lombok.Data;
import java.util.List;

@Data
public class KidAndTrans {
    private String name;
    private int newBalance;
    private String KID;
    private List<Transaction> transactions;
}