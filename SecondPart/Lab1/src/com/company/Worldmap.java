package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Worldmap {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = null;
    Document document = null;
    String filename;

    // Массив стран
    public ArrayList<Country> countries = new ArrayList<>();
    // Массив городов
    public ArrayList<City> cities =  new ArrayList<>();

    void showCountries(){
        for (Country a: countries){
            System.out.println("code: " + a.code + ", " + "name: " + a.name);
        }
    }
    void showCities(){
        for (City a: cities){
            System.out.println("code: " + a.code + ", " + "name: " + a.name + ", " + "isCapital: " + a.isCapital + ", " + "count: " + a.count + ", " + "country name: " + a.country.name + ", " + "country code: " + a.country.code);
        }
    }

    Worldmap(String inFilename){
        filename = inFilename;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = builder.parse(new File(filename));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.getDocumentElement().normalize();
    }

    // Записать данные в файл XML
    public void saveToFile() {

        File fileToDelete = new File(filename);
        fileToDelete.delete();

        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        doc = db.newDocument();
        Element root = doc.createElement("map");
        doc.appendChild(root);

        for (Country c: countries){

            Element country = doc.createElement("country");
            country.setAttribute("id", String.valueOf(c.code));
            country.setAttribute("name", c.name);
            root.appendChild(country);

            for (City cc: cities){
                if (cc.country.code == c.code){
                    Element city = doc.createElement("city");
                    city.setAttribute("id", String.valueOf(cc.code));
                    city.setAttribute("name", cc.name);
                    city.setAttribute("count", String.valueOf(cc.count));
                    city.setAttribute("iscap", String.valueOf(cc.isCapital));
                    country.appendChild(city);
                }
            }
        }


        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(filename));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        try {
            transformer.transform(domSource, fileResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }

    // Прочитать данные из файла XML
    public void loadFromFile() {

        try {
            document = builder.parse(new File(filename));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element root = document.getDocumentElement();
        if (root.getTagName().equals("map"))
        {
            NodeList listCountries = root.getElementsByTagName("country");
            for (int i=0; i<listCountries.getLength(); i++)
            {

                Element country = (Element)listCountries.item(i);
                String countryCode = country.getAttribute("id");
                String countryName = country.getAttribute("name");
//                System.out.println(countryCode + " " +countryName + ":");

                countries.add(new Country(Integer.parseInt(countryCode),countryName));

                NodeList listCities = country.getElementsByTagName("city");

                for (int j=0; j<listCities.getLength(); j++)
                {
                    Element city = (Element)listCities.item(j);
                    String cityName = city.getAttribute("name");
                    String id = city.getAttribute("id");
                    String iscap = city.getAttribute("iscap");

                    Boolean capital = false;
                    if (iscap.equals("1")) capital = true;

                    String count = city.getAttribute("count");
//                    System.out.println(" " + cityName + " " + id);

                    cities.add(new City(Integer.parseInt(id), cityName, capital, Integer.parseInt(count), countries.get(countries.size()-1)));
                }
            }
        }
    }

    // Добавить новую страну
    public void addCountry(int code, String name) {
        countries.add(new Country(code,name));
    }

    // Получить страну c заданным кодом
    public Country getCountry(int code) {
        for (Country a: countries){
            if (a.code == code) return a;
        }
        return null;
    }

    // Получить страну c заданным номером
    public Country getCountryInd(int index) {
        return countries.get(index);
    }

    // Получить количество стран
    public int countCountries() {
        return countries.size();
    }

    // Удалить страну
    public void deleteCountry(int code) {

       for(int i=0; i < cities.size();i++) deleteCity(code);

        for (Country a: countries) {
            if (a.code == code){
                countries.remove(a);
            }
        }
    }

    // Удалить город
    public void deleteCity(int countryCode){
        for (City a: cities) {
            if (a.country.code == countryCode){
                cities.remove(a);
                return;
            }
        }
    }

    // Добавить новый город для заданной страны
    public void addCity(int code, String name, boolean isCapital, int count, int countryCode) {
        cities.add(new City(code,name,isCapital,count,getCountry(countryCode)));
    }

}
