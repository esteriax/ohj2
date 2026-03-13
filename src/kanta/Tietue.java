/**
 * 
 */
package kanta;
import kirjaloki.Kirja;

/**
 * Rajapinta tietueelle johon voidaan taulukon avulla rakentaa "attribuutit".
 * @author heta
 * @version 11.3.2026
 * @example
 * <pre name="test">
 * #import kirjaloki.Kirja;
 * </pre>
 *
 */
public interface Tietue {
    

    /**
     * @return tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     *   #import Kirjaloki.Kirja;
     *   Kirja kirja = new Kirja();
     *   kirja.getKenttia() === 5;
     * </pre>
     */
    public abstract int getKenttia();


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   kirja.ekaKentta() === 2;
     * </pre>
     */
    public abstract int ekaKentta();


    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   kirja.getKysymys(2) === "ala";
     * </pre>
     */
    public abstract String getKysymys(int k);


    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   kirja.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   kirja.anna(0) === "2";   
     *   kirja.anna(1) === "10";   
     *   kirja.anna(2) === "Kalastus";   
     *   kirja.anna(3) === "1949";   
     *   kirja.anna(4) === "22";   
     * </pre>
     */
    public abstract String anna(int k);

    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   kirja.aseta(3,"kissa") === "aloitusvuosi: Ei kokonaisluku (kissa)";
     *   kirja.aseta(3,"1940")  === null;
     *   kirja.aseta(4,"kissa") === "h/vko: Ei kokonaisluku (kissa)";
     *   kirja.aseta(4,"20")    === null;
     * </pre>
     */
    public abstract String aseta(int k, String s);


    /**
     * Tehdään identtinen klooni tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Kirja kirja = new Kirja();
     *   kirja.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Object kopio = kirja.clone();
     *   kopio.toString() === kirja.toString();
     *   kirja.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(kirja.toString()) === false;
     *   kopio instanceof Kirja === true;
     * </pre>
     */
    public abstract Tietue clone() throws CloneNotSupportedException;


    /**
     * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tietue tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kirja kirja = new Kirja();
     *   kirja.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   kirja.toString()    =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
     * </pre>
     */
    @Override
    public abstract String toString();


}
