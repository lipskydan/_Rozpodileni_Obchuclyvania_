package com.myrmi;

import java.rmi.*;

public interface Map extends Remote {

    public String addCountry(int id, String name) throws RemoteException;
    public String deleteCountry(int id) throws RemoteException;
    public String showCountries() throws RemoteException;

    public String addCity(int idCity, int idCountry, String name, int count, int isCapital)throws RemoteException;
    public String deleteCity(int id) throws RemoteException;
    public String showCities(int idCountry) throws RemoteException;
    public String showAllCities() throws RemoteException;
    public String changeCityInfo(int idCity,int idCountry, String name, int count, int isCapital) throws RemoteException;
    public String countCities(int idCountry) throws RemoteException;

}
