package org.example;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class AccountService {
    final Logger logger = LogManager.getLogger(Main.class.getName());
    AccountsDAOImplementation accountsDAOImplementation = new AccountsDAOImplementation();
    Scanner input = new Scanner(System.in);
    Account account = new Account();
    int custNum = 1;
    int exit = 4;

    /*********
     *Validate pin-numbers -> DONE
     *limit amount of tries ->
     *check for where connections should be closed
     *improve queries with parameters ->
     * ******/

    public void menu() {
        int tries = 0;

        do {
            try {
                if (tries == 3) {
                    System.out.println("This is your last attempt. Make it count.");
                }

                logger.info("Into the main menu.");
                System.out.println("\nWhat would you like to do today?");
                System.out.print("Select:\n1 - to create an account\n2 - to close an account" +
                        "\n3 - access an account\n4 - to Exit\nEnter choice here: ");
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        logger.info("1 is pressed to create an account.");
                        System.out.print("Enter an account number or press 0 to exit: ");
                        custNum = account.setCustomerNumber(input.nextInt());
                        if (custNum == 0){
                            logger.debug("Exiting application for 0 input.");
                            System.out.println("Good bye! See you soon!");
                            System.exit(0);
                        }else{
                            System.out.print("Enter a desired pin number: ");
                            account.setPinNumber(input.nextInt());
                            accountsDAOImplementation.createAccount();
                        }
                        break;

                    //PIN NEEDS TO BE VALIDATED AFTER GETTING CUSTOMER #
                    case 2:
                        logger.debug("2 is pressed to close an account");
                        getPINAndAccountNumber();
                        accountsDAOImplementation.closeAccount();
                        accountsDAOImplementation.accountData.clear();
                        break;

                    case 3:
                        logger.info("3 is pressed to access an account.");
                        getPINAndAccountNumber();
                        int check = accountsDAOImplementation.checkIfAccountExist();
                        if (check == 1) {
                            logger.info("Accessing Account!");
                            accessAccount();
                        } else {
                            logger.info("Account number or PIN is wrong!");
                            System.out.println("Incorrect account number or PIN.");
                            tries++;
                            logger.debug("Try " + tries +" for incorrect account number or PIN");
                            if (tries < 4) {
                                System.out.println("Try again. You have " + (4 - tries) + " attempt(s) left.");

                            } else {
                                logger.info("Close account for too many attempts.");
                                System.out.println("Too many attempts. Good bye!");
                                System.exit(0);
                            }
                        }
                        break;

                    case 4:
                        logger.info("4 is pressed to exit the application.");
                        System.out.println("Good bye! See you soon.");
                        System.exit(0);
                        tries = 4;
                        break;

                    default:
                        tries++;
                        if (tries < 4) {
                            logger.info("Wrong character!");
                            System.out.println("Invalid input. Try again. You have " + (4 - tries) + " attempt(s) left.");

                        } else {
                            logger.info("Closing account after too many attempts.");
                            System.out.println("Too many attempts. Good bye!");
                            System.exit(0);
                        }
                        break;
                }
            } catch (Exception e) {
                tries++;
                if (tries < 4) {
                    System.out.println("Invalid character. DIGIT ONLY. You have " + (4 - tries) + " attempt(s) left.");

                } else {
                    System.out.println("Too many attempts. Good bye!");
                    System.exit(0);
                }
                input.nextLine();
            }

        } while (tries < 4);
    }

    public void accessAccount() {
        int tries = 0;

        do {
            try {
                if (tries == 3) {
                    System.out.println("This is your last attempt. Make it count.");
                }
                logger.info("Into the user's account.");
                System.out.println("Select:\n1 - to see account information" +
                        "\n2 - to see checking balance" +
                        "\n3 - to see saving balance" +
                        "\n4 - to withdraw from checking" +
                        "\n5 - to withdraw from saving" +
                        "\n6 - for checking deposit" +
                        "\n7 - for saving deposit" +
                        "\n8 - to Exit");
                System.out.print("Enter choice here: ");
                int choice = input.nextInt();


                switch (choice) {
                    case 1:
                        logger.info("Displaying user info as: " + accountsDAOImplementation.accountData);
                        System.out.println(accountsDAOImplementation.accountData.toString());
                        accessAccount();
                        break;

                    case 2:
                        logger.info("Displaying checking balance of: " + accountsDAOImplementation.
                                accountData.get(0).getCheckingBalance());
                        System.out.println("Your checking balance is: " + accountsDAOImplementation.
                                accountData.get(0).getCheckingBalance());
                        accessAccount();
                        break;

                    case 3:
                        logger.info("Displaying saving balance of: " + accountsDAOImplementation.
                                accountData.get(0).getSavingBalance());
                        System.out.println("Your saving balance is: " + accountsDAOImplementation.
                                accountData.get(0).getSavingBalance());
                        accessAccount();
                        break;

                    case 4:
                        System.out.print("\nEnter checking withdraw amount: ");
                        double checkingWithdrawAmount = Math.abs(input.nextDouble());
                        logger.info("Making checking withdraw of: " + checkingWithdrawAmount);
                        double newCheckingBalance1 = account.checkingWithdraw(checkingWithdrawAmount);
                        accountsDAOImplementation.checkingWithdrawDAO(newCheckingBalance1);
                        break;

                    case 5:
                        System.out.print("\nEnter saving withdraw amount: ");
                        double savingWithdrawAmount = Math.abs(input.nextDouble());
                        logger.info("Making saving withdraw of: " + savingWithdrawAmount);
                        double newSavingBalance1 = account.savingWithdraw(savingWithdrawAmount);
                        accountsDAOImplementation.savingWithdrawDAO(newSavingBalance1);
                        break;

                    case 6:
                        System.out.print("\nEnter checking deposit amount: ");
                        double checkingDepositAmount = Math.abs(input.nextDouble());
                        logger.info("Making checking deposit of: " + checkingDepositAmount);
                        double newCheckingBalance2 = account.checkingDeposit(checkingDepositAmount);
                        accountsDAOImplementation.checkingDepositDAO(newCheckingBalance2);
                        break;

                    case 7:
                        System.out.print("\nEnter saving deposit amount: ");
                        double savingDepositAmount = Math.abs(input.nextDouble());
                        logger.info("Making saving deposit of: " + savingDepositAmount);
                        double newSavingBalance2 = account.savingDeposit(savingDepositAmount);
                        accountsDAOImplementation.savingDepositDAO(newSavingBalance2);
                        break;

                    case 8:
                        logger.info("Exiting application for input 8 exit command.");
                        accountsDAOImplementation.accountData.clear();
                        menu();
                        exit = 5;
                        break;

                    default:
                        tries++;
                        if (tries < 4) {
                            System.out.println("Invalid input. Try again. You have " + (4 - tries) + " attempt(s) left.");

                        } else {
                            System.out.println("You tried.");
                            System.exit(0);
                        }
                        break;
                }
            } catch (Exception e) {
                tries++;
                if (tries < 4) {
                    System.out.println("Invalid character. Digit only. You have " + (4 - tries) + " attempt(s) left.");

                } else {
                    System.out.println("Too many attempts. Account's closing.");
                    System.exit(0);
                }
                input.nextLine();
            }

        } while (tries < 4 & exit == 4);
    }


    public void getPINAndAccountNumber() {
        logger.info("Call of getPINAndAccountNumber() to get user PIN and account number.");
        System.out.print("\nEnter account number: ");
        account.setCustomerNumber(input.nextInt());

        System.out.print("Enter account PIN: ");
        account.setPinNumber(input.nextInt());
    }
}