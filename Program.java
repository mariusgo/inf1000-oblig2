/**
 * Class with main method running the program. For this
 * assignment we were to build a simple command based system
 * for a birdwatcher. With the system the user can register
 * new bird observations, print all observations done for a
 * specific bird or for all observations done on a specific
 * location.
 *
 * Observations are stored on data file "fugler.txt" with the
 * following format for each observation per line:
 *
 * meis,F,Jevnaker,mai2013
 *
 * The system language is Norwegian. All read/write to file
 * is done with a package called easyIO, developed by the
 * authors of the INF1000 text book for Autumn 2013: "Rett
 * på java", written by Anders Brunland, Knut Hegna, Ole 
 * Christian Lingjærde, and Arne Maus.
 *
 * @author Marius Olaussen
 */
public class Program {
	private static String rapportfil = "fugler.txt";
	
	/**
	 * The main method creates a new object of the 
	 * Observasjoner (Observations) class and sends this as
	 * parameter to the constructor when creating a new Meny
	 * (Menu) class object.
	 */
  public static void main (String[] args) {
    Observasjoner o = new Observasjoner(rapportfil, 0);
    Meny m = new Meny(o);
  }
}
