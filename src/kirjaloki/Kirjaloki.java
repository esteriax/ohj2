package kirjaloki;

/**
 * @author heta
 * @version 17.2.2026
 *
 */
public class Kirjaloki {
    
    private final Kirjailijat kirjailijat = new Kirjailijat();
    
    
        /**
         * Pyyntimetodi kirjojen lukumäärälle
         * @param numero kirjan järjestysnumero kirjalokissa
         * @return indeksissä numero sijaitseva kirjailija
         */
        public Kirjailija annaKirjailija(int numero) {
        return kirjailijat.anna(numero);
        }
        
        /**
         * Ge
         * @return kirjojen lukumäärä
         */
        public int getKirjailijoita() {
        return kirjailijat.getLkm();
        }
        /**
         * 
         * @param kirjailija kirjalokiin lisättävä kirjailija
         * @throws SailoException näytettävä virhe, mikäli lisääminen ei onnistu
         * * @example
         * <pre name="test">
         * #THROWS SailoException
         * Kirjaloki kirjaloki = new Kirjaloki();
         * Kirjailija kytomaki1 = new Kirjailija(), kytomaki2 = new Kirjailija();
         * kytomaki1.rekisteroi(); kytomaki2.rekisteroi();
         * kirjaloki.getKirjailijoita() === 0;
         * kirjaloki.lisaa(kytomaki1); kirjaloki.getKirjailijoita() === 1;
         * kirjaloki.lisaa(kytomaki2); kirjaloki.getKirjailijoita() === 2;
         * kirjaloki.lisaa(kytomaki1); kirjaloki.getKirjailijoita() === 3;
         * kirjaloki.getKirjailijoita() === 3;
         * kirjaloki.annaKirjailija(0) === kytomaki1;
         * kirjaloki.annaKirjailija(1) === kytomaki2;
         * kirjaloki.annaKirjailija(2) === kytomaki1;
         * kirjaloki.annaKirjailija(3) === kytomaki1; #THROWS IndexOutOfBoundsException 
         * kirjaloki.lisaa(kytomaki1); kirjaloki.getKirjailijoita() === 4;
         * kirjaloki.lisaa(kytomaki1); kirjaloki.getKirjailijoita() === 5;
         * kirjaloki.lisaa(kytomaki1);            #THROWS SailoException
         * </pre>
         * */
        public void lisaa(Kirjailija kirjailija) throws SailoException {
            kirjailijat.lisaa(kirjailija);
        }

        /**
         * Lukee kirjalokin tiedot tiedostosta
         * @param nimi kirjailijan jota käyteään lukemisessa
         * @throws SailoException jos lukeminen epäonnistuu
         */
        public void lueTiedostosta(String nimi) throws SailoException {
            kirjailijat.lueTiedostosta(nimi);
        }
        
        /**
         * Tallettaa kirjalokin tiedot tiedostoon
         * @throws SailoException jos tallettamisessa ongelmia
         */
        public void talleta() throws SailoException {
            kirjailijat.talleta();
        }




        /** Testataan luokkaa
         * @param args ei käytössä
         */
        public static void main(String[] args) {
    
            Kirjaloki kirjaloki = new Kirjaloki();
    

            try {
                //kirjaloki.lueTiedostosta("Heta");

                Kirjailija kytomaki1 = new Kirjailija(), kytomaki2 = new Kirjailija();
                kytomaki1.rekisteroi();
                kytomaki1.vastaaKytomaki();
                kytomaki2.rekisteroi();
                kytomaki2.vastaaKytomaki();

                kirjaloki.lisaa(kytomaki1);
                kirjaloki.lisaa(kytomaki2);

                System.out.println("============= Kirjaloki testi =================");

                for (int i = 0; i < kirjaloki.getKirjailijoita(); i++) {
                    Kirjailija kirjailija = kirjaloki.annaKirjailija(i);
                    System.out.println("Kirjailija paikassa: " + i);
                    kirjailija.tulosta(System.out);
                }

            } catch (SailoException ex) {
                System.out.println(ex.getMessage());
            }
        }  

    }


