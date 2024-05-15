package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void checkingWithdrawTest(){
        Account account = new Account();
        AccountsDAOImplementation accountsDAO = new AccountsDAOImplementation();
        accountsDAO.accountData.add(new Account(111, 111, 0, 0));

        account.checkingWithdraw(500);
        assertEquals(accountsDAO.accountData.getFirst().getCheckingBalance(), 0);

        accountsDAO.accountData.getFirst().setCheckingBalance(account.checkingDeposit(500));
        double amount2 = account.checkingWithdraw(200);
        accountsDAO.accountData.getFirst().setCheckingBalance(amount2);
        assertEquals(accountsDAO.accountData.getFirst().getCheckingBalance(), amount2);
    }


    @Test
    public void savingWithdrawTest(){
        Account account = new Account();
        AccountsDAOImplementation accountsDAO = new AccountsDAOImplementation();
        accountsDAO.accountData.add(new Account(111, 111, 0, 0));

        account.savingWithdraw(500);
        assertEquals(accountsDAO.accountData.getFirst().getSavingBalance(), 0);

        accountsDAO.accountData.getFirst().setSavingBalance(account.savingDeposit(500));
        double amount2 = account.savingWithdraw(200);
        accountsDAO.accountData.getFirst().setSavingBalance(amount2);
        assertEquals(accountsDAO.accountData.getFirst().getSavingBalance(), amount2);
    }


    @Test
    void checkingDepositTest() {
        Account account = new Account();
        AccountsDAOImplementation accountsDAO = new AccountsDAOImplementation();
        accountsDAO.accountData.add(new Account(111, 111, 0, 0));

        double amount1 = account.checkingDeposit(500);
        accountsDAO.accountData.getFirst().setCheckingBalance(amount1);
        assertEquals(accountsDAO.accountData.getFirst().getCheckingBalance(), amount1);

        double amount2 = account.checkingDeposit(-1000);
        assertEquals(accountsDAO.accountData.getFirst().getCheckingBalance(), amount1);
    }


    @Test
    public void savingDepositTest(){
        Account account = new Account();
        AccountsDAOImplementation accountsDAO = new AccountsDAOImplementation();
        accountsDAO.accountData.add(new Account(111, 111, 0, 0));

        double amount1 = account.savingDeposit(500);
        accountsDAO.accountData.getFirst().setSavingBalance(amount1);
        assertEquals(accountsDAO.accountData.getFirst().getSavingBalance(), amount1);

        double amount2 = account.savingDeposit(-1000);
        assertEquals(accountsDAO.accountData.getFirst().getSavingBalance(), amount1);
    }
}