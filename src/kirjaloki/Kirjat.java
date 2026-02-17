package kirjaloki;



/**
 * @author heta
 * @version 13.2.2026
 *
 */
public class Kirjat {
    
    private static final int MAX_KIRJOJA   = 5;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Kirja            alkiot[]      = new Kirja[MAX_KIRJOJA];
    
    
    /**
     * Palauttaa viitteen i:teen jäseneen.
     * @param i monennenko kirjan viite halutaan
     * @return viite kirjaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Kirja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    
    /**
     * @return Kirjojen lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * Lisää uuden kirjan tietorakenteeseen.  Ottaa kirjan omistukseensa.
     * @param kirja lisätäävän kirja viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Kirja kirjat = new Kirjat();
     * Kirja margarita = new Kirja(), margarita2 = new Kirja();
     * kirjat.getLkm() === 0;
     * kirjat.lisaa(margarita); kirjat.getLkm() === 1;
     * kirjat.lisaa(margarita2); kirjat.getLkm() === 2;
     * kirjat.lisaa(margarita); kirjat.getLkm() === 3;
     * kirjat.anna(0) === margarita;
     * kirjat.anna(1) === margarita2;
     * kirjat.anna(2) === margarita;
     * kirjat.anna(1) == margarita === false;
     * kirjat.anna(1) == margarita2 === true;
     * kirjat.anna(3) === margarita; #THROWS IndexOutOfBoundsException 
     * kirjat.lisaa(margarita); kirjat.getLkm() === 4;
     * kirjat.lisaa(margarita); kirjat.getLkm() === 5;
     * kirjat.lisaa(margarita);  #THROWS SailoException
     * </pre>
     */

    public void lisaa(Kirja kirja) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = kirja;
        lkm++;

        
    }
    
    /**
     * Lukee jäsenistön tiedostosta TODO
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa kirjalokin tiedostoon TODO JATKA
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Testataan ohjelmaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjat Kirjat = new Kirjat();

        Kirja margarita = new Kirja(), margarita2 = new Kirja();
        margarita.rekisteroi();    margarita.vastaaMargarita();
        margarita2.rekisteroi();   margarita2.vastaaMargarita();

        try {
            Kirjat.lisaa(margarita);
            Kirjat.lisaa(margarita2);

            System.out.println("========== Kirjat testi ==============");

            for (int i=0; i<Kirjat.getLkm(); i++) {
                Kirja Kirja = Kirjat.anna(i);
                System.out.println("Kirja nro: " + i);
                Kirja.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }

    
    }

    
}