package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {
    static Connection connection;
    public static Connection mySQLConnection() {


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm_accounts", "root", "D!lemma628");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
