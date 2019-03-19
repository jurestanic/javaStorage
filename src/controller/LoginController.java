
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Korisnik;

public class LoginController implements Initializable {

    @FXML
    Label statusLbl;
    
    @FXML
    TextField imeTxt;
    
    @FXML
    PasswordField lozinkaTxt;

    public void login (ActionEvent e) {
        String ime = imeTxt.getText();
        String lozinka = lozinkaTxt.getText();
        
        if (ime.equals("") || lozinka.equals("")) {
            statusLbl.setText("Morate unijeti sve vrijednosti!");
        } else {
            if (Korisnik.login(ime, lozinka)) {
                noviProzor(e,"/view/Storage.fxml");
            } else {
                statusLbl.setText("Korisnicki podatci nisu ispravni!");
            }
        }
    }

    private void noviProzor(ActionEvent e, String path){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = new Stage();
            stage.setTitle("Storage Management");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 1024, 454));
            stage.show();
            ((Node) (e.getSource())).getScene().getWindow().hide();
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
