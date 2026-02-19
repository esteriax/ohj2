package kirjaloki;
import java.io.*;


/**
 * Kirjalokin Kirjailija-luokka
 * @author heta
 */
public class Kirjailija {
    
    private String nimi;
    private int kirjailijaId;
    private int syntymaVuosi;
    private boolean suosikki;

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
            syntymaVuosi = 1980;
            suosikki = true;
        }
    
    
    
    /**
     * Tulostetaan kirjailijan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", kirjailijaId) + " " + nimi);
        out.println("Syntymävuosi: " + syntymaVuosi);
        out.println("Onko suosikki: " + suosikki);
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

