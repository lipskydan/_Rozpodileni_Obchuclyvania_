package com.company;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Map map = new Map("MAP","localhost",3306,"root", "Steps.123");

        map.deleteCountry(2);
        map.deleteCountry(3);
        map.showCountries();

//        System.out.println("-------------------------------------------");
//
//        map.showCountries();
//        map.showCities(1);
//        map.showCities(2);
//        map.showCities(3);
//
//        System.out.println("-------------------------------------------");
//
//        map.addCountry(1, "RASHA");
//        map.addCountry(2, "USA");
//        map.addCountry(3,"Canada");
//
//        map.showCountries();
//
//        System.out.println("-------------------------------------------");
//
//        map.addCity(1,1,"MOSCOW",11612943,1);
//        map.addCity(2,1,"SOCHI",343334,0);
//        map.addCity(3,2,"NEW YORK",8363710,0);
//        map.addCity(4,2,"LOS ANGELES",56451241,0);
//        map.addCity(5,3,"TORONTO",94234710,1);
//
//        map.showCities(1);
//        map.showCities(2);
//        map.showCities(3);
//        map.showCities(4);
//        map.showCities(5);
//
//        System.out.println("-------------------------------------------");
//
//        map.changeCountryInfo(1,"Russia");
//        map.showCountries();
//
//        map.changeCityInfo(5,3,"TORONTO",94234710,0);
//        map.showCities(3);
//
//        System.out.println("-------------------------------------------");
//
//        map.deleteCity(4);
//        map.deleteCity(1);
//
//        map.deleteCountry(1);
//
//        map.showCountries();
//        map.showCities(1);
//        map.showCities(2);
//        map.showCities(3);
//        map.showCities(4);
//        map.showCities(5);
//
//        System.out.println("-------------------------------------------");
//
//        map.findCity(3);
//        map.findCountry(2);
//
//        System.out.println("-------------------------------------------");
//
//        map.countCities(1);
//        map.countCities(2);
//        map.countCities(3);
//
//        System.out.println("-------------------------------------------");
//
//        map.showAllCities();
//
//        System.out.println("-------------------------------------------");
//
//        map.deleteCity(3);
//        map.deleteCity(4);
//        map.deleteCity(5);
//        map.deleteCountry(2);
//        map.deleteCountry(3);
//
//        map.showCountries();
//        map.showCities(1);
//        map.showCities(2);
//        map.showCities(3);
//        map.showCities(4);
//        map.showCities(5);
//
//        System.out.println("-------------------------------------------");

    }
}
