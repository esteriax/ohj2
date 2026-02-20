package fxKirjaloki2;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import kanta.PaivaysTarkistus;
import kirjaloki.Kirja;
import kirjaloki.Kirjailija;
import kirjaloki.Kirjaloki;
import kirjaloki.SailoException;


/**
 * @author heta
 * @version 12.2.2026
 *
 */
public class Kirjaloki2GUIController implements Initializable {
    
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private TextField hakuehto;
    @FXML private TextField lukuPvm;
    @FXML private TextField julkaisuVuosi;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelKirjailija;
    @FXML private ListChooser<Kirjailija> chooserKirjailijat;
    private String kirjalokinnimi = "Heta";
    
    /**
     * Avaa aloitusview:n, josta voi vaihtaa käyttäjää
     */
    public void handleAvaa() {
        avaa();
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
     * Tarkistaa päiväyksen/vuosiluvun oikeinkirjoituksen
     */
    
    @FXML private void handleTarkistaLukuPvm() {
        String ehto = lukuPvm.getText(); 
        if ( ehto.isEmpty() | PaivaysTarkistus.tarkistaLukuPvm(ehto) == true)
            naytaVirhe(null);
        else
            naytaVirhe("Tarkista päivämäärä: " + ehto);         
    }
    
    /*
     * Tarkistaa päiväyksen/vuosiluvun oikeinkirjoituksen
     */
    
    @FXML private void handleTarkistaVuosi() {
        String ehto = julkaisuVuosi.getText(); 
        if ( ehto.isEmpty() || PaivaysTarkistus.tarkistaVuosi(ehto))
            naytaVirhe(null);
        else
            naytaVirhe("Tarkista vuosiluku: " + ehto);         
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
     * Aukaisee harjoitustyön suunnitelmasivun timistä
     */
     @FXML private void handleApua() {
         avustus();
     }
    
    /*
     * Avaa muokkausdialogin tyhjänä ja jos se tallennetaan OK:lla, lisätään uusi kirja.
     */
    @FXML private void handleUusiKirja() {
        uusiKirja();
    }
    
    @FXML private void handleUusiKirjailija() {
        uusiKirjailija();
    }
    
    @FXML private void handlePoistaKirja() {
        eiToimi();
    }
    
    @FXML private void handlePoistaKirjailija() {
        eiToimi();
    }
    
    
    @FXML private void handleMuokkaaKirja() {
        ModalController.showModal(Kirjaloki2GUIController.class.getResource("KirjanTiedotDialogi.fxml"), "Kirja", null, "");
    }
    
    @FXML private void handleMuokkaaKirjailija() {
        ModalController.showModal(Kirjaloki2GUIController.class.getResource("KirjailijanTiedotDialogi.fxml"), "Kirjailija", null, "");
    }
    
    @FXML private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null); 
        tulostaValitut(tulostusCtrl.getTextArea());

    }
    
    @FXML private void handleTietoja() {
        ModalController.showModal(Kirjaloki2GUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }
    
    //--------------------------------------------------------------------------------------------------------------------
    
    private Kirjaloki kirjaloki;
    private Kirjailija kirjailijaKohdalla;
    private TextArea areaKirjailija = new TextArea();
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa kirjojen tiedot.
     * Alustetaan myös kirjalistan kuuntelija 
     */
    protected void alusta() {
        panelKirjailija.setContent(areaKirjailija);
        areaKirjailija.setFont(new Font("Courier New", 12));
        panelKirjailija.setFitToHeight(true);
        
        chooserKirjailijat.clear();
        chooserKirjailijat.addSelectionListener(e -> naytaKirjailija());
    }

    
    /**
     * Näyttää listasta valitun kirjan tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    protected void naytaKirjailija() {
        kirjailijaKohdalla = chooserKirjailijat.getSelectedObject();

        if (kirjailijaKohdalla == null) return;

        areaKirjailija.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKirjailija)) {
            kirjailijaKohdalla.tulosta(os);
        }
    }
    
    /**
     * Tulostaa kirjailijan tiedot
     * @param os tietovirta johon tulostetaan
     * @param kirjailija tulostettava kirjailija
     */
    public void tulosta(PrintStream os, final Kirjailija kirjailija) {
        os.println("----------------------------------------------");
        kirjailija.tulosta(os);
        os.println("----------------------------------------------");
        List<Kirja> kirjat = kirjaloki.annaKirjat(kirjailija);   
        for (Kirja kir:kirjat)
            kir.tulosta(os); 

    }
    
    /**
     * Tulostaa listassa olevat kirjailijat tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki kirjailijat");
            for (int i = 0; i < kirjaloki.getKirjailijoita(); i++) {
                Kirjailija kirjailija = kirjaloki.annaKirjailija(i);
                tulosta(os, kirjailija);
                os.println("\n\n");
            }
        }
    }



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
    
    
    
    /**
     * Luo uuden kirjailijan jota aletaan editoimaan 
     */
    protected void uusiKirjailija() {
        Kirjailija uusi = new Kirjailija();
        uusi.rekisteroi();
        uusi.vastaaKytomaki();
        try {
            kirjaloki.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia kirjailijan lisäämisessä " + e.getMessage());
            return;
        }
        hae(uusi.getKirjailijaId());
    }
    
    /** 
     * Tekee uuden tyhjän kirjan editointia varten 
     */ 
    public void uusiKirja() { 
        if ( kirjailijaKohdalla == null ) return;  
        Kirja kirja = new Kirja();  
        kirja.rekisteroi();  
        kirja.vastaaMargarita(kirjailijaKohdalla.getKirjailijaId());  
        try {
            kirjaloki.lisaa(kirja);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia kirjan lisäämisessä " + e.getMessage());
            return;
        }  
        hae(kirjailijaKohdalla.getKirjailijaId());          
    } 

    
    /**
     * Hakee kirjailijoiden tiedot listaan
     * @param jnro kirjailijan numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {
        chooserKirjailijat.clear();

        int index = 0;
        for (int i = 0; i < kirjaloki.getKirjailijoita(); i++) {
            Kirjailija kirjailija = kirjaloki.annaKirjailija(i);
            if (kirjailija.getKirjailijaId() == jnro) index = i;
            chooserKirjailijat.add(kirjailija.getNimi(), kirjailija);
        }
        chooserKirjailijat.setSelectedIndex(index); 
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

    /**
     * @param kirjaloki käsiteltävä kirjalokin käyttöliittymässä
     */
    public void setKirjaloki(Kirjaloki kirjaloki) {
        this.kirjaloki = kirjaloki;
        naytaKirjailija();
    }

}