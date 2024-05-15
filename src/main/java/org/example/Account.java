package org.example;

import java.util.Scanner;

public class Account {
    AccountsDAOImplementation accountsDAO = new AccountsDAOImplementation();
    Scanner input = new Scanner(System.in);


    //declare attributes
    private static int customerNumber;
    private static int pinNumber;
    private static double checkingBalance = 0.0;
    private static double savingBalance = 0.0;

    public Account() {
    }

    public Account(int customerNumber, int pinNumber, double checkingBalance, double savingBalance) {
        this.customerNumber = customerNumber;
        this.pinNumber = pinNumber;
        this.checkingBalance = checkingBalance;
        this.savingBalance = savingBalance;
    }

    public int setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
        return this.customerNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public int setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
        return this.pinNumber;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public double getCheckingBalance() {
        return checkingBalance;
    }

    public double setCheckingBalance(double checkingBalance) {
        this.checkingBalance = checkingBalance;
        return this.checkingBalance;
    }

    public double getSavingBalance() {
        return savingBalance;
    }

    public double setSavingBalance(double savingBalance) {
        this.savingBalance = savingBalance;
        return this.savingBalance;
    }

    //get withdraw amount for checking
    public double checkingWithdraw(double amount){

        if((accountsDAO.accountData.getFirst().getCheckingBalance() - amount) <= 0){
            System.out.println("Account balance cannot be equal to or below zero!");
        }else{
            amount = accountsDAO.accountData.getFirst().getCheckingBalance() - amount;
            System.out.println("Withdrawal successful!");
        }
        return amount;
    }
    //get withdraw amount for saving
    public double savingWithdraw(double amount){

        if((accountsDAO.accountData.getFirst().getSavingBalance() - amount) <= 0){
            System.out.println("Account balance cannot be equal to or below zero!");
        }else{
            amount = accountsDAO.accountData.getFirst().getSavingBalance() - amount;
            System.out.println("Withdrawal successful!");

        }
        return amount;
    }
    //get deposit amount for checking
    public double checkingDeposit(double amount){
        if((accountsDAO.accountData.getFirst().getCheckingBalance() + amount) <= 0){
            System.out.println("Account balance cannot be equal to or below zero!");
        }else{
            amount = accountsDAO.accountData.getFirst().getCheckingBalance() +  amount;
            System.out.println("Deposit successful!");
        }
        return amount;
    }


    //get deposit amount for saving
    public double savingDeposit(double amount){

        if((accountsDAO.accountData.getFirst().getSavingBalance() + amount) <= 0){
            System.out.println("Account balance cannot be equal to or below zero!");
        }else{
            amount = accountsDAO.accountData.getFirst().getSavingBalance() + amount;
            System.out.println("Deposit successful!");
        }

        return amount;
    }

    @Override
    public String toString() {
        return "customerNumber=" + customerNumber +
                ", pinNumber=" + pinNumber +
                ", checkingBalance=" + checkingBalance +
                ", savingBalance=" + savingBalance;
    }
}
