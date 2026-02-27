package fxKirjaloki2;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kanta.PaivaysTarkistus;
import kirjaloki.Kirjailija;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * 
 * @author heta
 * @version 26.2.2026
 *
 */
public class KirjailijanTiedotController implements ModalControllerInterface<Kirjailija>{

    @FXML private TextField syntymaVuosi;
    @FXML private Label labelVirhe;
    @FXML private TextArea lisatiedot;
    @FXML private TextField nimi;
    @FXML private TextField suosikki;
    
    @FXML
    void handleDefaultCancel() {
        ModalController.closeStage(labelVirhe);
    }

    @FXML
    void handleDefaultOK() {
        ModalController.closeStage(labelVirhe);
    }
    
    /*
     * Tarkistaa päiväyksen/vuosiluvun oikeinkirjoituksen
     */
    
    @FXML private void handleTarkistaVuosi() {
        String ehto = syntymaVuosi.getText(); 
        if ( ehto.isEmpty() || PaivaysTarkistus.tarkistaVuosi(ehto))
            naytaVirhe(null);
        else
            naytaVirhe("Korjaa syntymävuosi: " + ehto);         
    }
    
    
 
    // -------------------------------------------------------------------------------------------------------------
    private Kirjailija kirjailijaKohdalla;
    
    /**
     * Tyhjentään tekstikentät 
     */
    public void tyhjenna() {
        nimi.setText("");
        syntymaVuosi.setText("");
        suosikki.setText("");
        lisatiedot.setText("");
    }


    /**
     * Tekee tarvittavat muut alustukset.
     */
    protected void alusta() {
        //
    }
    
    @Override
    public Kirjailija getResult() {
        return kirjailijaKohdalla;
    }

    @Override
    public void handleShown() {
        nimi.requestFocus();
        
    }

    /**
     * @param oletus käsiteltävä kirjailija
     */
    @Override
    public void setDefault(Kirjailija oletus) {
        kirjailijaKohdalla = oletus;
        naytaKirjailija(kirjailijaKohdalla);
        
    }
    
    
    /**
     * 
     */
    public void initialize() {
        alusta();  
    }
    
    /**
     * Näytetään kirjailijan tiedot TextField komponentteihin
     * @param kirjailija näytettävä kirjailija
     */
    public void naytaKirjailija(Kirjailija kirjailija) {
        if (kirjailija == null) return;
        nimi.setText(kirjailija.getNimi());
        syntymaVuosi.setText(kirjailija.getSyntymaVuosi());
        suosikki.setText(kirjailija.getSuosikki());
        lisatiedot.setText(kirjailija.getLisatiedot());
    }
    
    
    /**
     * Luodaan kirjailijan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Kirjailija kysyKirjailija(Stage modalityStage, Kirjailija oletus) {
        return ModalController.showModal(
                    KirjailijanTiedotController.class.getResource("KirjailijanTiedotDialogi.fxml"),
                    "Kirjaloki",
                    modalityStage, oletus, null 
                );
        
    }
    
    /**
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

    
    
   

}
