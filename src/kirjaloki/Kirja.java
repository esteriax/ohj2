/**
 * 
 */
package kirjaloki;
import java.io.*;



/**
 * Kirjalokin Kirja-luokka
 * @author heta
 */
public class Kirja {
    
    private int kirjaId;
    private int kirjailijaId;
    private String nimi = "";
    private String kirjailija = "";
    private int julkaisuvuosi = 0;
    private String genre = "";
    private int tahdet = 0;
    private String lukupvm = "";
    private String lisatiedot = "";
    
    
    private static int seuraavaNro = 1;
    
    /**
     * Alustetaan kirja
     */
    public Kirja() {
        // TODO
    }

    
    /**
     * Alustetaan tietyn kirjan harrastus.  
     * @param kirjailijaId jäsenen viitenumero 
     */
    public Kirja(int kirjailijaId) {
        this.kirjailijaId = kirjailijaId;
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kirjalle.
     */
    public void vastaaMargarita() {
            nimi = "Margarita";
            kirjailija = "Anni Kytömäki";
            julkaisuvuosi = 2020;
            genre = "historiallinen romaani";
            tahdet = 5;
            lukupvm = "23.02.2022";
            lisatiedot = "Todellinen helmi, voitti Finlandia-palkinnon";
        }
    
    /**
     * Tulostetaan kirjan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", kirjaId) + " " + nimi);
        out.println("Julkaisuvuosi: " + julkaisuvuosi);
        out.println("Kirjailija: " + kirjailija);
        out.println("Genre: " + genre);
        out.println("Tähdet: " + tahdet + "/5");
        out.println("Luettu: " + lukupvm);
        out.println("Lisätiedot: " + lisatiedot);
        out.println();
    }
    
    /**
     * Tulostetaan kirjan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));

    }
    
    /**
     * Palauttaa kirjan tunnusnumeron.
     * @return kirjan tunnusnumeron
     * @example
     * <pre name="test">
     *   Kirja kirja1 = new Kirja();
     *   kirja1.getKirjaId() === 0;
     *   kirja1.rekisteroi();
     *   Kirja kirja2 = new Kirja();
     *   kirja2.rekisteroi();
     *   int n1 = kirja1.getKirjaId();
     *   int n2 = kirja2.getKirjaId();
     *   n1 === n2-1;
     * </pre>
     */
     public int rekisteroi() {
         kirjaId = seuraavaNro;
         seuraavaNro++;
         return kirjaId;

        }


     /**
      * Palauttaa kirjan id:n
      * @return kirjan tunnusnumero
      */
     public int getKirjaId() {
         return kirjaId;
        }
    
    /**
     * @return kirjailijaId kirjan kirjailijan ID
     * 
     */
    public int getKirjailijaId() {
        return kirjailijaId;

    }
    
    
    /**
     * Testataan Kirja-luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Kirja margarita = new Kirja();
        Kirja margarita2 = new Kirja();

        margarita.rekisteroi();
        margarita2.rekisteroi();
        margarita.vastaaMargarita();
        margarita2.vastaaMargarita();
        margarita.tulosta(System.out);
        margarita2.tulosta(System.out);
         

    }

}
