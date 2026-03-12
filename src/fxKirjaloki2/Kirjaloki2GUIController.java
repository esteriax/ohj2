package fxKirjaloki2;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyCode;
import kanta.PaivaysTarkistus;
import kirjaloki.Kirja;
import kirjaloki.Kirjailija;
import kirjaloki.Kirjaloki;
import kirjaloki.SailoException;
import static fxKirjaloki2.TietueDialogController.getFieldId; 



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
    @FXML private StringGrid<Kirja> tableKirjat;
    @FXML private GridPane gridKirjailija;
    //private String kirjalokinnimi = "Heta";
    private static Kirja apukirja = new Kirja(); 
    
    @FXML private TextField syntymaVuosi;
    @FXML private TextField lisatiedot;
    @FXML private TextField kirjailijaNimi;
    @FXML private TextField suosikki;
    
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
        hae(0);    
    }
    
    /*
     * Tarkistaa päiväyksen/vuosiluvun oikeinkirjoituksen
     */
    
    @FXML private void handleTarkistaLukuPvm() {
        String ehto = lukuPvm.getText(); 
        PaivaysTarkistus pvm = new PaivaysTarkistus();
        if ( ehto.isEmpty() | pvm.tarkistaLukuPvm(ehto) == true)
            naytaVirhe(null);
        else
            naytaVirhe("Korjaa päivämäärä: " + ehto);         
    }
    
    /*
     * Tarkistaa päiväyksen/vuosiluvun oikeinkirjoituksen
     */
    
    @FXML private void handleTarkistaVuosi() {
        String ehto = julkaisuVuosi.getText(); 
        PaivaysTarkistus vuosi = new PaivaysTarkistus();
        if ( ehto.isEmpty() || vuosi.tarkistaVuosi(ehto))
            naytaVirhe(null);
        else
            naytaVirhe("Korjaa vuosiluku: " + ehto);         
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
        poistaKirja();
    }
    
    @FXML private void handlePoistaKirjailija() {
        poistaKirjailija();
    }
    
    
    @FXML private void handleMuokkaaKirja() {
        ModalController.showModal(Kirjaloki2GUIController.class.getResource("KirjanTiedotDialogi.fxml"), "Kirja", null, "");
    }
    
    
    @FXML private void handleMuokkaaKirjailija() {
        muokkaaKirjailija(kentta);
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
    private String kirjalokinnimi = "Heta";
    private static Kirjailija apukirjailija = new Kirjailija(); 
    private TextField muutokset[];
    private int kentta = 0; 
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa kirjojen tiedot.
     * Alustetaan myös kirjalistan kuuntelija 
     */
    protected void alusta() {
        chooserKirjailijat.clear();
        chooserKirjailijat.addSelectionListener(e -> naytaKirjailija());
        cbKentat.clear(); 
        for (int k = apukirjailija.ekaKentta(); k < apukirjailija.getKenttia(); k++) 
            cbKentat.add(apukirjailija.getKysymys(k), null); 
        cbKentat.getSelectionModel().select(0); 
        
        muutokset = TietueDialogController.luoKentat(gridKirjailija, apukirjailija); 
 
        for (TextField edit: muutokset)  
            if ( edit != null ) {  
                edit.setEditable(false);  
                edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaaKirjailija(getFieldId(e.getSource(),0)); });  
                edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));  
                edit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaKirjailija(kentta);}); 
            }    
     // alustetaan kirjataulukon otsikot 
        int eka = apukirja.ekaKentta(); 
        int lkm = apukirja.getKenttia(); 
        String[] headings = new String[lkm-eka]; 
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = apukirja.getKysymys(k); 
        tableKirjat.initTable(headings); 
        tableKirjat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        tableKirjat.setEditable(false); 
        tableKirjat.setPlaceholder(new Label("Ei vielä kirjoja")); 
         
        // Tämä on vielä huono, ei automaattisesti muutu jos kenttiä muutetaan. 
        tableKirjat.setColumnSortOrderNumber(1); 
        tableKirjat.setColumnSortOrderNumber(2); 
        tableKirjat.setColumnWidth(1, 60); 
        tableKirjat.setColumnWidth(2, 60); 
        
        tableKirjat.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaKirjaa(); } );
        tableKirjat.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaKirjaa();}); 
    }



    
    /**
     * Näyttää listasta valitun kirjailijan tiedot tekstikenttiin. 
     */
    protected void naytaKirjailija() {
        kirjailijaKohdalla = chooserKirjailijat.getSelectedObject();
        if (kirjailijaKohdalla == null) return;
        TietueDialogController.naytaTietue(muutokset, kirjailijaKohdalla);
        naytaKirjat(kirjailijaKohdalla);
     }
    
    /**
     * Näytetään valitun kirjailijan kaikki kirjat taulukossa
     * TODO pitäisi olla try catch-muttei toimi, haittaako?
     * @param kirjailija valittu kirjailija
     */
    private void naytaKirjat(Kirjailija kirjailija) {
        tableKirjat.clear();
        if ( kirjailija == null ) return;
        
        List<Kirja> kirjat = kirjaloki.annaKirjat(kirjailija);
        if ( kirjat.size() == 0 ) return;
        for (Kirja kirja: kirjat)
            naytaKirja(kirja); 

    }

    /**
     * Näyttää kirjan tiedot taulukossa
     * @param kirja joka näytetään
     */
    private void naytaKirja(Kirja kirja) {
        int kenttia = kirja.getKenttia(); 
        String[] rivi = new String[kenttia-kirja.ekaKentta()]; 
        for (int i=0, k=kirja.ekaKentta(); k < kenttia; i++, k++) 
            rivi[i] = kirja.anna(k); 
        tableKirjat.add(kirja,rivi);

    }
    
    /**
     * Avaa kirjailijan muokkausdialogin
     */
    private void muokkaaKirjailija(int k) {
        if ( kirjailijaKohdalla == null ) return; 
        try { 
            Kirjailija kirjailija; 
            kirjailija = TietueDialogController.kysyTietue(null, kirjailijaKohdalla.clone(), k); 
            if ( kirjailija == null ) return; 
            kirjaloki.korvaaTaiLisaa(kirjailija); 
            hae(kirjailija.getKirjailijaId()); 
        } catch (CloneNotSupportedException e) { 
            // 
        } catch (SailoException e) { 
            Dialogs.showMessageDialog(e.getMessage()); 
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
        for (Kirja kirja:kirjat) 
            kirja.tulosta(os);  
    }
    
    /**
     * Tulostaa listassa olevat kirjailijat tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki kirjailijat");
            
            Collection<Kirjailija> kirjailijat = kirjaloki.etsi("", -1); 
            for (Kirjailija kirjailija:kirjailijat) { 
                tulosta(os, kirjailija);
                os.println("\n\n");
            }
        } catch (SailoException ex) { 
            Dialogs.showMessageDialog("Kirjailijan hakemisessa ongelmia! " + ex.getMessage()); 
        }
        
    }


    /**
     * Tallentaa muokatut tiedot
     * @return null jos kaikki meni hyvin, muuten virhe
     */
    private String tallenna() {
        try {
            kirjaloki.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
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
        try {
            Kirjailija uusi = new Kirjailija();
            uusi = TietueDialogController.kysyTietue(null, uusi, 1);  
            if ( uusi == null ) return;
            uusi.rekisteroi();
            kirjaloki.lisaa(uusi);
            hae(uusi.getKirjailijaId());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden kirjailijan luomisessa " + e.getMessage());
            return;
        }

    }
    
    /** 
     * Tekee uuden tyhjän kirjan editointia varten 
     */ 
    private void uusiKirja() { 
        if ( kirjailijaKohdalla == null ) return;  
        try {
            Kirja uusi = new Kirja(kirjailijaKohdalla.getKirjailijaId());
            uusi = TietueDialogController.kysyTietue(null, uusi, 0);
            if ( uusi == null ) return;
            uusi.rekisteroi();
            kirjaloki.lisaa(uusi);
            naytaKirjat(kirjailijaKohdalla); 
            tableKirjat.selectRow(1000);  // järjestetään viimeinen rivi valituksi

        } catch (SailoException e) {
            Dialogs.showMessageDialog("Lisääminen epäonnistui: " + e.getMessage());
        }
    } 
    
    private void muokkaaKirjaa() {
        int r = tableKirjat.getRowNr();
        if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        Kirja har = tableKirjat.getObject();
        if ( har == null ) return;
        int k = tableKirjat.getColumnNr()+har.ekaKentta();
        try {
            har = TietueDialogController.kysyTietue(null, har.clone(), k);
            if ( har == null ) return;
            kirjaloki.korvaaTaiLisaa(har); 
            naytaKirjat(kirjailijaKohdalla); 
            tableKirjat.selectRow(r);  // järjestetään sama rivi takaisin valituksi
        } catch (CloneNotSupportedException  e) { /* clone on tehty */  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }
    
    /**
     * Poistetaan kirjataulukosta valitulla kohdalla oleva kirja. 
     */
    private void poistaKirja() {
        int rivi = tableKirjat.getRowNr();
        if ( rivi < 0 ) return;
        Kirja kirja = tableKirjat.getObject();
        if ( kirja == null ) return;
        kirjaloki.poistaKirja(kirja);
        naytaKirjat(kirjailijaKohdalla);
        int kirjoja = tableKirjat.getItems().size(); 
        if ( rivi >= kirjoja ) rivi = kirjoja -1;
        tableKirjat.getFocusModel().focus(rivi);
        tableKirjat.getSelectionModel().select(rivi);
    }


    /*
     * Poistetaan listalta valittu kirjailija
     */
    private void poistaKirjailija() {
        Kirjailija kirjailija = kirjailijaKohdalla;
        if ( kirjailija == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko kirjailija: " + kirjailija.getNimi(), "Kyllä", "Ei") )
            return;
        kirjaloki.poista(kirjailija);
        int index = chooserKirjailijat.getSelectedIndex();
        hae(0);
        chooserKirjailijat.setSelectedIndex(index);
    }



    
    /**
     * Hakee kirjailijoiden tiedot listaan
     * @param knro kirjailijan numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int knro) {
        int jnro = knro; // jnro kirjailijan numero, joka aktivoidaan haun jälkeen 
        if ( jnro <= 0 ) { 
            Kirjailija kohdalla = kirjailijaKohdalla; 
            if ( kohdalla != null ) jnro = kohdalla.getKirjailijaId(); 
        }
        
        int k = cbKentat.getSelectionModel().getSelectedIndex() + apukirjailija.ekaKentta(); 

        String ehto = hakuehto.getText(); 
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
        
        chooserKirjailijat.clear();

        int index = 0;
        Collection<Kirjailija> kirjailijat;
        try {
            kirjailijat = kirjaloki.etsi(ehto, k);
            int i = 0;
            for (Kirjailija kirjailija:kirjailijat) {
                if (kirjailija.getKirjailijaId() == knro) index = i;
                chooserKirjailijat.add(kirjailija.getNimi(), kirjailija);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Kirjailijan hakemisessa ongelmia " + ex.getMessage());
        }
        chooserKirjailijat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää kirjailijaen

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
     * @param nimi tiedosto joka luetaan
     * @return virhe jos jokin meni pieleen, null jos kaikki meni hyvin
     */
    protected String lueTiedosto(String nimi) {
        kirjalokinnimi = nimi;
        setTitle("Kirjaloki: " + kirjalokinnimi);
        try {
            kirjaloki.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
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