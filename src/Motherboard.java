/**
 * socket type
 */
enum Socket {
    /**
     * INTEL
     */
    INTEL,
    /**
     * AMD
     */
    AMD
}
/**
 * A child of the component class
 * Represents a motherboard
 */
public class Motherboard extends Component {
    /**
     * socket type of the motherboard
     */
    private final Socket socket;
    /**
     * maximum memory capacity of the motherboard
     */
    private final int memory;
    /**
     * maximum number of ports on the motherboard
     */
    private final int sataCount;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param model the model name of the motherboard
     * @param releaseYear the release year of the motherboard
     * @param manufacturer the manufacturer of the motherboard
     * @param price the price of the motherboard
     * @param socket the socket type of the motherboard (enum Socket: AMD, INTEL)
     * @param memory the max memory capacity of the motherboard
     * @param sataCount the amount of sata ports on the motherboard
     */
    public Motherboard(String model, int releaseYear, String manufacturer, double price, Socket socket, int memory, int sataCount) {
        super(model, releaseYear, manufacturer, price);
        this.socket = socket;
        this.memory = memory;
        this.sataCount = sataCount;
    }

    /**
     * @return the string representation of the motherboard class
     */
    @Override
    public String toString() {
        return String.format("Motherboard {\n\tCode: %d\n\tModel: %s\n\tRelease year: %d\n\tManufacturer: %s\n\tPrice: %f\n\tSocket: %s\n\tMemory: %d GB\n\tSata port count: %d\n\t}", code, model, releaseYear, manufacturer, price, socket, memory, sataCount);
    }

    /**
     * @return string representation of serialized class
     */
    @Override
    public String serialized() {
        return String.format("\tITEM\n\t{\n\t\tITEM_TYPE %s\n\t\tMODEL %s\n\t\tMODEL_YEAR %d\n\t\tMANUFACTURER %s\n\t\tPRICE %f\n\t\tSOCKET %s\n\t\tMEMORY %d\n\t\tSATA_COUNT %d", this.getClass().getName().toLowerCase(), model, releaseYear, manufacturer, price, socket, memory, sataCount);
    }
}