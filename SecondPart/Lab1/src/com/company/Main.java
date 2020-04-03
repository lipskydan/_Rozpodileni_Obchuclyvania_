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

        a.addCountry(3, "Белоруссия");
        a.addCity(6, "Минск",true, 1700000,3);
        a.addCity(7, "Витебск",false, 3000000,3);


        System.out.println("\n----------------------------------------------------------");
        System.out.println("----------------------------------------------------------\n");

        a.showCountries();
        a.showCities();

        a.saveToFile();





    }

}



