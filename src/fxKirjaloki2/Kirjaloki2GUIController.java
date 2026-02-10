package fxKirjaloki2;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author heta
 * @version 30.1.2026
 *
 */
public class Kirjaloki2GUIController implements Initializable {
    
    
    /**
     * Avaa aloitusview:n, josta voi vaihtaa käyttäjää
     */
    public void handleAvaa() {
        avaa();
    }
    
    /*
     * TODO naytaVirhe-ohjelma, plus id-tarkistus
     */
    @FXML private void handleHakuehto() {
        eiToimi();
        /*String hakukentta = cbKentat.getSelectedText();
        String ehto = hakuehto.getText(); 
        if ( ehto.isEmpty() )
            naytaVirhe(null);
        else
            naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
            */
    }
    
    @FXML private void handleTallenna() {
        eiToimi();

    }
    
    /*
     * Lopettaa ohjelman ja tallentaa mahdollisesti tallentamattomat tiedot.
     */
    @FXML private void handleLopeta() {
        eiToimi();

    }
    
    /*
     * Avaa muokkausdialogin tyhjänä ja jos se tallennetaan OK:lla, lisätään uusi kirja.
     */
    @FXML private void handleUusiKirja() {
        eiToimi();

    }
    
    @FXML private void handlePoistaKirja() {
        eiToimi();
    }
    
    
    @FXML private void handleUusiKirjailija() {
        eiToimi();
    }
    
    /*
     * Aukaisee harjoitustyön suunnitelmasivun timistä
     */
     @FXML private void handleApua() {
        eiToimi();
     }
     
    
    @FXML private void handleMuokkaaKirja() {
        ModalController.showModal(Kirjaloki2GUIController.class.getResource("KirjanTiedotDialogi.fxml"), "Kirja", null, "");
    }
    
    @FXML private void handleMuokkaaKirjailija() {
        ModalController.showModal(Kirjaloki2GUIController.class.getResource("KirjailijanTiedotDialogi.fxml"), "Kirjailija", null, "");
    }
    
    @FXML private void handleTulosta() {
        ModalController.showModal(Kirjaloki2GUIController.class.getResource("TulostusView.fxml"), "Tulostus", null, "");
    }
    
    @FXML private void handleTietoja() {
        ModalController.showModal(Kirjaloki2GUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }
    
    
   

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
    
    //--------------------------------------------------------------------------------------------------------------------
    
    /**
     * dialogi, joka kertoo ettei toimi vielä
     */
    public void eiToimi() {
        Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Huomautus");
          alert.setHeaderText(null);
          alert.setContentText("Ei toimi vielä");
          alert.getDialogPane().setStyle("-fx-font-family: monospace;");
          alert.showAndWait();
    }
    
    /**
     * Avaa uuden kirjatiedoston. Tallentaa mahdolliset muutokset ennen tätä.
     * @return true jos onnistui, false jos kenttä jäi täyttämättä
     */
    public boolean avaa() {
        String uusinimi = AloitusViewController.kysyNimi(null, "heta");
        if (uusinimi == null) return false;
        //luetiedosto(uusinimi);
        return true;
    }

   
}