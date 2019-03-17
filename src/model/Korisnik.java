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

public class Korisnik implements Model{

    public enum Role {
        admin, member
    }

    public static Role user;
    public static String userName;

    private SimpleIntegerProperty id;
    private SimpleStringProperty ime;
    private SimpleStringProperty role;

    public static Map<String, Integer> map = new HashMap<String, Integer>();

    public Korisnik(int id, String ime, String role) {

        map.put("admin",1);
        map.put("member",2);

        this.id = new SimpleIntegerProperty(id);
        this.ime = new SimpleStringProperty(ime);
        this.role = new SimpleStringProperty(role);
    }

    public String getUloga(){
        return role.get();
    }

    public String getIme(){
        return ime.get();
    }

    public int getUlogaInt(){
        if(map.get(this.getUloga()) != null){
            return map.get(this.getUloga());
        } else {
            return -1;
        }
    }

    private int getId(){
        return this.id.get();
    }

    public void setUloga(String role){
        this.role.set(role);
    }

    public static ObservableList<Korisnik> listaKorisnika() {
        ObservableList<Korisnik> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM korisnik k JOIN role r WHERE k.role_id=r.id");
        try {
            while (rs.next()) {
                lista.add(new Korisnik(rs.getInt("k.id"), rs.getString("korisnicko_ime"),
                                       rs.getString("r.role_name")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public void create() {

    }

    @Override
    public void update() {
        try {
            setRole(this.getUlogaInt());
            Baza DB = new Baza();
            PreparedStatement upit = DB.exec("UPDATE korisnik SET role_id=? WHERE id=?");
            upit.setInt(1, getUlogaInt());
            upit.setInt(2, this.getId());
            upit.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
        }
    }

    @Override
    public void delete() {

    }

    public static boolean logiraj (String ime, String lozinka) {
        Baza db = new Baza();
        PreparedStatement ps = db.exec("SELECT * FROM korisnik WHERE korisnicko_ime =? AND "
                + "lozinka=?");
        try {

            ps.setString(1, ime);
            ps.setString(2, lozinka);
            ResultSet rs = ps.executeQuery();


            if(rs.next()){
                userName = rs.getString("korisnicko_ime");
                setRole(rs.getInt("role_id"));
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Nastala je greška: "+ex.getMessage());
            return false;
        }
    }

    public static void setRole(int id){
        if(id == 1){
            user = Role.admin;
        } else {
            user = Role.member;
        }
    }

    public static boolean checkIsAdmin(){
        return user.toString() == "admin";
    }
}
