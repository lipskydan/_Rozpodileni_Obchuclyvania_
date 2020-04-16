package com.company;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        MapImpl map = new MapImpl("MAP","localhost",3306,"root", "Steps.123");
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("Map", map);
        System.out.println("Server started!");
    }
}