package com.company;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/MAP?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Steps.123";

    public static void main(String[] args) throws SQLException {

        Map map = new Map("MAP","localhost",3306,"root", "Steps.123");
        map.showCountries();
        map.showCities(1);
        map.showCities(2);
        map.showCities(3);

        System.out.println("-------------------------------------------");

        map.findCity(3);
        map.findCity(1);
        map.findCity(2);

//        map.changeCountryInfo(1,"Russia");

//        map.deleteCity(2);
//        map.addCity(2,1,"SOCHI",343334,0);
//
//        map.changeCityInfo(2,1,"SOCHI",343334,0);
//
//        map.showCountries();
//        map.showCities(1);
//        map.showCities(2);
//        map.showCities(3);


//        map.deleteCity(1);
//        map.deleteCity(2);
//        map.deleteCountry(1);
//
//        map.addCountry(1,"Russia");
//        map.addCity(1,1,"MOSCOW", 11612943, 1);
//        map.addCity(2,1,"SOCHI",343334,0);

//        map.addCountry(2,"USA");
//        map.addCity(3,2,"NEW YORK",8363710,0);

//        map.deleteCity(3);
//
//        map.showCountries();
//        map.showCities(1);
//        map.showCities(2);
//        map.showCities(3);




    }

}
