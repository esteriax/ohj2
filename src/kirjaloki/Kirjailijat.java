/**
 * 
 */
package kirjaloki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author heta
 * @version 19.2.2026
 *
 */
public class Kirjailijat implements Iterable<Kirjailija>{

    private static final int MAX_KIRJAILIJOITA   = 5;
    private int              lkm           = 0;
    private Kirjailija            alkiot[]      = new Kirjailija[MAX_KIRJAILIJOITA];
    private boolean muutettu = false;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "nimet";
    
    
    /**
     * Palauttaa viitteen kirjailijaan indeksissä i.
     * @param i monennenko kirjailijan viite halutaan
     * @return viite kirjailijaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Kirjailija anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    
    /**
     * @return Kirjailijoiden lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * Lisää uuden kirjan tietorakenteeseen.  Ottaa kirjan omistukseensa.
     * @param kirjailija lisätäävän kirja viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Kirjailijat kirjailijat = new Kirjailijat();
     * Kirjailija kytomaki = new Kirjailija(), kytomaki2 = new Kirjailija();
     * kirjailijat.getLkm() === 0;
     * kirjailijat.lisaa(kytomaki); kirjailijat.getLkm() === 1;
     * kirjailijat.lisaa(kytomaki2); kirjailijat.getLkm() === 2;
     * kirjailijat.lisaa(kytomaki); kirjailijat.getLkm() === 3;
     * kirjailijat.anna(0) === kytomaki;
     * kirjailijat.anna(1) === kytomaki2;
     * kirjailijat.anna(2) === kytomaki;
     * kirjailijat.anna(1) == kytomaki === false;
     * kirjailijat.anna(1) == kytomaki2 === true;
     * kirjailijat.anna(3) === kytomaki; #THROWS IndexOutOfBoundsException 
     * kirjailijat.lisaa(kytomaki); kirjailijat.getLkm() === 4;
     * kirjailijat.lisaa(kytomaki); kirjailijat.getLkm() === 5;
     * kirjailijat.lisaa(kytomaki);  #THROWS SailoException
     * </pre>
     */

    public void lisaa(Kirjailija kirjailija) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = kirjailija;
        lkm++;
        muutettu = true;

        
    }
    
    /**
     * Lukee käyttäjien tiedostosta. 
     * @param tiedosto tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Kirjailijat kirjailijat = new Kirjailijat();
     *  Kirjailija kytomaki1 = new Kirjailija(), kytomaki2 = new Kirjailija();
     *  kytomaki1.vastaaKytomaki();
     *  kytomaki2.vastaaKytomaki();
     *  String hakemisto = "testiheta";
     *  String tiedNimi = hakemisto + "/nimet";
     *  File ftied = new File(tiedNimi + ".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  kirjailijat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kirjailijat.lisaa(kytomaki1);
     *  kirjailijat.lisaa(kytomaki2);
     *  kirjailijat.tallenna();
     *  kirjailijat = new Kirjailijat();            // Poistetaan vanhat luomalla uusi
     *  kirjailijat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Kirjailija> i = kirjailijat.iterator();
     *  i.next() === kytomaki1;
     *  i.next() === kytomaki2;
     *  i.hasNext() === false;
     *  kirjailijat.lisaa(kytomaki2);
     *  kirjailijat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusNimi(tiedosto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Kerhon nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
            // int maxKoko = Mjonot.erotaInt(rivi,10); // tehdään jotakin

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Kirjailija kirjailija = new Kirjailija();
                kirjailija.parse(rivi); // voisi olla virhekäsittely
                lisaa(kirjailija);
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
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    

    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    

    /**
     * Palauttaa kirjalokin koko nimen
     * @return kirjalokin koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }


    /**
     * Tallentaa kirjalokin tiedostoon 
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (Kirjailija kirjailija : this) {
                fo.println(kirjailija.toString());
            }
            //} catch ( IOException e ) { // ei heitä poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }

    /**
     * Luokka kirjailijaten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Kirjailijat kirjailijat = new Kirjailijat();
     * Kirjailija kytomaki1 = new Kirjailija(), kytomaki2 = new Kirjailija();
     * kytomaki1.rekisteroi(); kytomaki2.rekisteroi();
     *
     * kirjailijat.lisaa(kytomaki1); 
     * kirjailijat.lisaa(kytomaki2); 
     * kirjailijat.lisaa(kytomaki1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Kirjailija kirjailija:kirjailijat)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+kirjailija.vastaaKirjailijaId());           
     * 
     * String tulos = " " + kytomaki1.vastaaKirjailijaId() + " " + kytomaki2.vastaaKirjailijaId() + " " + kytomaki1.vastaaKirjailijaId();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Kirjailija>  i=kirjailijat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Kirjailija kirjailija = i.next();
     *   ids.append(" " + kirjailija.vastaaKirjailijaId());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Kirjailija>  i=kirjailijat.iterator();
     * i.next() == kytomaki1  === true;
     * i.next() == kytomaki2  === true;
     * i.next() == kytomaki1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class KirjailijaetIterator implements Iterator<Kirjailija> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa kirjailijatä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä kirjailijaiä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava kirjailija
         * @return seuraava kirjailija
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Kirjailija next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori kirjailijaistään.
     * @return kirjailija iteraattori
     */
    @Override
    public Iterator<Kirjailija> iterator() {
        return new KirjailijaetIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien kirjailijaten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä kirjailijaistä 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Kirjailijat kirjailijat = new Kirjailijat(); 
     *   Kirjailija kirjailija1 = new Kirjailija(); kirjailija1.parse("1|Ankka Aku|030201-115H|Paratiisitie 13|"); 
     *   Kirjailija kirjailija2 = new Kirjailija(); kirjailija2.parse("2|Ankka Tupu||030552-123B|"); 
     *   Kirjailija kirjailija3 = new Kirjailija(); kirjailija3.parse("3|Susi Sepe|121237-121V||131313|Perämetsä"); 
     *   Kirjailija kirjailija5 = new Kirjailija(); kirjailija5.parse("4|Ankka Iines|030245-115V|Ankkakuja 9"); 
     *   Kirjailija kirjailija4 = new Kirjailija(); kirjailija4.parse("5|Ankka Roope|091007-408U|Ankkakuja 12"); 
     *   kirjailijat.lisaa(kirjailija1); kirjailijat.lisaa(kirjailija2); kirjailijat.lisaa(kirjailija3); kirjailijat.lisaa(kirjailija5); kirjailijat.lisaa(kirjailija4);
     *   // TODO: toistaiseksi palauttaa kaikki kirjailijat 
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Kirjailija> etsi(String hakuehto, int k) { 
        Collection<Kirjailija> loytyneet = new ArrayList<Kirjailija>(); 
        for (Kirjailija kirjailija : this) { 
            loytyneet.add(kirjailija);  
        } 
        return loytyneet; 
    }

    /**
     * Testataan ohjelmaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjailijat kirjailijat = new Kirjailijat();

        Kirjailija kytomaki = new Kirjailija(), kytomaki2 = new Kirjailija();
        kytomaki.rekisteroi();    kytomaki.vastaaKytomaki();
        kytomaki2.rekisteroi();   kytomaki2.vastaaKytomaki();

        try {
            kirjailijat.lisaa(kytomaki);
            kirjailijat.lisaa(kytomaki2);

            System.out.println("========== kirjailijat testi ==============");

            for (int i = 0; i < kirjailijat.getLkm(); i++) {
                Kirjailija kirjailija = kirjailijat.anna(i);
                System.out.println("Kirjailijan nro: " + i);
                kirjailija.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }

    
    }

}
