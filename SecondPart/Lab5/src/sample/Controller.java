package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.client.Client;
import sample.dao.ConcreteDAO;
import sample.dao.DAO;

import javax.jms.JMSException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private TreeView treeView;
    @FXML
    private Button addCompany;
    @FXML
    private Button addFlight;
    @FXML
    private Button updateCompany;
    @FXML
    private Button updateFlight;
    @FXML
    private Button deleteCompany;
    @FXML
    private Button deleteFlight;
    @FXML
    private Button save;
    @FXML
    private TextField codeCompany;
    @FXML
    private TextField nameCompany;
    @FXML
    private TextField codeFlight;
    @FXML
    private TextField fromFlight;
    @FXML
    private TextField toFlight;
    @FXML
    private Label information;

    Client client;
    @FXML
    public void initialize() {
        try {
            client = new Client();
            printToTreeView();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }


    }
    public void printToTreeView() throws IOException, ClassNotFoundException, JMSException {
        treeView.setRoot(null);
        ArrayList<Aircompany> aircompanyes = client.getAll().getaircompanies();
        //ArrayList<TreeItem<String>> aircompanyesTree = new ArrayList<>();
        TreeItem<String> root = new TreeItem<>("Airport");

        for(int i = 0; i < aircompanyes.size(); i++){
            String info;
            info = "Code: " + aircompanyes.get(i).code + " Name: " + aircompanyes.get(i).name;
            ArrayList<Flight> flights = aircompanyes.get(i).getFlights();
            TreeItem<String> aircompany = new TreeItem<>(info);
            for(int j = 0; j < flights.size(); j++){
                String flightInfo = flights.get(j).code + " " + flights.get(j).from + " - " + flights.get(j).to;
                TreeItem<String> flight = new TreeItem<>(flightInfo);
                aircompany.getChildren().add(flight);
            }
            // aircompanyesTree.add(aircompany);
            root.getChildren().add(aircompany);
        }
        treeView.setRoot(root);
    }
    @FXML
    private void addCompany(ActionEvent event) throws IOException, ClassNotFoundException, JMSException {
        String code = codeCompany.getText();
        String name = nameCompany.getText();
        //Aircompany aircompany = new Aircompany(Integer.valueOf(code), name);
        client.addAircompany(Integer.valueOf(code), name);
        printToTreeView();
    }
    @FXML
    private void addFlight(ActionEvent event) throws IOException, ClassNotFoundException, JMSException {
        String code = codeFlight.getText();
        String from = fromFlight.getText();
        String to = toFlight.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected aircompany");
            return;
        }
        String codeAircompany = selectedItem.getValue().split(" ")[1];
        //Aircompany aircompany = xmlWorker.findAircompanyByCode(Integer.valueOf(codeAircompany));
        //xmlWorker.addFlight(Integer.valueOf(codeAircompany), new Flight(Integer.valueOf(code), from, to));

        client.addFlight(Integer.valueOf(codeAircompany), Integer.valueOf(code), from, to);
        printToTreeView();

    }
    @FXML
    private void updateCompany(ActionEvent event) throws IOException, ClassNotFoundException, JMSException {
        String newCode = codeCompany.getText();
        String newName = nameCompany.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected aircompany");
            return;
        }
        String codeAircompany = selectedItem.getValue().split(" ")[1];
        String nameAircompany = selectedItem.getValue().split(" ")[3];
        //Aircompany aircompany = xmlWorker.findAircompanyByCode(Integer.valueOf(codeAircompany));
        if(newCode.equals("")){
            newCode = codeAircompany;
        }
        if(newName.equals("")){
            newName = nameAircompany;
        }
        client.updateAircompany(Integer.valueOf(codeAircompany), Integer.valueOf(newCode), newName);
        printToTreeView();
    }
    @FXML
    public void updateFlight(ActionEvent event) throws IOException, ClassNotFoundException, JMSException {
        String newCode = codeFlight.getText();
        String newFrom = fromFlight.getText();
        String newTo = toFlight.getText();
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected aircompany");
            return;
        }
        String codeAircompany = selectedItem.getParent().getValue().split(" ")[1];
        String codeFlight = selectedItem.getValue().split(" ")[0];
        String fromFlight = selectedItem.getValue().split(" ")[1];
        String toFlight = selectedItem.getValue().split(" ")[3];
        if(newCode.equals("")){
            newCode = codeFlight;
        }
        if(newFrom.equals("")){
            newFrom = fromFlight;
        }
        if(newTo.equals("")){
            newTo = toFlight;
        }
        client.updateFlight(Integer.valueOf(codeAircompany), Integer.valueOf(codeFlight),
                Integer.valueOf(newCode), newFrom, newTo);
        printToTreeView();
    }
    @FXML
    public void deleteCompany(ActionEvent event) throws IOException, ClassNotFoundException, JMSException {
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected aircompany");
            return;
        }
        String codeAircompany = selectedItem.getValue().split(" ")[1];
        client.deleteAircompany(Integer.valueOf(codeAircompany));
        printToTreeView();
    }
    @FXML
    public void deleteFlight(ActionEvent event) throws IOException, ClassNotFoundException, JMSException {
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected aircompany");
            return;
        }
        String codeAircompany = selectedItem.getParent().getValue().split(" ")[1];
        String codeFlight = selectedItem.getValue().split(" ")[0];
        client.deleteFlight(Integer.valueOf(codeAircompany), Integer.valueOf(codeFlight));
        printToTreeView();
    }

}
