package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Korisnik;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    Korisnik odabraniKorisnik;


    @FXML
    Button backBtn;
    @FXML
    Button urediBtn;

    @FXML
    TableView table;
    @FXML
    TableColumn ime;
    @FXML
    TableColumn uloga;

    @FXML
    TextField odabranaUloga;
    @FXML
    TextField novaUloga;
    @FXML
    Label errorTxt;

    @FXML
    public void choose () {
        this.odabraniKorisnik = (Korisnik) this.table.getSelectionModel().getSelectedItem();
        this.odabranaUloga.setText(this.odabraniKorisnik.getUloga());
    }


    @FXML
    private void update() {
        this.odabraniKorisnik.setUloga(this.novaUloga.getText());
        if(odabraniKorisnik.getUlogaInt() == -1){
            errorTxt.setText("Ne postoji ta uloga!");
        } else {
            errorTxt.setText("");
            this.odabraniKorisnik.update();
            clearTxtFields();
            refreshTableView();
        }

    }

    @FXML
    private void goBack(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Storage.fxml"));
            Scene scene = new Scene(root);
            Stage adminStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            adminStage.setScene(scene);
            adminStage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTableView();

        ime.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("Ime"));
        uloga.setCellValueFactory(new PropertyValueFactory<Korisnik, String>("Uloga"));

        refreshTableView();
    }

    private void clearTxtFields(){
        this.odabranaUloga.setText("");
        this.novaUloga.setText("");
    }

    private void refreshTableView() {
        ObservableList<Korisnik> data = Korisnik.listaKorisnika();
        this.table.setItems(data);
        table.getSelectionModel().selectFirst();
    }
}
