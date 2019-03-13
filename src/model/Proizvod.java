package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Proizvod implements Model{

    private SimpleIntegerProperty sifra;
    private SimpleStringProperty ime;
    private SimpleStringProperty prezime;
    private SimpleStringProperty email;

    public Proizvod (Integer sifra, String ime, String prezime, String email) {
        this.sifra = new SimpleIntegerProperty (sifra);
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.email = new SimpleStringProperty(email);
    }
    public Integer getSifra () {
        return sifra.get();
    }
    public String getIme () {
        return ime.get();
    }
    public String getPrezime () {
        return prezime.get();
    }
    public String getEmail () {
        return email.get();
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public void setPrezime(String prezime) {
        this.prezime.set(prezime);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public static ObservableList<Proizvod> listaKontakata () {
        ObservableList<Proizvod> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM kontakt");
        try {
            while (rs.next()) {
                lista.add(new Proizvod(rs.getInt("id"), rs.getString("ime"), rs.getString("prezime"), rs.getString("email")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }


    @Override
    public void create() {
        try {
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("INSERT INTO kontakt VALUES (null,?,?,?)");
            upit.setString(1, this.getIme());
            upit.setString(2, this.getPrezime());
            upit.setString(3, this.getEmail());
            upit.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("UPDATE kontakt SET ime=?, prezime=?, email=? WHERE id=?");
            upit.setString(1, this.getIme());
            upit.setString(2, this.getPrezime());
            upit.setString(3, this.getEmail());
            upit.setInt(4, this.getSifra());
            upit.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
        }

    }

    @Override
    public void delete() {
        try {
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("DELETE FROM kontakt WHERE id=?");
            upit.setInt(1, this.getSifra());
            upit.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
        }
    }
}
