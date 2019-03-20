package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import model.Proizvod;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StorageController implements Initializable {

    Proizvod odabraniProizvod;

    @FXML
    TableView tbl;
    @FXML
    TableColumn naziv;
    @FXML
    TableColumn kategorija;
    @FXML
    TableColumn kolicina;
    @FXML
    TableColumn cijena;
    @FXML
    TableColumn skladiste;

    @FXML
    Button dodajBtn;
    @FXML
    Button urediBtn;
    @FXML
    Button brisiBtn;

    @FXML
    TextField nazivTxt;
    @FXML
    TextField kategorijaTxt;
    @FXML
    TextField kolicinaTxt;
    @FXML
    TextField cijenaTxt;
    @FXML
    TextField skladisteTxt;
    @FXML
    Label userName;
    @FXML
    Label errorTxt;

    @FXML
    Button logoutBtn;
    @FXML
    Button adminBtn;



    @FXML
    public void logout(ActionEvent e){
        newStage(e,"/view/Login.fxml", 600, 400, "Login");
    }

    @FXML
    public void openAdminPanel(ActionEvent e){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Admin.fxml"));
            Scene scene = new Scene(root);
            Stage adminStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            adminStage.setScene(scene);
            adminStage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private void newStage(ActionEvent e, String path, int x, int  y, String title){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(new Scene(root, x, y));
            stage.show();
            ((Node) (e.getSource())).getScene().getWindow().hide();
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }


    @FXML
    public void dodaj (ActionEvent e) {

        String ime = this.nazivTxt.getText();
        String kategorija = this.kategorijaTxt.getText();
        String kolicina = this.kolicinaTxt.getText();
        String cijena = this.cijenaTxt.getText();
        int skladiste = Integer.parseInt(this.skladisteTxt.getText());

        Proizvod noviProizvod = new Proizvod(0, ime, kategorija, kolicina, cijena, skladiste);
        ObservableList<Proizvod> data = Proizvod.listaProizvoda();

        if(!Korisnik.checkIsAdmin()){
            if(!checkKat(noviProizvod)) return;
        }

        if(!checkStorage(noviProizvod)) return;


        for(Proizvod p : data){
            if(p.getIme().equalsIgnoreCase(noviProizvod.getIme()) && p.getKategorija().equalsIgnoreCase(noviProizvod.getKategorija())){
                p.setKolicina(String.valueOf(Integer.parseInt(p.getKolicina()) + Integer.parseInt(noviProizvod.getKolicina())));
                p.update();
                refreshTableView();
                noviProizvod.exists = true;
                break;
            }
        }

        if(!noviProizvod.exists){
            noviProizvod.exists = true;
            noviProizvod.create();
            refreshTableView();
        }
    }

    @FXML
    public void odaberi (Event e) {
        this.odabraniProizvod = (Proizvod) this.tbl.getSelectionModel().getSelectedItem();
        this.nazivTxt.setText(this.odabraniProizvod.getIme());
        this.kategorijaTxt.setText(this.odabraniProizvod.getKategorija());
        this.kolicinaTxt.setText(this.odabraniProizvod.getKolicina());
        this.cijenaTxt.setText(this.odabraniProizvod.getCijena());
        this.skladisteTxt.setText(String.valueOf(this.odabraniProizvod.getSkladiste()));
    }


    @FXML
    public void uredi(Event e) {
        this.odabraniProizvod.setIme(this.nazivTxt.getText());
        this.odabraniProizvod.setKategorija(this.kategorijaTxt.getText());
        this.odabraniProizvod.setKolicina(this.kolicinaTxt.getText());
        this.odabraniProizvod.setCijena(this.cijenaTxt.getText());
        this.odabraniProizvod.setSkladiste(Integer.parseInt(this.skladisteTxt.getText()));
        if(!checkKat(odabraniProizvod) || !checkStorage(odabraniProizvod)) return;
        this.odabraniProizvod.update();
        refreshTableView();
    }

    @FXML
    public void brisi(Event e) {
        if (this.odabraniProizvod != null) {
            this.odabraniProizvod.delete();
            clearTxtFields();
        }
        refreshTableView();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Proizvod.getKatNum();

        refreshTableView();

        if(Korisnik.checkIsAdmin()) {
            adminBtn.setVisible(true);
        } else {
            adminBtn.setVisible(false);
        }

        userName.setText("Welcome " + Korisnik.userName + "!");

        naziv.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("Ime"));
        kategorija.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("Kategorija"));
        kolicina.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("Kolicina"));
        cijena.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("Cijena"));
        skladiste.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("Skladiste"));

        refreshTableView();
    }

    private boolean checkKat(Proizvod proizvod){
        if(proizvod.getKatInt() == -1) {
            errorTxt.setText("Ne postoji ta kategorija!");
            return false;
        }  else {
            errorTxt.setText("");
            return true;
        }
    }

    private boolean checkStorage(Proizvod proizvod){
        if (proizvod.getStorageInt() == -1){
            errorTxt.setText("Ne postoji to skladište!");
            return false;
        } else {
            errorTxt.setText("");
            return true;
        }
    }

    private void clearTxtFields(){
        this.nazivTxt.setText("");
        this.kategorijaTxt.setText("");
        this.kolicinaTxt.setText("");
        this.cijenaTxt.setText("");
        this.skladisteTxt.setText("");
    }

    private void refreshTableView() {
        ObservableList<Proizvod> data = Proizvod.listaProizvoda();
        this.tbl.setItems(data);
        tbl.getSelectionModel().selectFirst();
    }


}
