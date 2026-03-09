package fxKirjaloki2;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kirjaloki.Kirjailija;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

/**
 * 
 * @author heta
 * @version 26.2.2026
 *
 */
public class KirjailijanTiedotController implements ModalControllerInterface<Kirjailija>{

    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelKirjailija;
    @FXML private GridPane gridKirjailija;
    
    @FXML
    void handleDefaultCancel() {
        kirjailijaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

    @FXML
    void handleDefaultOK() {
        if ( kirjailijaKohdalla != null && kirjailijaKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    

    /**
     * 
     */
    public void initialize() {
        alusta();  
    }
    
    
 
    // -------------------------------------------------------------------------------------------------------------
    
    private Kirjailija kirjailijaKohdalla;
    private static Kirjailija apukirjailija = new Kirjailija(); // Jäsen jolta voidaan kysellä tietoja.
    private TextField[] edits;
    private int kentta = 0;
    

    /**
     * Luodaan GridPaneen kirjailijan tiedot
     * @param gridKirjailija mihin tiedot luodaan
     * @return luodut tekstikentät
     */
    public static TextField[] luoKentat(GridPane gridKirjailija) {
        gridKirjailija.getChildren().clear();
        TextField[] edits = new TextField[apukirjailija.getKenttia()];
        
        for (int i=0, k = apukirjailija.ekaKentta(); k < apukirjailija.getKenttia(); k++, i++) {
            Label label = new Label(apukirjailija.getKysymys(k));
            gridKirjailija.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridKirjailija.add(edit, 1, i);
        }
        return edits;
    }

    
    /**
     * Tyhjentää kirjailijan tiedot
     * @param edits tyhjennettävät kentät
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit: edits) 
            if ( edit != null ) edit.setText(""); 

    }
    
    /**
    * Palautetaan komponentin id:stä saatava luku
    * @param obj tutkittava komponentti
    * @param oletus mikä arvo jos id ei ole kunnollinen
    * @return komponentin id lukuna 
    */
   public static int getFieldId(Object obj, int oletus) {
       if ( !( obj instanceof Node)) return oletus;
       Node node = (Node)obj;
       return Mjonot.erotaInt(node.getId().substring(1),oletus);
   }



   /**
    * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
    * yksi iso tekstikenttä, johon voidaan tulostaa kirjailijoiden tiedot.
    */

    protected void alusta() {
        edits = luoKentat(gridKirjailija);
        for (TextField edit : edits)
            if ( edit != null )
                edit.setOnKeyReleased( e -> kasitteleMuutosKirjailijaan((TextField)(e.getSource())));
        panelKirjailija.setFitToHeight(true);

    }
    
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }

    
    /**
     * Käsitellään kirjailijaan tullut muutos
     * @param edit muuttunut kenttä
     */
    protected void kasitteleMuutosKirjailijaan(TextField edit) {
        if (kirjailijaKohdalla == null) return;
        int k = getFieldId(edit,apukirjailija.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = kirjailijaKohdalla.aseta(k,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    @Override
    public Kirjailija getResult() {
        return kirjailijaKohdalla;
    }

    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        kentta = Math.max(apukirjailija.ekaKentta(), Math.min(kentta, apukirjailija.getKenttia()-1));
        edits[kentta].requestFocus();

        
    }

    /**
     * @param oletus käsiteltävä kirjailija
     */
    @Override
    public void setDefault(Kirjailija oletus) {
        kirjailijaKohdalla = oletus;
        naytaKirjailija(edits, kirjailijaKohdalla);
        
    }
    
    
    /**
     * Näytetään kirjailijan tiedot TextField komponentteihin
     * @param edits taulukko TextFieldeistä johon näytetään
     * @param kirjailija näytettävä kirjailija
     */
    public static void naytaKirjailija(TextField[] edits, Kirjailija kirjailija) {
        if (kirjailija == null) return;
        for (int k = kirjailija.ekaKentta(); k < kirjailija.getKenttia(); k++) {
            edits[k].setText(kirjailija.anna(k));
        }


    }
    
    
    /**
     * Luodaan kirjailijan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @param kentta mikä kenttä saa fokuksen kun näytetään
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Kirjailija kysyKirjailija(Stage modalityStage, Kirjailija oletus, int kentta) {
        return ModalController.<Kirjailija, KirjailijanTiedotController>showModal(
                    KirjailijanTiedotController.class.getResource("KirjailijanTiedotDialogi.fxml"),
                    "Kirjaloki",
                    modalityStage, oletus,
                    ctrl -> ctrl.setKentta(kentta) 
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
