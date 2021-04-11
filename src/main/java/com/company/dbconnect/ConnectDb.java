package com.company.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class ConnectDb {

    public Connection connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/fuad_test";
        String username = "root";
        String password = "haciyev96";
        Connection c = DriverManager.getConnection(url, username, password);

        return c;
    }
}
