package com.company;

public class City {

    public int code; // Уникальный код города
    public String  name; // Название города
    public boolean isCapital; // Признак столицы
    public int count; // Количество жителей
    public Country  country; // Страна

    public City(int inCode, String inName, boolean inCapital, int inCount, Country inCountry) {
        code = inCode;
        name = inName;
        isCapital = inCapital;
        count = inCount;
        country = inCountry;
    }
}
