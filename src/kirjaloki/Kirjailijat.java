/**
 * 
 */
package kirjaloki;

/**
 * @author heta
 * @version 19.2.2026
 *
 */
public class Kirjailijat {

    private static final int MAX_KIRJAILIJOITA   = 5;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Kirjailija            alkiot[]      = new Kirjailija[MAX_KIRJAILIJOITA];
    
    
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

        
    }
    
    /**
     * Lukee kirjailijoiden tiedostosta TODO
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
