/**
 * 
 */
package kirjaloki;

/**
 * Poikkeusluokka tietoakenteesta eiheutuville poikkeuksille
 */
public class SailoException extends Exception {
	
	private static final long serialVersionUID = 1L;


	/** Poikkeuksen muodostaja jolle tuodaan poikkeuksessa käytettävä viesti
	 * @param message poikkeuksen viesti
	 */
	public SailoException(String message) {
		super(message);
	}

	
}
