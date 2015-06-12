import easyIO.*;
import java.util.HashMap;

/**
 * Objects of Meny class can print the system menu to 
 * terminal window. All info regarding menu layout and
 * text elements are stored in the Meny class.
 */
public class Meny {
  // IO
  private In tastatur;
  private Out terminal;
  
  // Layout variables
  private final int BREDDE2 = 2;
  private final int BREDDE10 = 10;
  private final int BREDDE20 = 20;
  private final int BREDDE15 = 15;
  private final int BREDDE1 = 1;
  private final int BREDDEmidt = BREDDE10+BREDDE20+BREDDE15;
  private final int BREDDE55 = 75;
  private final String BORD_KORT = "|----------------------------------------------|";
  private final String BORD_LANG = "|----------------------------------------------------------------------------|";
  private final String TERM_FUGLETYPE = "Fugletype: ";
  private final String TERM_KJOENN = "Kjoenn: ";
  private final String TERM_STED = "Observasjonssted: ";
  private final String TERM_DATO = "Observasjonsdato: ";
  private final String ANT_OBS = "Antall observasjoner: ";
  private final String ULOVLIG_VALG = "Velg mellom menyvalg 1, 2, 3 eller 4.";

  /**
   * The Constructor declares the easyIO in/out print 
   * handlers and prints the menu to terminal. Takes an 
   * object of Observasjoner class to access its methods for
   * registering and printing bird observations.
   *
   * @param o Object of Observasjoner class.
   */
  Meny(Observasjoner o) {
    tastatur = new In();
    terminal = new Out();
    int menyvalg = 0;

    do {
      meny();
      menyvalg = tastatur.inInt();

      switch (menyvalg) {
        case 1:    // Register a new observation and store all it to report file
          o.registrer(this);
          break;
        case 2:    // Print all observations for a specific bird type
          o.skrivFugletype(this);
          break;
        case 3:    // Print all observations on a specific location
          o.skrivSted(this);
          break;
        case 4:    // Terminate the program
          break;
        default:   // Print suitable error message if menu option != 1-4
          ukorrektMenyvalg();
      }

    } while (menyvalg != 4);
  }
  
  /**
   * Method that prints the system menu to terminal.
   */
  public void meny() {
    terminal.outln();
    terminal.outln(BORD_LANG);
    terminal.out("| ", BREDDE2, Out.LEFT);
    terminal.out("O L E S   I T - S Y S T E M   F O R   F U G L E O B S E R V A S J O N", BREDDE55, Out.CENTER);
    terminal.outln("|", BREDDE1, Out.LEFT);
    terminal.outln(BORD_LANG);
    terminal.out("| ", BREDDE2, Out.LEFT);
    terminal.out("1. Registrer en fugleobservasjon", BREDDE55, Out.LEFT);
    terminal.outln("|", BREDDE1, Out.LEFT);
    terminal.out("| ", BREDDE2, Out.LEFT);
    terminal.out("2. Skriv ut alle observasjoner av en fugletype", BREDDE55, Out.LEFT);
    terminal.outln("|", BREDDE1, Out.LEFT);
    terminal.out("| ", BREDDE2, Out.LEFT);
    terminal.out("3. Skriv ut alle observasjonene paa ett bestemt sted", BREDDE55, Out.LEFT);
    terminal.outln("|", BREDDE1, Out.LEFT);
    terminal.out("| ", BREDDE2, Out.LEFT);
    terminal.out("4. Avslutt systemet", BREDDE55, Out.LEFT);
    terminal.outln("|", BREDDE1, Out.LEFT);
    terminal.outln(BORD_LANG);
    terminal.outln();
    terminal.out("Velg: ");
  }

  /**
   * Method that print an error message to terminal if user
   * tries to give any invalid user input to the system.
   */  
  public void ukorrektMenyvalg() {
    terminal.outln(ULOVLIG_VALG);
  }
  
  /**
   * Method that returns all the menu integer values. All 
   * the values are returned in a hashMap.
   *
   * @return menyVariabler HashMap with all the menu integer values.
   */
  public HashMap<String,Integer> hentMenyVariabler() {
    HashMap<String,Integer> menyVariabler = new HashMap<String,Integer>();
    menyVariabler.put("BREDDE2",BREDDE2);
    menyVariabler.put("BREDDE10",BREDDE10);
    menyVariabler.put("BREDDE20",BREDDE20);
    menyVariabler.put("BREDDE15",BREDDE15);
    menyVariabler.put("BREDDE1",BREDDE1);
    menyVariabler.put("BREDDEmidt",BREDDEmidt);
    menyVariabler.put("BREDDE55",BREDDE55);
    return menyVariabler;
  }

  /**
   * Method that returns all the menu text elements. All the
   * values are returned in a hashMap.
   *
   * @return menyTekst HashMap with all the menu text elements.
   */
  public HashMap<String,String> hentMenyTekst() {
    HashMap<String,String> menyTekst = new HashMap<String,String>();
    menyTekst.put("BORD_KORT",BORD_KORT);
    menyTekst.put("BORD_LANG",BORD_LANG);
    menyTekst.put("TERM_FUGLETYPE",TERM_FUGLETYPE);
    menyTekst.put("TERM_KJOENN",TERM_KJOENN);
    menyTekst.put("TERM_STED",TERM_STED);
    menyTekst.put("TERM_DATO",TERM_DATO);
    menyTekst.put("ANT_OBS",ANT_OBS);
    menyTekst.put("ULOVLIG_VALG",ULOVLIG_VALG);
    return menyTekst;
  }
}
