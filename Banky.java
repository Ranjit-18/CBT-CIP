package com.internship;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public Account(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }

    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Successfully withdrew $" + amount);
            return true;
        } else {
            System.out.println("Insufficient balance or invalid amount.");
            return false;
        }
    }

    public boolean transfer(Account toAccount, double amount) {
        if (withdraw(amount)) {
            toAccount.deposit(amount);
            System.out.println("Successfully transferred $" + amount + " to " + toAccount.getAccountHolder());
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return accountNumber + "," + accountHolder + "," + balance;
    }
}

class Bank {
    private Map<String, Account> accounts = new HashMap<>();
    private static final String DATA_FILE = "accounts.txt";

    public Bank() {
        loadAccounts(); 
    }

    public void createAccount(String accountNumber, String accountHolder, double initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account number already exists.");
        } else {
            Account newAccount = new Account(accountNumber, accountHolder, initialBalance);
            accounts.put(accountNumber, newAccount);
            System.out.println("Account created successfully.");
            saveAccounts(); 
        }
    }

    
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    
    public void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Account account : accounts.values()) {
                writer.write(account.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    
    public void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String accountNumber = data[0];
                String accountHolder = data[1];
                double balance = Double.parseDouble(data[2]);
                accounts.put(accountNumber, new Account(accountNumber, accountHolder, balance));
            }
        } catch (IOException e) {
            System.out.println("No saved accounts found.");
        }
    }
}


public class Banky {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        
        
        while (true) {
            System.out.println("\n--- Welcome to Banky ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Balance");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Account Number: ");
                    String accNumber = scanner.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String accHolder = scanner.nextLine();
                    System.out.print("Enter Initial Balance: ");
                    double balance = scanner.nextDouble();
                    bank.createAccount(accNumber, accHolder, balance);
                }
                case 2 -> {
                    System.out.print("Enter Account Number: ");
                    String accNumber = scanner.nextLine();
                    Account account = bank.getAccount(accNumber);
                    if (account != null) {
                        System.out.print("Enter Deposit Amount: ");
                        double amount = scanner.nextDouble();
                        account.deposit(amount);
                        bank.saveAccounts();
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Account Number: ");
                    String accNumber = scanner.nextLine();
                    Account account = bank.getAccount(accNumber);
                    if (account != null) {
                        System.out.print("Enter Withdrawal Amount: ");
                        double amount = scanner.nextDouble();
                        if (account.withdraw(amount)) {
                            bank.saveAccounts();
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter Your Account Number: ");
                    String fromAccNumber = scanner.nextLine();
                    System.out.print("Enter Recipient Account Number: ");
                    String toAccNumber = scanner.nextLine();
                    Account fromAccount = bank.getAccount(fromAccNumber);
                    Account toAccount = bank.getAccount(toAccNumber);
                    if (fromAccount != null && toAccount != null) {
                        System.out.print("Enter Transfer Amount: ");
                        double amount = scanner.nextDouble();
                        if (fromAccount.transfer(toAccount, amount)) {
                            bank.saveAccounts();
                        }
                    } else {
                        System.out.println("Invalid account details.");
                    }
                }
                case 5 -> {
                    System.out.print("Enter Account Number: ");
                    String accNumber = scanner.nextLine();
                    Account account = bank.getAccount(accNumber);
                    if (account != null) {
                        System.out.println("Account Balance: $" + account.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 6 -> {
                    System.out.println("Thank you for using Banky!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
