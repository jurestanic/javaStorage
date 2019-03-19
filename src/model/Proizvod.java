package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Proizvod implements Model{

    private SimpleIntegerProperty sifra;
    private SimpleStringProperty ime;
    private SimpleStringProperty kategorija;
    private SimpleStringProperty kolicina;
    private SimpleStringProperty cijena;
    private SimpleIntegerProperty skladiste;

    public static Map<String, Integer> map = new HashMap<>();
    private static int katNum;
    public boolean exists;

    public static void getKatNum(){
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM kategorija");
        try{
            while(rs.next()){
                map.put(rs.getString("ime"),rs.getInt("kategorija_id"));
                katNum++;
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        DB.close();
    }

    public Proizvod (Integer sifra, String ime, String kategorija, String kolicina, String cijena, int skladiste) {
        this.sifra = new SimpleIntegerProperty (sifra);
        this.ime = new SimpleStringProperty(ime);
        this.kategorija = new SimpleStringProperty(kategorija);
        this.kolicina = new SimpleStringProperty(kolicina);
        this.cijena = new SimpleStringProperty(cijena);
        this.skladiste = new SimpleIntegerProperty(skladiste);
    }

    public Integer getSifra () {
        return sifra.get();
    }
    public String getIme () {
        return ime.get();
    }
    public String getKategorija () {
        return kategorija.get();
    }
    public String getKolicina () {
        return kolicina.get();
    }
    public String getCijena () {
        return cijena.get();
    }
    public int getSkladiste () {
        return skladiste.get();
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public void setKategorija(String kategorija) {
        this.kategorija.set(kategorija);
    }

    public void setKolicina(String kolicina) {
        this.kolicina.set(kolicina);
    }

    public void setCijena(String cijena) {
        this.cijena.set(cijena);
    }

    public void setSkladiste(int skladiste) {
        this.skladiste.set(skladiste);
    }

    public int getKatInt(){
        if(map.get(this.getKategorija()) != null){
            return map.get(this.getKategorija());
        } else {
            return -1;
        }
    }

    public int getStorageInt(){
        if(this.getSkladiste() != 1){
            return -1;
        } else {
            return 1;
        }
    }

    public static ObservableList<Proizvod> listaProizvoda() {
        ObservableList<Proizvod> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM proizvod p JOIN kategorija k WHERE p.kategorija_id=k.kategorija_id");
        try {
            while (rs.next()) {
                lista.add(new Proizvod(rs.getInt("id"), rs.getString("p.ime"), rs.getString("k.ime") ,
                                       rs.getString("kolicina"), rs.getString("cijena"), rs.getInt("skladiste_id")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        DB.close();
        return lista;
    }


    @Override
    public void create() {
        try {
            Baza DB = new Baza();

            if((getKatInt() == -1) && (Korisnik.checkIsAdmin())) {
                map.put(getKategorija(), ++katNum);
                PreparedStatement katUpit = DB.exec("INSERT INTO kategorija VALUES (?,?)");
                katUpit.setInt(1, getKatInt());
                katUpit.setString(2, getKategorija());
                katUpit.executeUpdate();
            }

            PreparedStatement upit = DB.exec("INSERT INTO proizvod VALUES (null,?,?,?,?,?)");
            upit.setString(1, this.getIme());
            upit.setInt(2, getKatInt());
            upit.setString(3, this.getKolicina());
            upit.setString(4, this.getCijena());
            upit.setInt(5, this.getSkladiste());

            upit.executeUpdate();
            DB.close();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("UPDATE proizvod SET ime=?, kategorija_id=?, kolicina=?, cijena=?, skladiste_id=? WHERE id=?");
            upit.setString(1, this.getIme());
            upit.setInt(2, getKatInt());
            upit.setString(3, this.getKolicina());
            upit.setString(4, this.getCijena());
            upit.setInt(5, this.getSkladiste());
            upit.setInt(6, this.getSifra());
            upit.executeUpdate();
            DB.close();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
        }

    }

    @Override
    public void delete() {
        try {
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("DELETE FROM proizvod WHERE id=?");
            upit.setInt(1, this.getSifra());
            upit.executeUpdate();
            DB.close();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
        }
    }
}
