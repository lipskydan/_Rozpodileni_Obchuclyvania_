package com.company;
import com.myrmi.Map;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    private static Map map;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void showCountries() throws RemoteException {
        String[] fields = map.showCountries().split("-");
        String result = " ";
        for (int i = 0; i < fields.length; i++) result += fields[i] + "\n";
        System.out.println(result);
    }

    public static void addCountry(int id, String name) throws RemoteException {
        System.out.println(map.addCountry(id,name));
    }

    public static void deleteCountry(int id) throws RemoteException {
        System.out.println(map.deleteCountry(id));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void addCity(int idCity, int idCountry, String name, int count, int isCapital) throws RemoteException {
        System.out.println(map.addCity(idCity,idCountry,name,count,isCapital));
    }

    public static void deleteCity(int id) throws RemoteException {
        System.out.println(map.deleteCity(id));
    }

    public static void showCities(int idCountry) throws RemoteException{
        String[] fields = map.showCities(idCountry).split("-");
        String result = " ";
        for (int i = 0; i < fields.length; i++) result += fields[i] + "\n";
        System.out.println(result);
    }

    public static void showAllCities() throws RemoteException{
        String[] fields = map.showAllCities().split("-");
        String result = " ";
        for (int i = 0; i < fields.length; i++) result += fields[i] + "\n";
        System.out.println(result);
    }

    public static void changeCityInfo(int idCity,int idCountry, String name, int count, int isCapital) throws RemoteException {
        System.out.println(map.changeCityInfo(idCity,idCountry,name,count,isCapital));
    }

    public static void countCities(int idCountry) throws RemoteException {
        System.out.println(map.countCities(idCountry));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws NotBoundException, RemoteException, MalformedURLException {
        String url = "//localhost:1099/Map";
        map = (Map) Naming.lookup(url);
        System.out.println("RMI object found");

        System.out.println("-----------------------------------------------------------");

        showCountries();
        showCities(1);
        showCities(2);
        showCities(3);

        System.out.println("-----------------------------------------------------------");

        addCountry(1, "Russia");
        addCountry(2, "USA");
        addCountry(3,"Canada");

        showCountries();

        System.out.println("-----------------------------------------------------------");

        addCity(1,1,"MOSCOW",11612943,1);
        addCity(2,1,"SOCHI",343334,0);
        addCity(3,2,"NEW YORK",8363710,0);
        addCity(4,2,"LOS ANGELES",56451241,0);
        addCity(5,3,"TORONTO",94234710,1);

        showCities(1);
        showCities(2);
        showCities(3);


        System.out.println("-----------------------------------------------------------");

        deleteCity(2);
        deleteCity(1);

        deleteCountry(1);

        showCountries();
        showAllCities();

        System.out.println("-----------------------------------------------------------");

        changeCityInfo(3,2,"NEW YORK",1000000,1);
        countCities(2);
        countCities(3);

        System.out.println("-----------------------------------------------------------");

        showCountries();
        showAllCities();

        System.out.println("-----------------------------------------------------------");

        deleteCity(3);
        deleteCity(4);
        deleteCity(5);

        deleteCountry(2);
        deleteCountry(3);

        System.out.println("-----------------------------------------------------------");

        showCountries();
        showAllCities();

        System.out.println("-----------------------------------------------------------");

    }
}
