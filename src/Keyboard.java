/**
 * The type of connection that the keyboard uses
 */
enum Connection {
    /**
     * WIRED
     */
   WIRED,
    /**
     * WIRELESS
     */
   WIRELESS
}

/**
 * A child of the peripheral class
 * Represents a keyboard
 */
public class Keyboard extends Peripheral {
    /**
     * Type of connection that the keyboard uses
     */
    private final Connection connection;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param model the model name of the keyboard
     * @param releaseYear the release year of the keyboard
     * @param manufacturer the manufacturer of the keyboard
     * @param price the price of the keyboard
     * @param connection type of connection (enum Connection: WIRED, WIRELESS)
     */
    public Keyboard(String model, int releaseYear, String manufacturer, double price, Connection connection) {
        super(model, releaseYear, manufacturer, price);
        this.connection = connection;
    }

    /**
     * @return the string representation of the keyboard class
     */
    @Override
    public String toString() {
        return String.format("Keyboard {\n\tCode: %s\n\tModel: %s\n\tRelease year: %d\n\tManufacturer: %s\n\tPrice: %f\n\tConnection: %s\n\t}", code, model, releaseYear, manufacturer, price, connection);
    }

    /**
     * @return string representation of serialized class
     */
    @Override
    public String serialized() {
        return String.format("\tITEM\n\t{\n\t\tITEM_TYPE %s\n\t\tMODEL %s\n\t\tMODEL_YEAR %d\n\t\tMANUFACTURER %s\n\t\tPRICE %f\n\t\tCONNECTION %s", this.getClass().getName().toLowerCase(), model, releaseYear, manufacturer, price, connection);
    }
}
