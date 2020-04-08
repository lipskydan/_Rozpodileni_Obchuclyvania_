package com.company;

import java.sql.*;

public class Map {

    private  String URL = "";
    private  String USERNAME = "";
    private  String PASSWORD = "";

    private Connection connection = null;
    private Statement statement = null;

    public Map(String DBName, String ip, int port, String userName, String password){
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

    public boolean addCountry(int id, String name) {
        String sql = "INSERT INTO COUNTRIES (ID_CO, NAME)" + "VALUES ("+id+", '"+name+"')";

        try {
            statement.executeUpdate(sql);
            System.out.println("[Map::addCountry()] Страна " + name + " успешно добавлена!");
            return true;
        } catch (SQLException e) {
            System.out.println("[Map::addCountry()] ОШИБКА! Страна " + name + " не добавлена!");
            System.out.println(" >> "+e.getMessage());
            return false;
        }
    }

    public boolean deleteCountry(int id){
        String sql = "DELETE FROM COUNTRIES WHERE ID_CO = " + id;
        try {
            int c = statement.executeUpdate(sql);

            if (c>0) {
                System.out.println("[Map::deleteCountry()] Страна с идентификатором " + id +" успешно удалена!");
                return true;
            } else {
                System.out.println("[Map::deleteCountry()] Страна с идентификатором " + id +" не найдена!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("[Map::deleteCountry()] ОШИБКА при удалении страны с идентификатором " + id);
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public void findCountry(int id){
        String sql = "SELECT ID_CO, NAME FROM COUNTRIES";

        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                 int idTmp = rs.getInt("ID_CO");

                 if (idTmp == id) {
                     String name = rs.getString("NAME");
                     System.out.println("[Map::findCountry()] "+ id + " - " + name);
                     rs.close();
                     return;
                 }
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void showCountries() {
        String sql = "SELECT ID_CO, NAME FROM COUNTRIES";
        try
        {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("[Map::showCountries()] СПИСОК СТРАН:");
            while (rs.next())
            {
                int id = rs.getInt("ID_CO");
                String name = rs.getString("NAME");
                System.out.println(" >> "+ id + " - " + name);
            }
            rs.close();
        } catch (SQLException e)
        {
            System.out.println("[Map::showCountries()] ОШИБКА при получении списка стран");
            System.out.println(" >> "+e.getMessage());
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void showCities(){
        String sql = "SELECT ID_CI, ID_CO, NAME, COUNT, ISCAPITAL FROM CITIES";
        try
        {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("[Map::showCities()] СПИСОК ГОРОДОВ:");
            while (rs.next())
            {
                int idCity = rs.getInt("ID_CI");
                int idCountry = rs.getInt("ID_CO");
                String nameCity = rs.getString("NAME");
                Integer count = rs.getInt("COUNT");
                Boolean isCapital = (rs.getInt("ISCAPITAL") == 1 ? true : false);

                System.out.println("idCity: " + idCity + "  idCountry:" + idCountry + "   nameCity:" + nameCity + "   count:" + count + "   isCapital:"  + isCapital);
            }

            rs.close();
        } catch (SQLException e)
        {
            System.out.println("[Map::showCities()] ОШИБКА при получении списка городов");
            System.out.println(" >> "+e.getMessage());
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
