package kirjaloki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 
 * @author heta
 * @version 19.2.2026
 *
 */
public class Kirjat implements Iterable<Kirja> {

    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";

    /** taulukko kirjoista */
    private final Collection<Kirja> alkiot = new ArrayList<Kirja>();


    /**
     * Alustetaan kirjat
     */
    public Kirjat() {
    }


    /**
     * Lisää uuden kirjan kirjat-listaan
     * @param kirja1 lisättävä Kirja
     */
    public void lisaa(Kirja kirja1) {
        alkiot.add(kirja1);
        muutettu = true;
    }


    /**
     *  Lukee kirjat tiedostosta
     * @param tiedosto tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Kirjat kirjat = new Kirjat();
     *  Kirja kirja1 = new Kirja(); kirja1.vastaaMargarita(2);
     *  Kirja kirja2 = new Kirja(); kirja2.vastaaMargarita(1);
     *  Kirja kirja3 = new Kirja(); kirja3.vastaaMargarita(2); 
     *  Kirja kirja4 = new Kirja(); kirja4.vastaaMargarita(1); 
     *  Kirja pitsi23 = new Kirja(); pitsi23.vastaaMargarita(2); 
     *  String tiedNimi = "testiheta";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  kirjat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kirjat.lisaa(kirja1);
     *  kirjat.lisaa(kirja2);
     *  kirjat.lisaa(kirja3);
     *  kirjat.lisaa(kirja4);
     *  kirjat.lisaa(pitsi23);
     *  kirjat.tallenna();
     *  kirjat = new Kirjat();
     *  kirjat.lueTiedostosta(tiedNimi);
     *  Iterator<Kirja> i = kirjat.iterator();
     *  i.next().toString() === kirja1.toString();
     *  i.next().toString() === kirja2.toString();
     *  i.next().toString() === kirja3.toString();
     *  i.next().toString() === kirja4.toString();
     *  i.next().toString() === pitsi23.toString();
     *  i.hasNext() === false;
     *  kirjat.lisaa(pitsi23);
     *  kirjat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusNimi(tiedosto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Kirja kirja = new Kirja();
                kirja.parse(rivi); // voisi olla virhekäsittely
                lisaa(kirja);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    /**
    * Luetaan aikaisemmin annetun nimisestä tiedostosta
    * @throws SailoException jos tulee poikkeus
    */
   public void lueTiedostosta() throws SailoException {
       lueTiedostosta(getTiedostonPerusNimi());
   }
   
   /**
    * Asettaa tiedoston perusnimen ilan tarkenninta
    * @param tied tallennustiedoston perusnimi
    */
   public void setTiedostonPerusNimi(String tied) {
       tiedostonPerusNimi = tied;
   }


   /**
    * Palauttaa tiedoston nimen, jota käytetään tallennukseen
    * @return tallennustiedoston nimi
    */
   public String getTiedostonPerusNimi() {
       return tiedostonPerusNimi;
   }


   /**
    * Palauttaa tiedoston nimen, jota käytetään tallennukseen
    * @return tallennustiedoston nimi
    */
   public String getTiedostonNimi() {
       return tiedostonPerusNimi + ".dat";
   }


   /**
    * Palauttaa varakopiotiedoston nimen
    * @return varakopiotiedoston nimi
    */
   public String getBakNimi() {
       return tiedostonPerusNimi + ".bak";
   }

    /**
     * Tallentaa käyttäjien tiedostoon 
     * @throws SailoException jos talletus epäonnistuu
     */
   public void tallenna() throws SailoException {
       if ( !muutettu ) return;

       File fbak = new File(getBakNimi());
       File ftied = new File(getTiedostonNimi());
       fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
       ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

       try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
           for (Kirja har : this) {
               fo.println(har.toString());
           }
       } catch ( FileNotFoundException ex ) {
           throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
       } catch ( IOException ex ) {
           throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
       }

       muutettu = false;
   }


    /**
     * Palauttaa kerhon Kirjaten lukumäärän
     * @return Kirjaten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien Kirjaten läpikäymiseen
     * @return Kirjaiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Kirjat kirjat = new Kirjat();
     *  Kirja margarita21 = new Kirja(2); kirjat.lisaa(margarita21);
     *  Kirja margarita11 = new Kirja(1); kirjat.lisaa(margarita11);
     *  Kirja margarita22 = new Kirja(2); kirjat.lisaa(margarita22);
     *  Kirja margarita12 = new Kirja(1); kirjat.lisaa(margarita12);
     *  Kirja margarita23 = new Kirja(2); kirjat.lisaa(margarita23);
     * 
     *  Iterator<Kirja> i2=kirjat.iterator();
     *  i2.next() === margarita21;
     *  i2.next() === margarita11;
     *  i2.next() === margarita22;
     *  i2.next() === margarita12;
     *  i2.next() === margarita23;
     *  i2.next() === margarita12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Kirja kirja1:kirjat ) { 
     *    kirja1.getKirjailijaId() === jnrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Kirja> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kaikki kirjailijan kirjat
     * @param kirjailijaId kirjailija jonka kirjoja haetaan
     * @return tietorakenne jossa viiteet löydetteyihin kirjoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kirjat kirjat = new Kirjat();
     *  Kirja margarita21 = new Kirja(2); kirjat.lisaa(margarita21);
     *  Kirja margarita11 = new Kirja(1); kirjat.lisaa(margarita11);
     *  Kirja margarita22 = new Kirja(2); kirjat.lisaa(margarita22);
     *  Kirja margarita12 = new Kirja(1); kirjat.lisaa(margarita12);
     *  Kirja margarita23 = new Kirja(2); kirjat.lisaa(margarita23);
     *  Kirja margarita51 = new Kirja(5); kirjat.lisaa(margarita51);
     *  
     *  List<Kirja> loytyneet;
     *  loytyneet = kirjat.annaKirjat(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kirjat.annaKirjat(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == margarita11 === true;
     *  loytyneet.get(1) == margarita12 === true;
     *  loytyneet = kirjat.annaKirjat(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == margarita51 === true;
     * </pre> 
     */
    public List<Kirja> annaKirjat(int kirjailijaId) {
        List<Kirja> loydetyt = new ArrayList<Kirja>();
        for (Kirja kirja1 : alkiot)
            if (kirja1.getKirjailijaId() == kirjailijaId) loydetyt.add(kirja1);
        return loydetyt;
    }


    /**
     * Testiohjelma kirjailijoille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjat kirjat = new Kirjat();
        Kirja margarita1 = new Kirja();
        margarita1.vastaaMargarita(2);
        Kirja margarita2 = new Kirja();
        margarita2.vastaaMargarita(1);
        Kirja margarita3 = new Kirja();
        margarita3.vastaaMargarita(2);
        Kirja margarita4 = new Kirja();
        margarita4.vastaaMargarita(2);

        kirjat.lisaa(margarita1);
        kirjat.lisaa(margarita2);
        kirjat.lisaa(margarita3);
        kirjat.lisaa(margarita2);
        kirjat.lisaa(margarita4);

        System.out.println("============= Kirjat testi =================");

        List<Kirja> Kirjat2 = kirjat.annaKirjat(2);

        for (Kirja kirja1 : Kirjat2) {
            System.out.print(kirja1.getKirjailijaId() + " ");
            kirja1.tulosta(System.out);
        }

    }

}
