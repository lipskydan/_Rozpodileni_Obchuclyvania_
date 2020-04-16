package com.company;

import com.myrmi.Map;
import java.sql.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MapImpl extends UnicastRemoteObject
implements Map {

    private  String URL = "";
    private  String USERNAME = "";
    private  String PASSWORD = "";

    private Connection connection = null;
    private Statement statement = null;

    public MapImpl(String DBName, String ip, int port, String userName, String password) throws RemoteException {
        super();
        URL = "jdbc:mysql://"+ ip + ":" + port + "/" + DBName + "?serverTimezone=UTC";
        USERNAME = userName;
        PASSWORD = password;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String addCountry(int id, String name)throws RemoteException {
        String sql = "INSERT INTO COUNTRIES (ID_CO, NAME)" + "VALUES ("+id+", '"+name+"')";

        try {
            statement.executeUpdate(sql);
            System.out.println("[Map::addCountry()] Страна " + name + " успешно добавлена!");
            return "[Map::addCountry()] Страна " + name + " успешно добавлена!";
        } catch (SQLException e) {
            System.out.println("[Map::addCountry()] ОШИБКА! Страна " + name + " не добавлена!");
            System.out.println(" >> "+e.getMessage());
            return "[Map::addCountry()] ОШИБКА! Страна " + name + " не добавлена!";
        }
    }

    public String deleteCountry(int id) throws RemoteException{
        String sql = "DELETE FROM COUNTRIES WHERE ID_CO = " + id;
        try {
            int c = statement.executeUpdate(sql);

            if (c>0) {
                System.out.println("[Map::deleteCountry()] Страна с идентификатором " + id +" успешно удалена!");
                return "[Map::deleteCountry()] Страна с идентификатором " + id +" успешно удалена!";
            } else {
                System.out.println("[Map::deleteCountry()] Страна с идентификатором " + id +" не найдена!");
                return "[Map::deleteCountry()] Страна с идентификатором " + id +" не найдена!";
            }

        } catch (SQLException e) {
            System.out.println("[Map::deleteCountry()] ОШИБКА при удалении страны с идентификатором " + id + ", в этой стране есть города (удалите сначала их)");
//            System.out.println(" >> " + e.getMessage());
            return "[Map::deleteCountry()] ОШИБКА при удалении страны с идентификатором " + id + ", в этой стране есть города (удалите сначала их)";
        }
    }

    public String showCountries() throws RemoteException {
        String sql = "SELECT ID_CO, NAME FROM COUNTRIES";
        String res = "[Map::showCountries()] СПИСОК СТРАН: " + "-";
        try
        {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("[Map::showCountries()] СПИСОК СТРАН:");
            while (rs.next())
            {
                int id = rs.getInt("ID_CO");
                String name = rs.getString("NAME");
                System.out.println(" >> "+ id + ", " + name);
                res += "  " + id + ". " + name + "-";
            }
            rs.close();
        } catch (SQLException e)
        {
            res = "[Map::showCountries()] ОШИБКА при получении списка стран";
            System.out.println("[Map::showCountries()] ОШИБКА при получении списка стран");
            System.out.println(" >> "+e.getMessage());
        }
        return res;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String addCity(int idCity, int idCountry, String name, int count, int isCapital) throws RemoteException {
        String sql = "INSERT INTO CITIES (ID_CI, ID_CO, NAME, COUNT, ISCAPITAL)" + "VALUES ("+idCity+","+idCountry+", '"+name+"',"+count+","+isCapital+")";

        try {
            statement.executeUpdate(sql);
            System.out.println("[Map::addCountry()] Страна " + name + " успешно добавлена!");
            return "[Map::addCountry()] Страна " + name + " успешно добавлена!";
        } catch (SQLException e) {
            System.out.println("[Map::addCountry()] ОШИБКА! Страна " + name + " не добавлена!");
            System.out.println(" >> " + e.getMessage());
            return "[Map::addCountry()] ОШИБКА! Страна " + name + " не добавлена!";
        }
    }

    public String deleteCity(int id) throws RemoteException {
        String sql = "DELETE FROM CITIES WHERE ID_CI = " + id;
        try {
            int c = statement.executeUpdate(sql);

            if (c>0) {
                System.out.println("[Map::deleteCity()] Город с идентификатором " + id +" успешно удалена!");
                return "[Map::deleteCity()] Город с идентификатором " + id +" успешно удалена!";
            } else {
                System.out.println("[Map::deleteCity()] Город с идентификатором " + id +" не найдена!");
                return "[Map::deleteCity()] Город с идентификатором " + id +" не найдена!";
            }

        } catch (SQLException e) {
            System.out.println("[Map::deleteCity()] ОШИБКА при удалении города с идентификатором " + id);
            //System.out.println(" >> " + e.getMessage());
            return "[Map::deleteCity()] ОШИБКА при удалении города с идентификатором " + id;
        }
    }

    public String showCities(int idCountry) throws RemoteException {
        String sql = "SELECT ID_CI, ID_CO, NAME, COUNT, ISCAPITAL FROM CITIES";
        String res = "[Map::showCities()] СПИСОК ГОРОДОВ: " + "-";
        try
        {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("[Map::showCities()] СПИСОК ГОРОДОВ " + " :");
            while (rs.next())
            {
                int idCity = rs.getInt("ID_CI");
                int idCountryTmp = rs.getInt("ID_CO");
                String nameCity = rs.getString("NAME");
                Integer count = rs.getInt("COUNT");
                Boolean isCapital = (rs.getInt("ISCAPITAL") == 1 ? true : false);

                if (idCountry == idCountryTmp){
                    System.out.println("idCity: " + idCity + "  idCountry:" + idCountryTmp + "   nameCity:" + nameCity + "   count:" + count + "   isCapital:"  + isCapital);
                    res += "idCity: " + idCity + "  idCountry:" + idCountryTmp + "   nameCity:" + nameCity + "   count:" + count + "   isCapital:"  + isCapital + "-";
                }
            }

            rs.close();
        } catch (SQLException e)
        {
            res = "[Map::showCities()] ОШИБКА при получении списка городов";
            System.out.println("[Map::showCities()] ОШИБКА при получении списка городов");
            System.out.println(" >> "+e.getMessage());
        }
        return res;
    }

    public String showAllCities() throws RemoteException {
        String sql = "SELECT ID_CI, ID_CO, NAME, COUNT, ISCAPITAL FROM CITIES";
        String res = "[Map::showAllCities()] СПИСОК ВСЕХ ГОРОДОВ: " + "-";
        try
        {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("[Map::showAllCities()] СПИСОК ВСЕХ ГОРОДОВ " + " :");
            while (rs.next())
            {
                int idCity = rs.getInt("ID_CI");
                int idCountryTmp = rs.getInt("ID_CO");
                String nameCity = rs.getString("NAME");
                Integer count = rs.getInt("COUNT");
                Boolean isCapital = (rs.getInt("ISCAPITAL") == 1 ? true : false);

                System.out.println("idCity: " + idCity + "  idCountry:" + idCountryTmp + "   nameCity:" + nameCity + "   count:" + count + "   isCapital:"  + isCapital);
                res += "idCity: " + idCity + "  idCountry:" + idCountryTmp + "   nameCity:" + nameCity + "   count:" + count + "   isCapital:"  + isCapital + "-";
            }

            rs.close();
        } catch (SQLException e)
        {
            System.out.println("[Map::showCities()] ОШИБКА при получении списка городов");
            System.out.println(" >> "+e.getMessage());
            res = "[Map::showCities()] ОШИБКА при получении списка городов";
        }
        return res;
    }

    public String changeCityInfo(int idCity,int idCountry, String name, int count, int isCapital) throws RemoteException {
        System.out.println("[Map::changeCountryInfo()]");

        String sql1 = "UPDATE CITIES SET NAME = '"+name+"' WHERE ID_CI = '" + idCity + "'";
        String sql2 = "UPDATE CITIES SET ID_CO = '"+idCountry+"' WHERE ID_CI = '" + idCity + "'";
        String sql3 = "UPDATE CITIES SET COUNT = '"+count+"' WHERE ID_CI = '" + idCity + "'";
        String sql4 = "UPDATE CITIES SET ISCAPITAL = '"+isCapital+"' WHERE ID_CI = '" + idCity + "'";

        try {
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
            statement.executeUpdate(sql3);
            statement.executeUpdate(sql4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "[Map::changeCountryInfo()] City (ID: " + idCity + " ) was changed";
    }

    public String countCities(int idCountry) throws RemoteException  {
        String sql = "SELECT ID_CI, ID_CO, NAME, COUNT, ISCAPITAL FROM CITIES";
        int count = 0;
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int idCountryTmp = rs.getInt("ID_CO");
                if (idCountry == idCountryTmp) count++;
            }
            rs.close();
            System.out.println("[Map::countCities()] count cities for country (ID: " + idCountry +") is " + count);
            return "[Map::countCities()] count cities for country (ID: " + idCountry +") is " + count;
        } catch (SQLException e) {
            System.out.println("[Map::showCities()] ОШИБКА при получении списка городов");
            System.out.println(" >> "+e.getMessage());
        }
        return "[Map::showCities()] ОШИБКА при получении списка городов";
    }

}
