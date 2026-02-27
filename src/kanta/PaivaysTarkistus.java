package kanta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Luokka päiväyksiä sisältävien TextFieldien oikeellisuuden tarkistamiseen
 * @author heta
 * @version 17.2.2026
 *
 */
public class PaivaysTarkistus {
    
    
    /**
     * @param lukuPvm tarkistettava päivämäärä
     * @return true jos tarkastus meni läpi, false jos ei
     * <pre name="test">
     *  Boolean pvm1 = tarkistaLukuPvm("1.1.2020"); Boolean pvm2 = tarkistaLukuPvm("1.12.2020");
     *  Boolean pvm3 = tarkistaLukuPvm("11.1.2020"); Boolean pvm4 = tarkistaLukuPvm("1/1/2020");
     *  Boolean pvm5 = tarkistaLukuPvm("2020"); Boolean pvm6 = tarkistaLukuPvm(".12"); 
     *  Boolean pvm7 = tarkistaLukuPvm(" "); Boolean pvm8 = tarkistaLukuPvm("1.2020"); Boolean pvm9 = tarkistaLukuPvm("1");
     *  pvm1 === true; pvm2 === true; pvm3 === true;
     *  pvm4 === false; pvm5 === false; pvm6 === false;
     *  pvm7 === false; pvm8 === false; pvm9 === false;
     * </pre>
     */
    public static boolean tarkistaLukuPvm(String lukuPvm) {
        DateTimeFormatter muoto = DateTimeFormatter
                .ofPattern("d.M.uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(lukuPvm, muoto);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Tarkastaa vuosiluvun oikeinkirjoituksen. Vuosiluvun tulee olla suurempi kuin 600.
     * @param vuosi tarkistettava vuosiluku
     * @return true jos tarkastus meni läpi, false jos ei
     */
    public static boolean tarkistaVuosi(String vuosi) {
        int tamaVuosi = LocalDateTime.now().getYear();
        try {
            int v = Integer.parseInt(vuosi);;
            return v >= 600 && v <= tamaVuosi;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Tarkastaa vuosiluvun oikeinkirjoituksen. Vuosiluvun tulee olla suurempi kuin 600.
     * @param vuosi tarkistettava vuosiluku
     * @return true jos tarkastus meni läpi, false jos ei
     */
    public static boolean tarkistaVuosi(int vuosi) {
        int tamaVuosi = LocalDateTime.now().getYear();
        try {
            return vuosi >= 600 && vuosi <= tamaVuosi;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Testataan luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        int tamaVuosi = LocalDateTime.now().getYear();
        String vuosi = "2222";
        
        System.out.println(tarkistaVuosi(tamaVuosi));
        System.out.println(tarkistaVuosi(vuosi));
    }

}
