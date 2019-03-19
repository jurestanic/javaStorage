package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Baza extends Konekcija {

    private Statement upit;
    private PreparedStatement execUpit;

    public Baza(){
        connect();
    }

    @Override
    public ResultSet select (String sql) {
        try {
            this.upit = this.konekcija.createStatement();
            return this.upit.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println ("Nastala je greška priliko izvršavanja upita " + e.getMessage());
            return null;
        }
    }

    @Override
    public PreparedStatement exec (String sql) {
        try {
            this.execUpit = this.konekcija.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return this.execUpit;
        } catch (SQLException e) {
            System.out.println("Nisam uspio izvršiti upit " + e.getMessage());
        }
        return null;
    }
}