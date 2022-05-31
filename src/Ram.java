/**
 * Enum to represent types of ddr memory modules
 */
enum Ddr {
    /**
     * DDR3
     */
   DDR3,
    /**
     * DDR4
     */
   DDR4,
    /**
     * DDR5
     */
   DDR5
}

/**
 * A child of the component class
 * Represents a ram module
 */
public class Ram extends Component {
    /**
     * type of ddr
     */
   private final Ddr type;
    /**
     * the memory capacity of the module
     */
   private final int size;
    /**
     * the frequency of the memory
     */
   private final int freq;

    /**
     * Action: initializes all class attributes by calling super
     * constructor
     * @param model the model of the ram module
     * @param releaseYear the release year of the ram module
     * @param manufacturer the manufacturer of the ram module
     * @param price the price of the ram module
     * @param type the type of the ram module (enum Ddr: DDR3, DDR4, DDR5)
     * @param size the capacity of the ram module
     * @param freq the frequency of the ram module
     */
  public Ram(String model, int releaseYear, String manufacturer, double price, Ddr type, int size, int freq) {
      super(model, releaseYear, manufacturer, price);
      this.type = type;
      this.size = size;
      this.freq = freq;
  }

    /**
     * @return the string representation of the ram class
     */
  @Override
  public String toString() {
      return String.format("Ram {\n\tCode: %d\n\tModel: %s\n\tRelease year: %d\n\tManufacturer: %s\n\tPrice: %f\n\tDdr: %s\n\tSize: %d GB\n\tFrequency: %d MHz\n\t}", code, model, releaseYear, manufacturer, price, type, size, freq);
  }

    /**
     * @return string representation of serialized class
     */
  @Override
  public String serialized() {
      return String.format("\tITEM\n\t{\n\t\tITEM_TYPE %s\n\t\tMODEL %s\n\t\tMODEL_YEAR %d\n\t\tMANUFACTURER %s\n\t\tPRICE %f\n\t\tDDR %s\n\t\tSIZE %d\n\t\tFREQUENCY %d", this.getClass().getName().toLowerCase(), model, releaseYear, manufacturer, price, type, size, freq);
  }
}
