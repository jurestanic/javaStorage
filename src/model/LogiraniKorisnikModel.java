package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogiraniKorisnikModel {

    public static boolean logiraj (String kime, String lozinka) {
        Baza db = new Baza();
        PreparedStatement ps = db.exec("SELECT * FROM korisnik WHERE korisnicko_ime =? AND "
                + "lozinka=?");
        try {
            ps.setString(1, kime);
            ps.setString(2, lozinka);
            ResultSet rs = ps.executeQuery();

            return rs.next() ? true : false;
        } catch (SQLException ex) {
            System.out.println("Nastala je greška: "+ex.getMessage());
            return false;
        }
    }
}
