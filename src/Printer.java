/**
 * Type of colors that the printer can print
 */
enum Colors {
    /**
     * ALL COLORS
     */
    COLORED,
    /**
     * BLACK AND WHITE
     */
    BLACKNWHITE
}

/**
 * the type of printer
 */
enum PrinterType {
    /**
     * LASER
     */
   LASER,
    /**
     * INKJET
     */
   INKJET
}

/**
 * A child of the peripheral class
 * Represents a printer
 */
public class Printer extends Peripheral {
    /**
     * the type of printer
     */
   private final PrinterType printerType;
    /**
     * the colors that the printer can print
     */
   private final Colors colors;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param model the model name of the printer
     * @param releaseYear the release year of the printer
     * @param manufacturer the manufacturer of the printer
     * @param price the price of the printer
     * @param printerType the type of printer (enum PrinterType: LASER, INKJET)
     * @param colors the colors that the printer can print (enum Colors: BLACKNWHITE, COLORED)
     */
   public Printer(String model, int releaseYear, String manufacturer, double price, PrinterType printerType, Colors colors) {
       super(model, releaseYear, manufacturer, price);
       this.printerType = printerType;
       this.colors = colors;
   }

    /**
     * @return the string representation of the printer class
     */
   @Override
   public String toString() {
       return String.format("Printer {\n\tCode: %d\n\tModel: %s\n\tRelease year: %d\n\tManufacturer: %s\n\tPrice: %f\n\tType: %s\n\tColors: %s\n\t}", code, model, releaseYear, manufacturer, price, printerType, colors);
   }
    
    /**
     * @return string representation of serialized class
     */
   @Override
    public String serialized() {
       return String.format("\tITEM\n\t{\n\t\tITEM_TYPE %s\n\t\tMODEL %s\n\t\tMODEL_YEAR %d\n\t\tMANUFACTURER %s\n\t\tPRICE %f\n\t\tPRINTER_TYPE %s\n\t\tCOLORS %s", this.getClass().getName().toLowerCase(), model, releaseYear, manufacturer, price, printerType, colors);
   }
}
