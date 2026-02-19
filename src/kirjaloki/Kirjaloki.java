package kirjaloki;

import java.util.List;

/**
 * @author heta
 * @version 17.2.2026
 *
 */
public class Kirjaloki {
    
    private final Kirjailijat kirjailijat = new Kirjailijat();
    private final Kirjat kirjat = new Kirjat();
    
    
        /**
         * Pyyntimetodi kirjojen lukumäärälle
         * @param numero kirjan järjestysnumero kirjalokissa
         * @return indeksissä numero sijaitseva kirjailija
         */
        public Kirjailija annaKirjailija(int numero) {
            return kirjailijat.anna(numero);
        }
        
        /**
         * Haetaan kaikki kirjailija kirjat
         * @param kirjailija jolle kirjoja haetaan
         * @return tietorakenne jossa viiteet löydetteyihin kirjoihin
         * @example
         * <pre name="test">
         * #import java.util.*;
         * 
         *  Kirjaloki kirjaloki = new Kirjaloki();
         *  Kirjailija kytomaki1 = new Kirjailija(), kytomaki2 = new Kirjailija(), kytomaki3 = new Kirjailija();
         *  kytomaki1.rekisteroi(); kytomaki2.rekisteroi(); kytomaki3.rekisteroi();
         *  int id1 = kytomaki1.getKirjailijaId();
         *  int id2 = kytomaki2.getKirjailijaId();
         *  Kirja margarita11 = new Kirja(id1); kirjaloki.lisaa(margarita11);
         *  Kirja margarita12 = new Kirja(id1); kirjaloki.lisaa(margarita12);
         *  Kirja margarita21 = new Kirja(id2); kirjaloki.lisaa(margarita21);
         *  Kirja margarita22 = new Kirja(id2); kirjaloki.lisaa(margarita22);
         *  Kirja margarita23 = new Kirja(id2); kirjaloki.lisaa(margarita23);
         *  
         *  List<Kirja> loytyneet;
         *  loytyneet = kirjaloki.annaKirjat(kytomaki3);
         *  loytyneet.size() === 0; 
         *  loytyneet = kirjaloki.annaKirjat(kytomaki1);
         *  loytyneet.size() === 2; 
         *  loytyneet.get(0) == margarita11 === true;
         *  loytyneet.get(1) == margarita12 === true;
         *  loytyneet = kirjaloki.annaKirjat(kytomaki2);
         *  loytyneet.size() === 3; 
         *  loytyneet.get(0) == margarita21 === true;
         * </pre> 
         */
        public List<Kirja> annaKirjat(Kirjailija kirjailija) {
            return kirjat.annaKirjat(kirjailija.getKirjailijaId());
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
         * Lisätään kirja kirjalokiin
         * @param kirja kirjalokiin lisättävä kirja
         * @throws SailoException mikäli lisääminen epäonnistuu
         */
        public void lisaa(Kirja kirja) throws SailoException {
            kirjat.lisaa(kirja);
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


