package kirjaloki;

import java.util.*;

/**
 * 
 * @author heta
 * @version 19.2.2026
 *
 */
public class Kirjat implements Iterable<Kirja> {

    private String                      tiedostonNimi = "";

    /** taulukko kirjoista */
    private final Collection<Kirja> alkiot        = new ArrayList<Kirja>();


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
    }


    /**
     * Lukee käyttäjien tiedostosta.  
     * TODO Kesken
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".kirja1";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa jäsenistön tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
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
     * Haetaan kaikki jäsen Kirjat
     * @param tunnusnro jäsenen tunnusnumero jolle kirja1rastuksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin kirja1rastuksiin
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
    public List<Kirja> annaKirjat(int tunnusnro) {
        List<Kirja> loydetyt = new ArrayList<Kirja>();
        for (Kirja kirja1 : alkiot)
            if (kirja1.getKirjailijaId() == tunnusnro) loydetyt.add(kirja1);
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
