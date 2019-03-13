package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Proizvod;
import java.net.URL;
import java.util.ResourceBundle;

public class StorageController implements Initializable {

    Proizvod odabraniProizvod;

    @FXML
    TableView tbl;
    @FXML
    TableColumn naziv;
    @FXML
    TableColumn kolicina;
    @FXML
    TableColumn cijena;
    @FXML
    Button dodajBtn;
    @FXML
    Button urediBtn;
    @FXML
    Button brisiBtn;
    @FXML
    TextField nazivTxt;
    @FXML
    TextField kolicinaTxt;
    @FXML
    TextField cijenaTxt;

    @FXML
    public void dodaj (ActionEvent e) {
        String ime = this.nazivTxt.getText();
        String prezime = this.kolicinaTxt.getText();
        String email = this.cijenaTxt.getText();

        Proizvod noviProizvod = new Proizvod(0, ime, prezime, email);
        noviProizvod.create();

        ObservableList<Proizvod> data = Proizvod.listaKontakata();
        this.tbl.setItems(Proizvod.listaKontakata());

    }

    @FXML
    public void odaberi (Event e) {
        this.odabraniProizvod = (Proizvod) this.tbl.getSelectionModel().getSelectedItem();
        this.nazivTxt.setText(this.odabraniProizvod.getIme());
        this.kolicinaTxt.setText(this.odabraniProizvod.getPrezime());
        this.cijenaTxt.setText(this.odabraniProizvod.getEmail());
    }


    @FXML
    public void uredi(Event e) {
        this.odabraniProizvod.setIme(this.nazivTxt.getText());
        this.odabraniProizvod.setPrezime(this.kolicinaTxt.getText());
        this.odabraniProizvod.setEmail(this.cijenaTxt.getText());
        this.odabraniProizvod.update();
        ObservableList<Proizvod> data = Proizvod.listaKontakata();
        this.tbl.setItems(data);
    }

    @FXML
    public void brisi(Event e) {
        if (this.odabraniProizvod != null) {
            this.odabraniProizvod.delete();
        }
        ObservableList<Proizvod> data = Proizvod.listaKontakata();
        this.tbl.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Proizvod> data = Proizvod.listaKontakata();

        naziv.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("Ime"));
        kolicina.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("Prezime"));
        cijena.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("Email"));

        tbl.setItems(data);
        tbl.getSelectionModel().selectFirst();
    }


}
