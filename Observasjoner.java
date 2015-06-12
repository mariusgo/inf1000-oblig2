import easyIO.*; // http://www.nettressurser.no/java/Java-og-easyIO

/**
 * Objects of Observasjoner can register new bird
 * observations and print all observations of a specific 
 * bird or on a specific location. All user input 
 * validatation is done in objects of this class.
 */
public class Observasjoner {
  private String rapportfil;
  private Out fil;
  private Out terminal;
  private In tastatur;
  private int antallObservasjoner;

  // Validation variables
  private final String UlovligPrefiks = "Ulovlig inndata. Du skreiv \"";
  private final String UlovligSuffiks = "\".\nKun standard latinske, dvs. ikke-skandinaviske, bokstaver er tillatt.";
  private final String UlovligStorrelse = "Systemet krever at ordet maa bestaa av minst to bokstaver.";
  private final String lovligKjoenn = "\".\nVelg mellom \"M: hanfugl\", \"F: hunfugl\" og \"X: kjoenn ikke fastslaatt\".";
  private final String lovligDato = "\".\nAngi dato etter dette moensteret: \"mai2013\".";
  private final String lovligAar = "\".\nSystemet tillater ikke registreringer foer aar 2000.";
  private final String maneder = "(januar|februar|mars|april|mai|juni|juli|august|september|oktober|november|desember)";
  private final String aar = "(2[0-1][0-9][0-9])";
  private final String fornaar = "(1?[0-9]?[0-9]?[0-9])";
  private final String ci = "(?i)";
  private final String separator = ",";
  private final String validOrd = ci + "[a-z ]{2,}";
  private final String validKjoenn = ci + "(m|f|x)";
  private final String validDato = ci + maneder + aar;
  private final String invalidDato = ci + maneder + fornaar;
  private String alleObservasjonerFugletype = "";
  private String alleObservasjonerSted = "";
  private final String suksessRegistrering = "Observasjon lagret..";
  

  /**
   * The constructor takes the name of the data file with 
   * all the observations stored as parameter and remembers 
   * this value in the class variable rapportfil (report 
   * file). easyIO in/out handling is assigned. Number of
   * observations is set to 0 (from parameter n).
   * 
   * @param r Name of the report file with all the observations.
   * @param n Number of observations registered.
   */
  Observasjoner(String r, int n) {
    rapportfil = r;
    fil = new Out(r, true);
    terminal = new Out();
    tastatur = new In();
    antallObservasjoner = n;
  }

  /**
   * Method that is called from the menu to let the user
   * register new bird observations from the command prompt.
   * Needs access to menu layout and text variables in Meny
   * class.
   *
   * @param m Meny-object.
   */
  public void registrer(Meny m) {
    terminal.outln();
    terminal.out(m.hentMenyTekst().get("TERM_FUGLETYPE"));
    String fugletype = tastatur.inLine().trim();
    while (!fugletype.matches(validOrd)) {
      terminal.out(UlovligPrefiks + fugletype);
      terminal.outln(UlovligSuffiks);
      if (fugletype.length() < 2) terminal.outln(UlovligStorrelse);
      terminal.out(m.hentMenyTekst().get("TERM_FUGLETYPE"));
      fugletype = tastatur.inLine().trim();
    }
    fugletype = fugletype.toLowerCase();

    terminal.out(m.hentMenyTekst().get("TERM_KJOENN"));
    String kjoenn = tastatur.inLine().trim();
    while (!kjoenn.matches(validKjoenn)) {  // M for male bird, F for female bird, X for unknown sex
      terminal.out(UlovligPrefiks + kjoenn);
      terminal.outln(lovligKjoenn);
      terminal.out(m.hentMenyTekst().get("TERM_KJOENN"));
      kjoenn = tastatur.inLine().trim();
    }
    kjoenn = kjoenn.toUpperCase();

    terminal.out(m.hentMenyTekst().get("TERM_STED"));
    String observasjonssted = tastatur.inLine().trim();
    while (!observasjonssted.matches(validOrd)) { // Municipality name, i.e. "Stavern". Scandinavian letters not supported.
      terminal.out(UlovligPrefiks + observasjonssted);
      terminal.outln(UlovligSuffiks);
      if (observasjonssted.length() < 2) {
        terminal.outln(UlovligStorrelse);
      }
      terminal.out(m.hentMenyTekst().get("TERM_STED"));
      observasjonssted = tastatur.inLine().trim();
    }
    observasjonssted = observasjonssted.toLowerCase();
    observasjonssted = Character.toUpperCase(observasjonssted.charAt(0)) + observasjonssted.substring(1);
    
    terminal.out(m.hentMenyTekst().get("TERM_DATO"));
    String dato = tastatur.inLine().trim();
    while (!dato.matches(validDato) || dato.matches(invalidDato)) { // mai2013
      terminal.out(UlovligPrefiks + dato);
      if (dato.matches(invalidDato)) terminal.outln(lovligAar);
      else terminal.outln(lovligDato);
      terminal.out(m.hentMenyTekst().get("TERM_DATO"));
      dato = tastatur.inLine().trim();
    }
    dato = dato.toLowerCase();
    
    fil.outln(fugletype + separator + kjoenn + separator + observasjonssted + separator + dato);
    terminal.outln(suksessRegistrering);
    fil.close();
  }
  
  /**
   * Method that writes all registered observations for a 
   * specific bird to terminal window. Needs access to menu 
   * layout and text variables in Meny class.
   *
   * @param m Meny-object.
   */
  public void skrivFugletype(Meny m) {
    terminal.outln();
    terminal.out(m.hentMenyTekst().get("TERM_FUGLETYPE"));
    alleObservasjonerFugletype = tastatur.inLine().trim();
    while (!alleObservasjonerFugletype.matches(validOrd)) {
      terminal.out("Kvakk? Hva mener du med + \"" + alleObservasjonerFugletype);
      terminal.outln(UlovligSuffiks);
      if (alleObservasjonerFugletype.length() < 2) terminal.outln(UlovligStorrelse);
      terminal.out(m.hentMenyTekst().get("TERM_FUGLETYPE"));
      alleObservasjonerFugletype = tastatur.inLine().trim();
    }

    terminal.outln();
    terminal.outln(m.hentMenyTekst().get("BORD_KORT"));
    terminal.out("| ",m.hentMenyVariabler().get("BREDDE2"));
    terminal.out(alleObservasjonerFugletype.toUpperCase() + ":",m.hentMenyVariabler().get("BREDDEmidt"),Out.CENTER);
    terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
    terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
    terminal.out("",m.hentMenyVariabler().get("BREDDEmidt"));
    terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
    terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
    terminal.out("Kjoenn", m.hentMenyVariabler().get("BREDDE10"), Out.CENTER);
    terminal.out("Sted", m.hentMenyVariabler().get("BREDDE20"), Out.LEFT);
    terminal.out("Dato", m.hentMenyVariabler().get("BREDDE15"), Out.LEFT);
    terminal.outln("|", m.hentMenyVariabler().get("BREDDE1"));
    terminal.outln(m.hentMenyTekst().get("BORD_KORT"));
    In rapportFugltype = new In(rapportfil);
    while (rapportFugltype.hasNext()) {
      String linje = rapportFugltype.inLine();
      if (linje.matches("^(" + ci + alleObservasjonerFugletype + "),((" + ci + "(f|m|x))(,[^,]+){2,})")) {  // Regex too "kind". Should be improved as the system data expands
        linje = linje.replaceAll("^(" + ci + alleObservasjonerFugletype + "),((" + ci + "(f|m|x))(,[^,]+){2,})", "$2");
        String[] ordPaaLinja = linje.split(separator);
        terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
        terminal.out(ordPaaLinja[0], m.hentMenyVariabler().get("BREDDE10"), Out.CENTER);
        terminal.out(ordPaaLinja[1], m.hentMenyVariabler().get("BREDDE20"), Out.LEFT);
        terminal.out(ordPaaLinja[2], m.hentMenyVariabler().get("BREDDE15"), Out.LEFT);
        terminal.outln("|", m.hentMenyVariabler().get("BREDDE1"));
        antallObservasjoner++;
      }
    }
    if (antallObservasjoner == 0) {
      terminal.out("|", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out("",m.hentMenyVariabler().get("BREDDEmidt"));
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
      terminal.out("|", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out("Ingen treff.",m.hentMenyVariabler().get("BREDDEmidt"), Out.CENTER);
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
      terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out("",m.hentMenyVariabler().get("BREDDEmidt"));
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
    } else {
      terminal.out("|", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out("",m.hentMenyVariabler().get("BREDDEmidt"));
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
      terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out(m.hentMenyTekst().get("ANT_OBS") + antallObservasjoner,m.hentMenyVariabler().get("BREDDEmidt"));
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
    }
    terminal.outln(m.hentMenyTekst().get("BORD_KORT"));
    terminal.outln();

    rapportFugltype.close();
  }
  
  /**
   * Method that writes all registered observations on a
   * specific location to terminal window. Needs access to 
   * menu layout and text variables in Meny class.
   *
   * @param m Meny-object.
   */
  public void skrivSted(Meny m) {
    terminal.outln();
    terminal.out(m.hentMenyTekst().get("TERM_STED"));
    alleObservasjonerSted = tastatur.inLine().trim();
    while (!alleObservasjonerSted.matches(validOrd)) { // Scandinavian letters not supported.
      terminal.outln(UlovligPrefiks + alleObservasjonerSted + UlovligPrefiks);
      terminal.out(m.hentMenyTekst().get("TERM_STED"));
      if (alleObservasjonerSted.length() < 2) {
        terminal.outln(UlovligStorrelse);
      }
      alleObservasjonerSted = tastatur.inLine().trim();
    }

    terminal.outln();
    terminal.outln(m.hentMenyTekst().get("BORD_KORT"));
    terminal.out("| ",m.hentMenyVariabler().get("BREDDE2"));
    terminal.out(alleObservasjonerSted.toUpperCase() + ":",m.hentMenyVariabler().get("BREDDEmidt"),Out.CENTER);
    terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
    terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
    terminal.out("",m.hentMenyVariabler().get("BREDDEmidt"));
    terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
    terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
    terminal.out("Fugletype", m.hentMenyVariabler().get("BREDDE10"), Out.LEFT);
    terminal.out("Kjoenn", m.hentMenyVariabler().get("BREDDE10"), Out.CENTER);
    terminal.out("Dato", m.hentMenyVariabler().get("BREDDE15"), Out.LEFT);
    terminal.out("", m.hentMenyVariabler().get("BREDDE10"), Out.LEFT);
    terminal.outln("|", m.hentMenyVariabler().get("BREDDE1"));
    terminal.outln(m.hentMenyTekst().get("BORD_KORT"));
    In rapportSted = new In(rapportfil);
    while (rapportSted.hasNext()) {
      String linje = rapportSted.inLine();
      if (linje.matches("(([^,]+,){2})(" + ci + alleObservasjonerSted + ",)([^,]+)")) {
        String[] ordPaaLinja = linje.split(separator);
        terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
        String fuglStorForbokstav = Character.toUpperCase(ordPaaLinja[0].charAt(0)) + ordPaaLinja[0].substring(1);
        terminal.out(fuglStorForbokstav, m.hentMenyVariabler().get("BREDDE10"), Out.LEFT);
        terminal.out(ordPaaLinja[1], m.hentMenyVariabler().get("BREDDE10"), Out.CENTER);
        terminal.out(ordPaaLinja[3], m.hentMenyVariabler().get("BREDDE15"), Out.LEFT);
        terminal.out("", m.hentMenyVariabler().get("BREDDE10"), Out.LEFT);
        terminal.outln("|", m.hentMenyVariabler().get("BREDDE1"));
        antallObservasjoner++;
      }
    }
    if (antallObservasjoner == 0) {
      terminal.out("|", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out("",m.hentMenyVariabler().get("BREDDEmidt"));
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
      terminal.out("|", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out("Ingen treff.",m.hentMenyVariabler().get("BREDDEmidt"), Out.CENTER);
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
      terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out("",m.hentMenyVariabler().get("BREDDEmidt"));
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
    } else {
      terminal.out("|", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out("",m.hentMenyVariabler().get("BREDDEmidt"));
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
      terminal.out("| ", m.hentMenyVariabler().get("BREDDE2"));
      terminal.out(m.hentMenyTekst().get("ANT_OBS") + antallObservasjoner,m.hentMenyVariabler().get("BREDDEmidt"));
      terminal.outln("|",m.hentMenyVariabler().get("BREDDE1"));
    }
    terminal.outln(m.hentMenyTekst().get("BORD_KORT"));
    terminal.outln();

    rapportSted.close();
  }
}
