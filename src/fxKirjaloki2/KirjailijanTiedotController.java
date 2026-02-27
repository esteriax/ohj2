package fxKirjaloki2;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kanta.PaivaysTarkistus;
import kirjaloki.Kirjailija;
import javafx.scene.control.Label;

/**
 * 
 * @author heta
 * @version 26.2.2026
 *
 */
public class KirjailijanTiedotController implements ModalControllerInterface<Kirjailija>{

    @FXML private Label labelVirhe;
    @FXML private TextField syntymaVuosi;
    @FXML private TextField lisatiedot;
    @FXML private TextField kirjailijaNimi;
    @FXML private TextField suosikki;
    private TextField muutokset[];
    
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
     * Tyhjentää kirjailijan tiedot
     * @param muutokset tehdyt muutokset
     */
    public static void tyhjenna(TextField[] muutokset) {
        for (TextField muutos : muutokset)
            muutos.setText("");
    }


    /**
     * Tekee tarvittavat muut alustukset.
     */
    protected void alusta() {
        muutokset = new TextField[]{kirjailijaNimi, syntymaVuosi, suosikki, lisatiedot};
    }
    
    @Override
    public Kirjailija getResult() {
        return kirjailijaKohdalla;
    }

    @Override
    public void handleShown() {
        kirjailijaNimi.requestFocus();
        
    }

    /**
     * @param oletus käsiteltävä kirjailija
     */
    @Override
    public void setDefault(Kirjailija oletus) {
        kirjailijaKohdalla = oletus;
        naytaKirjailija(muutokset, kirjailijaKohdalla);
        
    }
    
    
    /**
     * 
     */
    public void initialize() {
        alusta();  
    }
    
    /**
     * Näytetään kirjailijan tiedot TextField komponentteihin
     * @param muutokset taulukko tekstikenttineen
     * @param kirjailija näytettävä kirjailija
     */
    public static void naytaKirjailija(TextField[] muutokset, Kirjailija kirjailija) {
        if (kirjailija == null) return;
        muutokset[0].setText(kirjailija.getNimi());
        muutokset[1].setText(kirjailija.getSyntymaVuosi());
        muutokset[2].setText(kirjailija.getSuosikki());
        muutokset[3].setText(kirjailija.getLisatiedot());

    }
    
    
    /**
     * Luodaan kirjailijan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Kirjailija kysyKirjailija(Stage modalityStage, Kirjailija oletus) {
        return ModalController.<Kirjailija, KirjailijanTiedotController>showModal(
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
