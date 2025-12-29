package com.kidbalance.demo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class KidService {
    private final KidRepository kidRepository;
    private final TransactionRepository transactionRepository;

    // Manual FIFO Cache Implementation
    private final Map<String, Integer> cache = new HashMap<>();
    private final Queue<String> order = new LinkedList<>();

    public KidService(KidRepository kidRepository, TransactionRepository transactionRepository) {
        this.kidRepository = kidRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public List<KidAndTrans> updateBalance(String kidId, int operation, int threshold) {
        // 1. Database Logic: Fetch and Update Balance
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new RuntimeException("Kid not found: " + kidId));

        int newBalance = kid.getBalance() + operation;
        kid.setBalance(newBalance);
        kidRepository.save(kid); // Updates PostgreSQL 'kids' table

        // 2. Database Logic: Create new Transaction record
        Transaction t = new Transaction();
        t.setTid("t_" + System.currentTimeMillis());
        t.setOperation(operation);
        t.setKid(kid);
        transactionRepository.save(t); // Inserts into 'transactions' table

        // 3. FIFO Cache Logic: Manage memory based on threshold
        boolean isNewUser = !cache.containsKey(kidId); // Check if they are new BEFORE updating

        // Eviction Rule: Only run if it's a NEW user and we are full
        if (isNewUser && cache.size() >= threshold) {
            String oldest = order.poll();
            cache.remove(oldest);
            System.out.println("\n[CACHE LOG] Threshold reached! Evicting: " + oldest);
        }

        // Always update the balance in the Map (Map handles duplicates automatically)
        cache.put(kidId, newBalance);

        // Only add to the Queue if they are actually NEW
        if (isNewUser) {
            order.add(kidId);
        }

        // Log the current state
        System.out.println("[CACHE LOG] Current Cache Queue: " + order);

        // 4. Return the Payload as List<KidAndTrans>
        KidAndTrans response = new KidAndTrans();
        response.setKID(kid.getKidId());
        response.setName(kid.getName());
        response.setNewBalance(newBalance);
        response.setTransactions(Collections.singletonList(t));

        return Collections.singletonList(response);
    }
    // 1. NEW: Get everyone for the dashboard display
    public List<Kid> getAllKids() {
        return kidRepository.findAll();
    }

    // 2. NEW: Find the ID using the Name, then run the update
    @Transactional
    public List<KidAndTrans> updateBalanceByName(String name, int operation, int threshold) {
        // Find the kid by name
        Kid kid = kidRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("User '" + name + "' not found!"));

        // Reuse your existing logic by passing the ID we just found
        return updateBalance(kid.getKidId(), operation, threshold);
    }

    public void createKid(String kidId, String name) {
        // 1. Check if ID already exists to prevent duplicates
        if (kidRepository.existsById(kidId)) {
            throw new RuntimeException("Error: Kid ID '" + kidId + "' already exists.");
        }

        // 2. Create the new Entity
        Kid newKid = new Kid();
        newKid.setKidId(kidId);   // Using your manual Setter
        newKid.setName(name);     // Using your manual Setter
        newKid.setBalance(0);     // Start with 0

        // 3. Save to Database
        kidRepository.save(newKid);
    }
}