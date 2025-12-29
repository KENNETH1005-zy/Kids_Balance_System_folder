package com.kidbalance.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class KidConsoleRunner implements CommandLineRunner {

    private final KidService kidService;

    public KidConsoleRunner(KidService kidService) {
        this.kidService = kidService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("************************************");
        System.out.println("* KID LEDGER SYSTEM ONLINE     *");
        System.out.println("************************************");

        while (true) {
            // --- 1. THE DASHBOARD (Shows Name & Balance every time) ---
            System.out.println("\n------------------------------------");
            System.out.println(" CURRENT STATUS:");
            System.out.println("------------------------------------");

            // Fetch latest data from DB
            var allKids = kidService.getAllKids();

            if (allKids.isEmpty()) {
                System.out.println(" (No users found)");
            } else {
                // Print a clean row for every kid
                for (Kid k : allKids) {
                    System.out.printf(" %-10s | Balance: %d%n", k.getName(), k.getBalance());
                }
            }
            System.out.println("------------------------------------");

            // --- 2. THE MENU ---
            System.out.println("Options: [1] Update Balance  [2] Add New Kid  [3] Exit");
            System.out.print("Command: ");
            String choice = scanner.nextLine();

            if (choice.equals("3")) break;

            // --- 3. UPDATE BY NAME ---
            if (choice.equals("1")) {
                try {
                    System.out.print("Enter Name: "); // Asking for Name now
                    String nameInput = scanner.nextLine();

                    System.out.print("Enter Amount: ");
                    int op = Integer.parseInt(scanner.nextLine());

                    // Call the NEW service method
                    var result = kidService.updateBalanceByName(nameInput, op, 3);

                    System.out.println("\n>>> SUCCESS: " + result.get(0).getName() +
                            " is now at " + result.get(0).getNewBalance());

                } catch (Exception e) {
                    System.out.println("\n*** ERROR: " + e.getMessage() + " ***");
                }
            }

            // --- 4. ADD NEW KID ---
            if (choice.equals("2")) {
                try {
                    System.out.print("Enter New ID (e.g. kid_99): ");
                    String newId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String newName = scanner.nextLine();

                    kidService.createKid(newId, newName);
                    System.out.println("\n>>> Created user: " + newName);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        System.out.println("System Shutdown.");
    }
}