/**
 * 
 */
package kirjaloki;
import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;



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
    * Asettaa kirjaid:n ja samalla varmistaa että
    * seuraava numero on aina suurempi kuin tähän mennessä suurin.
    * @param nr asetettava kirjaid
    */
   private void setKirjaId(int nr) {
       kirjaId = nr;
       if ( kirjaId >= seuraavaNro ) seuraavaNro = kirjaId + 1;
   }


   /**
    * Palauttaa kirjan tiedot merkkijonona jonka voi tallentaa tiedostoon.
    * @return kirja tolppaeroteltuna merkkijonona 
    * @example
    * <pre name="test">
    *   Kirja kirja = new Kirja();
    *   kirja.parse("   2   |  10  |   Knimistus  | 1949 | 22 t ");
    *   kirja.toString()    === "2|10|Knimistus|1949|22";
    * </pre>
    */
   @Override
   public String toString() {
       return "" + getKirjaId() + "|" + kirjailijaId + "|" + kirjailija + "|" + julkaisuvuosi + "|" + genre + "|" + tahdet + "|" + lukupvm + "|" + lisatiedot;
   }


   /**
    * Selvitää kirjan tiedot | erotellusta merkkijonosta.
    * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
    * @param rivi josta kirjans tiedot otetaan
    * @example
    * <pre name="test">
    *   Kirja kirja = new Kirja();
    *   kirja.parse("   2   |  10  |   Knimistus  | 1949 | 22 t ");
    *   kirja.getJasenNro() === 10;
    *   kirja.toString()    === "2|10|Knimistus|1949|22";
    *   
    *   kirja.rekisteroi();
    *   int n = kirja.getKirjaId();
    *   kirja.parse(""+(n+20));
    *   kirja.rekisteroi();
    *   kirja.getKirjaId() === n+20+1;
    *   kirja.toString()     === "" + (n+20+1) + "|10|Knimistus|1949|22";
    * </pre>
    */
   public void parse(String rivi) {
       StringBuffer sb = new StringBuffer(rivi);
       setKirjaId(Mjonot.erota(sb, '|', getKirjaId()));
       kirjailijaId = Mjonot.erota(sb, '|', kirjailijaId);
       kirjailija = Mjonot.erota(sb, '|', kirjailija);
       julkaisuvuosi = Mjonot.erota(sb, '|', julkaisuvuosi);
       genre = Mjonot.erota(sb, '|', genre);
       tahdet = Mjonot.erota(sb, '|', tahdet);
       lukupvm = Mjonot.erota(sb, '|', lukupvm);
       lisatiedot = Mjonot.erota(sb, '|', lisatiedot);
   }


   @Override
   public boolean equals(Object obj) {
       if ( obj == null ) return false;
       return this.toString().equals(obj.toString());
   }
   

   @Override
   public int hashCode() {
       return kirjaId;
   }
   
    /**
     * Alustetaan tietyn kirjailijan kirja.  
     * @param kirjailijaId kirjailijan viitenumero 
     */
    public Kirja(int kirjailijaId) {
        this.kirjailijaId = kirjailijaId;
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kirjalle.
     * @param kirjailijaTunnus kirjailijan id kenen kirja on kysessä
     */
    public void vastaaMargarita(int kirjailijaTunnus) {
            kirjailijaId = kirjailijaTunnus;
            nimi = "Margarita";
            kirjailija = "Anni Kytömäki";
            julkaisuvuosi = 2020;
            genre = "historiallinen romaani";
            tahdet = 5;
            lukupvm = "23.02.2022";
            lisatiedot = "Todellinen helmi, voitti Finlandia-palkinnon";
        }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kirjalle ilman kirjailijaId:tä
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
     * Pnimiuttaa kirjan tunnusnumeron.
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
      * Pnimiuttaa kirjan id:n
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
        
        Kirja har = new Kirja();
        har.vastaaMargarita(2);
        har.tulosta(System.out);
        
        
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
