/**
 * 
 */
package fxKirjaloki2;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * Aloitusdialogi, jossa kysytään kenen kirjalokia halutaan katsella. Käyttäjä syöttää kirjalokinsa nimen.
 * 
 * @author heta
 * @version 28.1.2026
 *
 */
public class AloitusViewController implements ModalControllerInterface<String>{
    
    /*
     * Käyttäjän syöttämä teksti.
     */
    @FXML private TextField textVastaus;
    private String vastaus = null;


    /*
     * Siirrytään kirjalokiin painettaessa ok-nappia.
     */
    @FXML private void handleOK() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }

    /*
     * Poistutaan dialogista.
     */
    @FXML private void handleCancel() {
        ModalController.closeStage(textVastaus);
    }

    /*
     * Automaattinen get-metodi.
     */
    @Override
    public String getResult() {
        return vastaus;
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
        
    }

    /*
     * Asetetaan oletusteksti tekstikenttään.
     */
    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
        
    }
    
    //--------------------------------------------------------------------------------------------------------------------
    
    
    /**
     * Luodaan kirjalokin nimen kysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                AloitusViewController.class.getResource("AloitusView.fxml"),
                "Kirjaloki",
                modalityStage, oletus);
    }


}
