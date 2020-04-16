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
        map.addCity(idCity,idCountry,name,count,isCapital);
    }

    public static void deleteCity(int id) throws RemoteException {
        map.deleteCity(id);
    }

    public static void showCity(int idCountry) throws RemoteException{
        map.showCities(idCountry);
    }

    public static void showAllCities() throws RemoteException{
        map.showAllCities();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws NotBoundException, RemoteException, MalformedURLException {
        String url = "//localhost:1099/Map";
        map = (Map) Naming.lookup(url);
        System.out.println("RMI object found");

        System.out.println("-----------------------------------------------------------");

        System.out.println(map.showCountries());

        System.out.println("-----------------------------------------------------------");

        addCountry(1,"Ukraine");
        addCountry(2,"USA");
        addCountry(3,"Canada");

        System.out.println("-----------------------------------------------------------");

        showCountries();


        System.out.println("-----------------------------------------------------------");

        deleteCountry(2);

        showCountries();

        System.out.println("-----------------------------------------------------------");

        deleteCountry(3);

        showCountries();

        System.out.println("-----------------------------------------------------------");

        deleteCountry(1);

        showCountries();

        System.out.println("-----------------------------------------------------------");

    }
}
