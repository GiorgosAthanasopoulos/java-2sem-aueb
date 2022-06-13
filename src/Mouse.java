/**
 * Type of sensor that the mouse has
 */
enum Sensor {
    /**
     * LASER
     */
    LASER,
    /**
     * OPTICAL
     */
    OPTICAL
}

/**
 * A child of the peripheral class
 * Represents a mouse
 */
public class Mouse extends Peripheral {
    /**
     * the type of connection that the mouse uses
     */
   private final Connection connection;
    /**
     * the type of sensor that the mouse has
     */
   private final Sensor sensor;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param model the model name of the mouse
     * @param releaseYear the release year of the mouse
     * @param manufacturer the manufacturer of the mouse
     * @param price the price of the mouse
     * @param sensor the type of sensor on the mouse (enum Sensor: LASER, OPTICAL)
     * @param connection the type of connection on the mouse (enum Connection: WIRED, WIRELESS)
     */
   public Mouse(String model, int releaseYear, String manufacturer, double price, Sensor sensor, Connection connection) {
       super(model, releaseYear, manufacturer, price);
       this.connection = connection;
       this.sensor = sensor;
   }

    /**
     * @return the string representation of the mouse class
     */
   @Override
   public String toString() {
       return String.format("Mouse {\n\tCode: %d\n\tModel: %s\n\tRelease year: %d\n\tManufacturer: %s\n\tPrice: %f\n\tConnection: %s\n\tSensor: %s\n\t}", code, model, releaseYear, manufacturer, price, connection, sensor);
   }
    
    /**
     * @return string representation of serialized class
     */
   @Override
    public String serialized() {
       return String.format("\tITEM\n\t{\n\t\tITEM_TYPE %s\n\t\tMODEL %s\n\t\tMODEL_YEAR %d\n\t\tMANUFACTURER %s\n\t\tPRICE %f\n\t\tCONNECTION %s\n\t\tSENSOR %s", this.getClass().getName().toLowerCase(), model, releaseYear, manufacturer, price, connection, sensor);
   }
}
