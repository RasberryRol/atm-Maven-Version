package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    protected static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args){
        logger.info("Application Started!");
        AccountService accountService = new AccountService();
        accountService.menu();

    }
}