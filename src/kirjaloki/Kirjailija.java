package kirjaloki;
import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.PaivaysTarkistus;
import kanta.Tietue;


/**
 * Kirjalokin Kirjailija-luokka
 * @author heta
 */
public class Kirjailija implements Cloneable, Tietue {
    
    private String nimi = "";
    private int kirjailijaId;
    private String syntymaVuosi = "";
    private String suosikki = "";
    private String lisatiedot = "";

    private static int seuraavaNro = 1;
    
    /**
     * Palauttaa kirjailijan kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    @Override
    public int getKenttia() {
        return 5;
    }


    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return eknn kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 1;
    }


    /**
     * Alustetaan kirjailijan merkkijono-attribuuti tyhjiksi jonoiksi
     * ja tunnusnro = 0.
     */
    public Kirjailija() {
        // Toistaiseksi ei tarvita mitään
    }


    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    @Override
    public String anna(int k) {
        switch ( k ) {
            case 0: return "" + kirjailijaId;
            case 1: return "" + nimi;
            case 2: return "" + syntymaVuosi;
            case 3: return "" + suosikki;
            case 4: return "" + lisatiedot;
            default: return "";
            }
        }
    
    /**
     ** Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Kirjailija kirjailija = new Kirjailija();
     *   kirjailija.aseta(1,"Ankka Aku") === null;
     *   kirjailija.aseta(2,"kissa") =R= "Hetu liian lyhyt"
     *   kirjailija.aseta(2,"030201-1111") === "Tarkistusmerkin kuuluisi olla C"; 
     *   kirjailija.aseta(2,"030201-111C") === null; 
     *   kirjailija.aseta(9,"kissa") === "Liittymisvuosi väärin jono = \"kissa\"";
     *   kirjailija.aseta(9,"1940") === null;
     * </pre>

     */
    @Override
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setKirjailijaId(Mjonot.erota(sb, '§', getKirjailijaId()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2:
            PaivaysTarkistus vuosi = new PaivaysTarkistus();
            try {
                if (vuosi.tarkistaVuosi(tjono) == false) return "Tarkista syntymävuosi";
                syntymaVuosi = tjono;
                return null;
            } catch ( NumberFormatException ex ) {
                return "Syntymävuosi väärin " + ex.getMessage();
            }

        case 3:
            suosikki = tjono;
            return null;
        case 4:
            lisatiedot = tjono;
            return null;
        default:
            return "";
        }
    }
    
    /**
     * Palauttaa k:tta kirjailijan kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys

     */
    @Override
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "KirjailijaId";
        case 1: return "Nimi";
        case 2: return "Syntymävuosi";
        case 3: return "Oma suosikki";
        case 4: return "Lisätiedot";
        default: return "";
        }
    }


    

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
         for (int k = 0; k < getKenttia(); k++)
             aseta(k, Mjonot.erota(sb, '|'));

     }
     
     /**
      * Tutkii onko kirjailijan tiedot samat kuin parametrina tuodun kirjailijanx tiedot
      * @param kirjailija johon verrataan
      * @return true jos kaikki tiedot samat, false muuten
      * @example
      * <pre name="test">
      *   Kirjailija kirjailija1 = new Kirjailija();
      *   kirjailija1.parse("   3  |  Ankka Aku   | 030201-111C");
      *   Kirjailija kirjailija2 = new Kirjailija();
      *   kirjailija2.parse("   3  |  Ankka Aku   | 030201-111C");
      *   Kirjailija kirjailija3 = new Kirjailija();
      *   kirjailija3.parse("   3  |  Ankka Aku   | 030201-115H");
      *   
      *   kirjailija1.equals(kirjailija2) === true;
      *   kirjailija2.equals(kirjailija1) === true;
      *   kirjailija1.equals(kirjailija3) === false;
      *   kirjailija3.equals(kirjailija2) === false;
      * </pre>
      */
     public boolean equals(Kirjailija kirjailija) {
         if ( kirjailija == null ) return false;
         for (int k = 0; k < getKenttia(); k++)
             if ( !anna(k).equals(kirjailija.anna(k)) ) return false;
         return true;
     }

     
     
     @Override
     public boolean equals(Object kirjailija) {
         if ( kirjailija instanceof Kirjailija ) return equals((Kirjailija)kirjailija);
         return false;

     }
     
     /**
      * Tehdään identtinen klooni kirjailijasta
      * @return Object kloonattu kirjailija
      * @example
      * <pre name="test">
      * #THROWS CloneNotSupportedException 
      *   Kirjailija Kirjailija = new Kirjailija();
      *   Kirjailija.parse("   3  |  Ankka Aku   | 123");
      *   Kirjailija kopio = Kirjailija.clone();
      *   kopio.toString() === Kirjailija.toString();
      *   Kirjailija.parse("   4  |  Ankka Tupu   | 123");
      *   kopio.toString().equals(Kirjailija.toString()) === false;
      * </pre>
      */
     @Override
     public Kirjailija clone() throws CloneNotSupportedException {
         Kirjailija uusi;
         uusi = (Kirjailija) super.clone();
         return uusi;
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
}

