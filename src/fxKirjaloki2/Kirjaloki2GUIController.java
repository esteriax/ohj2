package fxKirjaloki2;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author heta
 * @version 12.2.2026
 *
 */
public class Kirjaloki2GUIController implements Initializable {
    
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private TextField hakuehto;
    @FXML private TextField syntyma;
    @FXML private Label labelVirhe;
    private String kirjalokinnimi = "Heta";
    
    /**
     * Avaa aloitusview:n, josta voi vaihtaa käyttäjää
     */
    public void handleAvaa() {
        avaa();
    }
    
    
    /*
     * Tallentaa syötetyt tiedot.
     */
    @FXML private void handleTallenna() {
        tallenna();

    }
    
    /*
     * Lopettaa ohjelman ja tallentaa mahdollisesti tallentamattomat tiedot.
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();

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
         avustus();
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
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
    }
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
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
        String uusinimi = AloitusViewController.kysyNimi(null, kirjalokinnimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    /*
     * Antaa hakulaatikkoon syötetyn tekstin mukaisen hakutuloksen
     */
    @FXML private void handleHakuehto() {
        String hakukentta = cbKentat.getSelectedText();
        String ehto = hakuehto.getText(); 
        if ( ehto.isEmpty() )
            naytaVirhe(null);
        else
            naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
            
    }
    
    /*
     * TODO PÄIVITÄ: Tarkistaa päiväyksen/vuosiluvun oikeinkirjoituksen
     */
    /*
    @FXML private void handlePaivays() {
        String hakukentta = syntyma.getText();
        if ( syntyma.isDirtyEmpty() )
            naytaVirhe(null);
        else
            naytaVirhe("Ei osata vielä tarkistaa; " + syntyma);
            
    }
    */
    
    /*
     * Näyttää tekstikentän virheen käyttöliittymässä, mikäli syöte ei ole oikeellinen. 
     * @param virhe viesti, joka näytetään virhekentässä
     */
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    /**
     * Alustaa kirjalokin lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     */
    protected void lueTiedosto(String nimi) {
        kirjalokinnimi = nimi;
        setTitle("Kirjaloki: " + kirjalokinnimi);
        String virhe = "Ei osata lukea vielä";  // TODO: tähän oikea tiedoston lukeminen
         //if (virhe != null) 
            Dialogs.showMessageDialog(virhe);
    }
    
    /**
     * Ohjelman käyttöohje TIM-suuunnitelmasivulla
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2023s/ht/heespari");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }


    /*
     * Asettaa otsikon elementille.
     */
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
        
    }



   
}