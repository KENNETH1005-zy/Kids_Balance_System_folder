package com.kidbalance.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface KidRepository extends JpaRepository<Kid, String> {
    // This magic method tells Spring: "Write SQL to find a user where name matches X"
    Optional<Kid> findByName(String name);
}