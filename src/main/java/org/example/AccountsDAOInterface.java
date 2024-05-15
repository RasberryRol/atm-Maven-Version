package org.example;

public interface AccountsDAOInterface {
    Account account = new Account();
    AccountService accountService = new AccountService();
    public void createAccount();
    public void closeAccount();
    public void checkingWithdrawDAO(double amount);

    void savingWithdrawDAO(double amount);
    public void checkingDepositDAO(double amount);

    void savingDepositDAO(double amount);

    public int checkIfAccountExist();

}
