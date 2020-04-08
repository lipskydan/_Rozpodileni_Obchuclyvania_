package com.company;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/MAP?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Steps.123";

    public static void main(String[] args) throws SQLException {

        Map map = new Map("MAP","localhost",3306,"root", "Steps.123");
        map.showCountries();
        map.showCities();
    }

}
