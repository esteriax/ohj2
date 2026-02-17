package kirjaloki;

/**
 * @author heta
 * @version 17.2.2026
 *
 */
/**
 * Kirjaloki-luokka
 * @author heta
 * @version 13.2.2026
 *
 */
public class Kirjaloki {
    
    private final Kirjat kirjat = new Kirjat();
    
    
        /**
         * Pyyntimetodi kirjojen lukumäärälle
         * @param numero kirjan järjestysnumero kirjalokissa
         * @return indeksissä numero sijaitseva kirja
         */
        public Kirja annaKirja(int numero) {
        return kirjat.anna(numero);
        }
        
        /**
         * Ge
         * @return kirjojen lukumäärä
         */
        public int getKirjoja() {
        return kirjat.getLkm();
        }
        /**
         * 
         * @param kirja kirjalokiin lisättävä kirja
         * @throws SailoException näytettävä virhe, mikäli lisääminen ei onnistu
         * * @example
         * <pre name="test">
         * #THROWS SailoException
         * Kirjaloki kirjaloki = new Kirjaloki();
         * Kirja margarita1 = new Kirja(), margarita2 = new Kirja();
         * margarita1.rekisteroi(); margarita2.rekisteroi();
         * kirjaloki.getKirjoja() === 0;
         * kirjaloki.lisaa(margarita1); kirjaloki.getKirjoja() === 1;
         * kirjaloki.lisaa(margarita2); kirjaloki.getKirjoja() === 2;
         * kirjaloki.lisaa(margarita1); kirjaloki.getKirjoja() === 3;
         * kirjaloki.getKirjoja() === 3;
         * kirjaloki.annaKirja(0) === margarita1;
         * kirjaloki.annaKirja(1) === margarita2;
         * kirjaloki.annaKirja(2) === margarita1;
         * kirjaloki.annaKirja(3) === margarita1; #THROWS IndexOutOfBoundsException 
         * kirjaloki.lisaa(margarita1); kirjaloki.getKirjoja() === 4;
         * kirjaloki.lisaa(margarita1); kirjaloki.getKirjoja() === 5;
         * kirjaloki.lisaa(margarita1);            #THROWS SailoException
         * </pre>
         * */
        public void lisaa(Kirja kirja) throws SailoException {
            kirjat.lisaa(kirja);
        }

        /**
         * Lukee kirjalokin tiedot tiedostosta
         * @param nimi jota käyteään lukemisessa
         * @throws SailoException jos lukeminen epäonnistuu
         */
        public void lueTiedostosta(String nimi) throws SailoException {
            kirjat.lueTiedostosta(nimi);
        }
        
        /**
         * Tallettaa kirjalokin tiedot tiedostoon
         * @throws SailoException jos tallettamisessa ongelmia
         */
        public void talleta() throws SailoException {
            kirjat.talleta();
        }




        /**
         * @param args ei käytössä
         */
        public static void main(String[] args) {
    
            Kirjaloki kirjaloki = new Kirjaloki();
    

            try {
                //kirjaloki.lueTiedostosta("Heta");

                Kirja margarita1 = new Kirja(), margarita2 = new Kirja();
                margarita1.rekisteroi();
                margarita1.vastaaMargarita();
                margarita2.rekisteroi();
                margarita2.vastaaMargarita();

                kirjaloki.lisaa(margarita1);
                kirjaloki.lisaa(margarita2);

                System.out.println("============= Kirjaloki testi =================");

                for (int i = 0; i < kirjaloki.getKirjoja(); i++) {
                    Kirja kirja = kirjaloki.annaKirja(i);
                    System.out.println("Kirja paikassa: " + i);
                    kirja.tulosta(System.out);
                }

            } catch (SailoException ex) {
                System.out.println(ex.getMessage());
            }
        }  

    }


