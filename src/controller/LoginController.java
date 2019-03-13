
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
import model.LogiraniKorisnikModel;

public class LoginController implements Initializable {

    @FXML
    Label statusLbl;
    
    @FXML
    TextField kimeTxt;
    
    @FXML
    PasswordField lozinkaTxt;

    public void prijavise (ActionEvent e) {
        String kime = kimeTxt.getText();
        String lozinka = lozinkaTxt.getText();
        
        if (kime.equals("") || lozinka.equals("")) {
            statusLbl.setText("Morate unijeti sve vrijednosti!");
        } else {
            if (LogiraniKorisnikModel.logiraj(kime, lozinka)) {
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
            stage.setScene(new Scene(root, 834, 400));
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
