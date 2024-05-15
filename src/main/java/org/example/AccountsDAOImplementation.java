package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountsDAOImplementation implements AccountsDAOInterface {
    final Logger logger = LogManager.getLogger(Main.class.getName());

    Scanner input = new Scanner(System.in);
    static ArrayList<Account> accountData = new ArrayList<Account>();
    Connection connection;

    @Override
    public void createAccount() {

            String query = "insert into accounts (customerNumber, pinNumber, checkingBalance, savingBalance) " +
                           "values(?, ?, ?, ?)";
            try{
                connection = JdbcConnection.mySQLConnection();
                PreparedStatement prepStatement = connection.prepareStatement(query);
                prepStatement.setInt(1, account.getCustomerNumber());
                prepStatement.setInt(2, account.getPinNumber());
                prepStatement.setDouble(3, 0.0);
                prepStatement.setDouble(4, 0.0);
                int count = prepStatement.executeUpdate();
                if (count > 0){
                    logger.info("Account added successfully to database!");
                    System.out.println("Account created successfully!\n");
                    accountService.menu();
                }
                connection.close();
            }catch (SQLIntegrityConstraintViolationException e) {
                logger.info("Account already exist in the database.");
                System.out.println("Account already exist.\n");
                accountService.menu();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    public void closeAccount(){
        connection = JdbcConnection.mySQLConnection();
        int accExistence = checkIfAccountExist();

        /*****improve with parametrized query***/
        String query = "delete from accounts where customerNumber=?";

        if(accExistence == 1){
            //account exists
            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, account.getCustomerNumber());
                boolean count = pstmt.execute();

                if(count == false){
                    logger.info("Account deleted successfully from database!");
                    System.out.println("Account deleted successfully!");
                }else {
                    logger.debug("Account did not get deleted. Something went wrong.");
                    System.out.println("Something went wrong!");
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            logger.info("Incorrect account number or PIN!");
            System.out.println("Incorrect account number or PIN!");
            accountService.menu();
        }
    }

    @Override
    public void checkingWithdrawDAO(double amount) {
        connection = JdbcConnection.mySQLConnection();

        /*****improve with parametrized query***/
        String query = "update accounts set checkingBalance=? where customerNumber=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountData.get(0).getCustomerNumber());
            int count = stmt.executeUpdate();
            if (count == 1){
                logger.info("Checking withdraw from database successful.");
                accountData.getFirst().setCheckingBalance(amount);
                accountService.accessAccount();
            }else{
                logger.info("Something went wrong. Checking withdraw from database unsuccessful.");
                System.out.println("Something went wrong.");
                accountService.accessAccount();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void savingWithdrawDAO(double amount) {
        connection = JdbcConnection.mySQLConnection();

        /*****improve with parametrized query***/
        String query = "update accounts set savingBalance=? where customerNumber=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountData.get(0).getCustomerNumber());
            int count = stmt.executeUpdate();
            if (count == 1){
                logger.info("Saving withdraw from database successful.");
                accountData.getFirst().setSavingBalance(amount);
                accountService.accessAccount();
            }else{
                logger.info("Something went wrong. Saving withdraw from database unsuccessful.");
                System.out.println("Something went wrong.");
                accountService.accessAccount();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void checkingDepositDAO(double amount) {
        connection = JdbcConnection.mySQLConnection();

        /*****improve with parametrized query***/
        String query = "update accounts set checkingBalance=? where customerNumber=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountData.getFirst().getCustomerNumber());
            int count = stmt.executeUpdate();

            if (count == 1){
                logger.info("Checking deposit to database successful.");
                accountData.get(0).setCheckingBalance(amount);
                accountService.accessAccount();
            }else{
                logger.info("Something went wrong. Checking deposit to database unsuccessful.");
                System.out.println("Something went wrong.");
                accountService.accessAccount();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void savingDepositDAO(double amount) {
        connection = JdbcConnection.mySQLConnection();

        String query = "update accounts set savingBalance=? where customerNumber=? ";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountData.getFirst().getCustomerNumber());
            int count = stmt.executeUpdate();

            if (count == 1){
                logger.info("Saving deposit to database successful.");
                accountData.get(0).setSavingBalance(amount);
                accountService.accessAccount();
            }else{
                logger.info("Something went wrong. Saving deposit to database unsuccessful.");
                System.out.println("Something went wrong.");
                accountService.accessAccount();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int checkIfAccountExist() {

        connection = JdbcConnection.mySQLConnection();

        String query = "select * from accounts where customerNumber = "+ account.getCustomerNumber()+
                " and pinNumber = " + account.getPinNumber();
        accountData.clear();

        try{
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                account.setCustomerNumber(resultSet.getInt(1));
                account.setPinNumber(resultSet.getInt(2));
                account.setCheckingBalance(resultSet.getDouble(3));
                account.setSavingBalance(resultSet.getDouble(4));

                accountData.add(new Account(account.getCustomerNumber(), account.getPinNumber(),
                        account.getCheckingBalance(), account.getSavingBalance()));

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!accountData.isEmpty()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}


