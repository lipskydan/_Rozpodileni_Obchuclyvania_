package com.company;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.setTitle("Labrasp2 Lab1");
        Button btn = new Button();

        btn.setText("start demonstration");
        btn.setOnAction(event -> {
            btn.setVisible(false);
            Label label = new Label("Look at console ;)");
            root.getChildren().add(label);
           alg();
        });

        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 350, 350));
        primaryStage.show();

    }


    void alg(){
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



