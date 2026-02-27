package kirjaloki;
import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Kirjalokin Kirjailija-luokka
 * @author heta
 */
public class Kirjailija {
    
    private String nimi = "";
    private int kirjailijaId;
    private String syntymaVuosi = "";
    private String suosikki = "";
    private String lisatiedot = "";

    private static int seuraavaNro = 1;
    
    
    /**
     * @return kirjailijan nimi
     * @example
     * <pre name="test">
     *   Kirjailija kytomaki = new Kirjailija();
     *   kytomaki.vastaaKytomaki();
     *   kytomaki.getNimi() === "Anni Kytömäki";
     * </pre>
     */
    public String getNimi() {
        return nimi;

    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kirjailijalle.
     */
    public void vastaaKytomaki() {
            nimi= "Anni Kytömäki";
            syntymaVuosi = "1980";
            suosikki = "kyllä";
            lisatiedot = "Kaunokirjallisuuden Finlandia-palkinto vuodelta 2020.";
        }
    
    
    
    /**
     * Tulostetaan kirjailijan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", kirjailijaId) + " " + nimi);
        out.println("Syntymävuosi: " + syntymaVuosi);
        out.println("Oma suosikki: " + suosikki);
        out.println("Lisätiedot: " + lisatiedot);
        out.println();
    }
    
    /**
     * Tulostetaan kirjailijan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));

    }

    
    /**
     * Palauttaa kirjailijan tunnusnumeron.
     * @return kirjailijan tunnusnumeron
     * @example
     * <pre name="test">
     *   Kirjailija kirjailija1 = new Kirjailija();
     *   kirjailija1.getKirjailijaId() === 0;
     *   kirjailija1.rekisteroi();
     *   Kirjailija kirjailija2 = new Kirjailija();
     *   kirjailija2.rekisteroi();
     *   int n1 = kirjailija1.getKirjailijaId();
     *   int n2 = kirjailija2.getKirjailijaId();
     *   n1 === n2-1;
     * </pre>
     */
     public int rekisteroi() {
         kirjailijaId = seuraavaNro;
         seuraavaNro++;
         return kirjailijaId;

        }


     /**
      * Palauttaa kirjailijan id:n.
      * @return kirjailijan id
      */
     public int getKirjailijaId() {
         return kirjailijaId;
        }
     
     /**
      * Asettaa kirjailijaid:n ja samalla varmistaa että
      * seuraava numero on aina suurempi kuin tähän mennessä suurin.
      * @param nr asetettava kirjailijaid
      */
     private void setKirjailijaId(int id) {
         kirjailijaId = id;
         if (kirjailijaId >= seuraavaNro) seuraavaNro = kirjailijaId + 1;
     }
     
     /**
      * Palauttaa kirjailijan tiedot merkkijonona jonka voi tallentaa tiedostoon.
      * @return kirjailija tolppaeroteltuna merkkijonona 
      * @example
      * <pre name="test">
      *   Kirjailija kirjailija = new Kirjailija();
      *   kirjailija.parse("   3  |  Anni Kytömäki   | 1980");
      *   kirjailija.toString().startsWith("3|Anni Kytömäki|1980|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
      * </pre>  
      */
     @Override
     public String toString() {
         return "" +
                 getKirjailijaId() + "|" +
                 nimi + "|" +
                 syntymaVuosi + "|" +
                 suosikki + "|" +
                 lisatiedot + "|";
     }


     /**
      * Selvitää kirjailijan tiedot | erotellusta merkkijonosta
      * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnsNro.
      * @param rivi josta kirjailijan tiedot otetaan
      * 
      * @example
      * <pre name="test">
      *   Kirjailija kirjailija = new Kirjailija();
      *   kirjailija.parse("   3  |  Anni Kytömäki   | 1980");
      *   kirjailija.getKirjailijaId() === 3;
      *   kirjailija.toString().startsWith("3|Anni Kytömäki|1980|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
      *
      *   kirjailija.rekisteroi();
      *   int n = kirjailija.getKirjailijaId();
      *   kirjailija.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
      *   kirjailija.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
      *   kirjailija.getKirjailijaId() === n+20+1;
      *     
      * </pre>
      */
     public void parse(String rivi) {
         StringBuffer sb = new StringBuffer(rivi);
         setKirjailijaId(Mjonot.erota(sb, '|', getKirjailijaId()));
         nimi = Mjonot.erota(sb, '|', nimi);
         syntymaVuosi = Mjonot.erota(sb, '|', syntymaVuosi);
         suosikki = Mjonot.erota(sb, '|', suosikki);
         lisatiedot = Mjonot.erota(sb, '|', lisatiedot);
     }
     
     
     @Override
     public boolean equals(Object kirjailija) {
         if ( kirjailija == null ) return false;
         return this.toString().equals(kirjailija.toString());
     }


     @Override
     public int hashCode() {
         return getKirjailijaId();
     }


    
    /**
     * Testataan Kirjailija-luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Kirjailija kytomaki1 = new Kirjailija();
        Kirjailija kytomaki2 = new Kirjailija();

        kytomaki1.rekisteroi();
        kytomaki2.rekisteroi();
        kytomaki1.vastaaKytomaki();
        kytomaki2.vastaaKytomaki();
        kytomaki1.tulosta(System.out);
        kytomaki2.tulosta(System.out);
         

    }

    
    /**
     * @return kirjailijan syntymävuosi
     */
    public String getSyntymaVuosi() {
        return syntymaVuosi;
    }

    /**
     * 
     * @return onko kirjailija käyttäjän suosikki
     */
    public String getSuosikki() {
        return suosikki;
    }

    /**
     * 
     * @return käyttäjän asettamat lisätiedot kirjailijasta
     */
    public String getLisatiedot() {
        return lisatiedot;
    }

}

