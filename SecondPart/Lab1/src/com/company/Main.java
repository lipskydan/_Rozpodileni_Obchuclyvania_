package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Worldmap a = new Worldmap("map.xml");
        a.loadFromFile();

        a.showCountries();
        a.showCities();

        a.addCountry(4, "USA");
        a.addCity(6, "LA",true, 1700000,4);
        a.addCity(7, "New York",false, 3000000,4);


        System.out.println("\n----------------------------------------------------------");
        System.out.println("----------------------------------------------------------\n");

        a.showCountries();
        a.showCities();

        a.deleteCountry(3);

        System.out.println("\n----------------------------------------------------------");
        System.out.println("----------------------------------------------------------\n");

        a.showCountries();
        a.showCities();

        System.out.println("\n----------------------------------------------------------");
        System.out.println("----------------------------------------------------------\n");

        System.out.println("Country with code " + a.getCountry(2).code + " is " + a.getCountry(2).name);

        System.out.println("\n----------------------------------------------------------");
        System.out.println("----------------------------------------------------------\n");

        System.out.println("Country with index " + 0 + " is " + a.getCountryInd(0).name);

        System.out.println("\n----------------------------------------------------------");
        System.out.println("----------------------------------------------------------\n");

        System.out.println("count of countries is " + a.countCountries());

        a.saveToFile();


        try {
            System.out.println(XMLUtils.validateWithDTDUsingDOM("map.xml"));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}



