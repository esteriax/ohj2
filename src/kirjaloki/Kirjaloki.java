package kirjaloki;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author heta
 * @version 17.2.2026
 *
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import kirjaloki.SailoException;
 *  private Kirjaloki kirjaloki;
 *  private Kirjailija kytomaki1;
 *  private Kirjailija kytomaki2;
 *  private int jid1;
 *  private int jid2;
 *  private Kirja margarita21;
 *  private Kirja margarita11;
 *  private Kirja margarita22; 
 *  private Kirja margarita12; 
 *  private Kirja margarita23;
 *  
 *  @SuppressWarnings("javadoc")
 *  public void alustaKirjaloki() {
 *    kirjaloki = new Kirjaloki();
 *    kytomaki1 = new Kirjailija(); kytomaki1.vastaaKytomaki(); kytomaki1.rekisteroi();
 *    kytomaki2 = new Kirjailija(); kytomaki2.vastaaKytomaki(); kytomaki2.rekisteroi();
 *    jid1 = kytomaki1.getTunnusNro();
 *    jid2 = kytomaki2.getTunnusNro();
 *    margarita21 = new Kirja(jid2); margarita21.vastaaMargarita(jid2);
 *    margarita11 = new Kirja(jid1); margarita11.vastaaMargarita(jid1);
 *    margarita22 = new Kirja(jid2); margarita22.vastaaMargarita(jid2); 
 *    margarita12 = new Kirja(jid1); margarita12.vastaaMargarita(jid1); 
 *    margarita23 = new Kirja(jid2); margarita23.vastaaMargarita(jid2);
 *    try {
 *    kirjaloki.lisaa(kytomaki1);
 *    kirjaloki.lisaa(kytomaki2);
 *    kirjaloki.lisaa(margarita21);
 *    kirjaloki.lisaa(margarita11);
 *    kirjaloki.lisaa(margarita22);
 *    kirjaloki.lisaa(margarita12);
 *    kirjaloki.lisaa(margarita23);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
*/
public class Kirjaloki {
    
    private Kirjailijat kirjailijat = new Kirjailijat();
    private Kirjat kirjat = new Kirjat();
    
    
        /** 
         * Palauttaa "taulukossa" hkytomakiehtoon vastaavien kirjailijoiden viitteet 
         * @param hakuehto ehto jonka mukaan haetaan
         * @param k etsittävän kentän indeksi  
         * @return tietorakenteen löytyneistä kirjailijoista 
         * @throws SailoException Jos jotakin menee väärin
         * @example 
         * <pre name="test">
         *   #THROWS CloneNotSupportedException, SailoException
         *   alustaKirjaloki();
         *   // TODO: tee testit kun etsi on korjattu
         * </pre>
         */ 
        public Collection<Kirjailija> etsi(String hakuehto, int k) throws SailoException { 
            return kirjailijat.etsi(hakuehto, k); 
        } 

        /**
         * Asettaa tiedostojen perusnimet
         * @param nimi uusi tiedoston nimi
         */
        public void setTiedosto(String nimi) {
            File dir = new File(nimi);
            dir.mkdirs();
            String hakemistonNimi = "";
            if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
            kirjailijat.setTiedostonPerusNimi(hakemistonNimi + "kirjailijat");
            kirjat.setTiedostonPerusNimi(hakemistonNimi + "kirjat");
        }
        
        /**
         * Haetaan kaikki kirjailijan kirjat
         * @param kirjailija jolle kirjoja haetaan
         * @return tietorakenne jossa viiteet löydetteyihin kirjoihin
         * @example
         * <pre name="test">
         * #import java.util.*;
         *  alustaKirjaloki();
         *  Kirjailija kytomaki3 = new Kirjailija();
         *  kytomaki3.rekisteroi();
         *  kirjaloki.lisaa(kytomaki3);
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
         * @example
          <pre name="test">
         *  #THROWS SailoException  
         *  alustaKirjaloki();
         *  kirjaloki.etsi("*",0).size() === 2;
         *  kirjaloki.lisaa(kytomaki1);
         *  kirjaloki.etsi("*",0).size() === 3;
         */
        public void lisaa(Kirjailija kirjailija) throws SailoException {
            kirjailijat.lisaa(kirjailija);
        }
        
        /**
         * Lisätään kirja kirjalokiin
         * @param kirja lisättävä kirja
         * @throws SailoException mikäli lisääminen epäonnistuu
         */
        public void lisaa(Kirja kirja) throws SailoException {
            kirjat.lisaa(kirja);
        }

        /**
         * Lukee kirjalokin tiedot tiedostosta
         * @param nimi tiedoston jota käyteään lukemisessa
         * @throws SailoException jos lukeminen epäonnistuu
         * * <pre name="test">
         * #THROWS SailoException 
         * #import java.io.*;
         * #import java.util.*;
         *   
         *  String hakemisto = "testiheta";
         *  File dir = new File(hakemisto);
         *  File ftied  = new File(hakemisto+"/nimet.dat");
         *  File fhtied = new File(hakemisto+"/kirjat.dat");
         *  dir.mkdir();  
         *  ftied.delete();
         *  fhtied.delete();
         *  kirjaloki = new Kirjaloki(); // tiedostoja ei ole, tulee poikkeus 
         *  kirjaloki.lueTiedostosta(hakemisto); #THROWS SailoException
         *  alustaKirjaloki();
         *  kirjaloki.setTiedosto(hakemisto); // nimi annettava koska uusi poisti sen
         *  kirjaloki.tallenna(); 
         *  kirjaloki = new Kirjaloki();
         *  kirjaloki.lueTiedostosta(hakemisto);
         *  Collection<Kirjailija> kaikki = kirjaloki.etsi("",-1); 
         *  Iterator<Kirjailija> it = kaikki.iterator();
         *  it.next() === kytomaki1;
         *  it.next() === kytomaki2;
         *  it.hasNext() === false;
         *  List<Kirja> loytyneet = kirjaloki.annaKirjat(kytomaki1);
         *  Iterator<Kirja> ih = loytyneet.iterator();
         *  ih.next() === margarita2;
         *  ih.next() === margarita4;
         *  ih.hasNext() === false;
         *  loytyneet = kirjaloki.annaKirjat(kytomaki2);
         *  ih = loytyneet.iterator();
         *  ih.next() === margarita1;
         *  ih.next() === margarita3;
         *  ih.next() === margarita5;
         *  ih.hasNext() === false;
         *  kirjaloki.lisaa(kytomaki2);
         *  kirjaloki.lisaa(margarita5);
         *  kirjaloki.tallenna(); // tekee molemmista .bak
         *  ftied.delete()  === true;
         *  fhtied.delete() === true;
         *  File fbak = new File(hakemisto+"/nimet.bak");
         *  File fhbak = new File(hakemisto+"/kirjat.bak");
         *  fbak.delete() === true;
         *  fhbak.delete() === true;
         *  dir.delete() === true;
         * </pre>
         */
        public void lueTiedostosta(String nimi) throws SailoException {
            kirjailijat = new Kirjailijat(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
            kirjat = new Kirjat();

            setTiedosto(nimi);
            kirjailijat.lueTiedostosta();
            kirjat.lueTiedostosta();
        }
        
        /**
         * Tallenttaa kirjalokin tiedot tiedostoon.  
         * Vaikka kirjailijoiden tallettamien epäonistuisi, niin yritetään silti tallettaa
         * kirjoja ennen poikkeuksen heittämistä.
         * @throws SailoException jos tallettamisessa ongelmia
         */
        public void tallenna() throws SailoException {
            String virhe = "";
            try {
                kirjailijat.tallenna();
            } catch ( SailoException ex ) {
                virhe = ex.getMessage();
            }

            try {
                kirjat.tallenna();
            } catch ( SailoException ex ) {
                virhe += ex.getMessage();
            }
            if ( !"".equals(virhe) ) throw new SailoException(virhe);
        }
        
        /** 
         * Korvaa kirjailijan tietorakenteessa.  Ottaa kirjailijan omistukseensa. 
         * Etsitään samalla tunnusnumerolla oleva kirjailija.  Jos ei löydy, 
         * niin lisätään uutena kirjailijana. 
         * @param kirjailija lisätäävän kirjailijan viite.  Huom tietorakenne muuttuu omistajaksi 
         * @throws SailoException jos tietorakenne on jo täynnä 
         * @example
         * <pre name="test">
         * #THROWS SailoException  
         *  alustaKirjaloki();
         *  kirjaloki.etsi("*",0).size() === 2;
         *  kirjaloki.Lisaa(kytomaki1);
         *  kirjaloki.etsi("*",0).size() === 2;
         * </pre>
         */ 
        public void korvaaTaiLisaa(Kirjailija kirjailija) throws SailoException { 
            kirjailijat.korvaaTaiLisaa(kirjailija); 
        } 

        /** 
         * Korvaa kiran tietorakenteessa.  Ottaa kirjan omistukseensa. 
         * Etsitään samalla tunnusnumerolla oleva kirja.  Jos ei löydy, 
         * niin lisätään uutena kirjana. 
         * @param kirja lisättävän kirjan viite.  Huom tietorakenne muuttuu omistajaksi 
         * @throws SailoException jos tietorakenne on jo täynnä 
         */ 
        public void korvaaTaiLisaa(Kirja kirja) throws SailoException { 
            kirjat.korvaaTaiLisaa(kirja); 
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
                
                int id1 = kytomaki1.getKirjailijaId();
                int id2 = kytomaki2.getKirjailijaId();
                Kirja kirja11 = new Kirja(id1);
                kirja11.vastaaMargarita(id1);
                kirjaloki.lisaa(kirja11);
                Kirja kirja12 = new Kirja(id1);
                kirja12.vastaaMargarita(id1);
                kirjaloki.lisaa(kirja12);
                Kirja kirja21 = new Kirja(id2);
                kirja21.vastaaMargarita(id2);
                kirjaloki.lisaa(kirja21);
                Kirja kirja22 = new Kirja(id2);
                kirja22.vastaaMargarita(id2);
                kirjaloki.lisaa(kirja22);
                Kirja kirja23 = new Kirja(id2);
                kirja23.vastaaMargarita(id2);
                kirjaloki.lisaa(kirja23);


                System.out.println("============= Kirjaloki testi =================");

                Collection<Kirjailija> kirjailijat = kirjaloki.etsi("", -1);
                int i = 0;
                for (Kirjailija kirjailija: kirjailijat) {
                    System.out.println("Kirjailija paikassa: " + i);
                    kirjailija.tulosta(System.out);
                    List<Kirja> loytyneet = kirjaloki.annaKirjat(kirjailija);
                    for (Kirja kirja : loytyneet)
                        kirja.tulosta(System.out);
                    i++;
                }

            } catch (SailoException ex) {
                System.out.println(ex.getMessage());
            }
        }  

    }


