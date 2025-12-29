package com.kidbalance.demo;

import jakarta.persistence.*;
import lombok.*; // We keep this, but we write manual getters just in case

@Entity
@Table(name = "transactions")
@Getter @Setter @NoArgsConstructor
public class Transaction {
    @Id
    private String tid;

    private int operation;

    @ManyToOne
    @JoinColumn(name = "kid_id")
    private Kid kid;

    // --- MANUAL GETTERS (The Fix) ---

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public Kid getKid() {
        return kid;
    }

    public void setKid(Kid kid) {
        this.kid = kid;
    }
}