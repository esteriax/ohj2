/**
 * 
 */
package kirjaloki;
import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.PaivaysTarkistus;
import kanta.Tietue;



/**
 * Kirjalokin Kirja-luokka
 * @author heta
 */
public class Kirja implements Cloneable, Tietue {
    
    private int kirjaId;
    private int kirjailijaId;
    private String nimi = "";
    //private String kirjailija = "";
    private String julkaisuvuosi;
    private String genre = "";
    private int tahdet;
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
     * @return kirjan kenttien lukumäärä
     */
    @Override
    public int getKenttia() {
        return 8;
    }


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 2;
    }
    

    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */
    @Override
    public String getKysymys(int k) {
        switch (k) {
            case 0:
                return "Kirjaid";
            case 1:
                return "KirjailijaId";
            case 2:
                return "Nimi";
            case 3:
                return "Julkaisuvuosi";
            case 4:
                return "Genre";
            case 5:
                return "Tähdet";
            case 6:
                return "Luettu";
            case 7:
                return "Lisätiedot";
            default:
                return "";
        }
    }


    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Kirja har = new Kirja();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   har.anna(0) === "2";   
     *   har.anna(1) === "10";   
     *   har.anna(2) === "Kalastus";   
     *   har.anna(3) === "1949";   
     *   har.anna(4) === "22";   
     *   
     * </pre>
     */
    @Override
    public String anna(int k) {
        switch (k) {
            case 0:
                return "" + kirjaId;
            case 1:
                return "" + kirjailijaId;
            case 2:
                return "" + nimi;
            case 3:
                return "" + julkaisuvuosi;
            case 4:
                return "" + genre;
            case 5:
                return "" + tahdet;
            case 6:
                return "" + lukupvm;
            case 7:
                return "" + lisatiedot;
            default:
                return "";
        }
    }


    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   har.aseta(3,"kissa") === "Aloitusvuosi väärin jono = \"kissa\"";
     *   har.aseta(3,"1940")  === null;
     *   har.aseta(4,"kissa") === "Viikkotunnit väärin jono = \"kissa\"";
     *   har.aseta(4,"20")    === null;
     *   
     * </pre>
     */
    @Override
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setKirjaId(Mjonot.erota(sb, '$', getKirjaId()));
                return null;
            case 1:
                kirjailijaId = Mjonot.erota(sb, '$', kirjailijaId);
                return null;
            case 2:
                nimi = st;
                return null;
            case 3:
                PaivaysTarkistus vuosi = new PaivaysTarkistus();
                try {
                    if (vuosi.tarkistaVuosi(st) == false) return "Tarkista julkaisuvuosi";
                    julkaisuvuosi = st;
                    return null;
                } catch ( NumberFormatException ex ) {
                    return "Julkaisuvuosi väärin " + ex.getMessage();
                }
            case 4:
                genre = st;
                return null;
            case 5:
                tahdet = Mjonot.erota(sb, '$', tahdet);
                return null;
            case 6:
                lukupvm = st;
                return null;
            case 7:
                lisatiedot = st;
                return null;

            default:
                return "Väärä kentän indeksi";
        }
    }


    /**
     * Tehdään identtinen klooni jäsenestä
     * @return Object kloonattu jäsen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Kirja har = new Kirja();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Kirja kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(har.toString()) === false;
     * </pre>
     */
    @Override
    public Kirja clone() throws CloneNotSupportedException { 
        return (Kirja)super.clone();
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
       StringBuffer sb = new StringBuffer("");
       String erotin = "";
       for (int k = 0; k < getKenttia(); k++) {
           sb.append(erotin);
           sb.append(anna(k));
           erotin = "|";
       }
       return sb.toString();
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
       for (int k = 0; k < getKenttia(); k++)
           aseta(k, Mjonot.erota(sb, '|'));

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
            //kirjailija = "Anni Kytömäki";
            julkaisuvuosi = "2020";
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
            //kirjailija = "Anni Kytömäki";
            julkaisuvuosi = "2020";
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