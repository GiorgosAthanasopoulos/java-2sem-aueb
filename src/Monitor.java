import java.util.Arrays;

/**
 * Type of monitor
 */
enum MonitorType {
    /**
     * MONITOR
     */
    MONITOR,
    /**
     * PORTABLE
     */
    PORTABLE,
    /**
     * TV
     */
    TV
}

/**
 * PORT
 */
enum Port {
    /**
     * DISPLAY PORT
     */
    DP,
    /**
     * HDMI
     */
    HDMI,
    /**
     * USB C
     */
    USBC
}

/**
 * BACKLIGHT: LCD/LED
 */
enum Backlight {
    /**
     * LCD
     */
    LCD,
    /**
     * LED
     */
    LED
}

/**
 * A child of the peripheral class
 * Represents a monitor
 */
public class Monitor extends Peripheral {
    /**
     * the type of the monitor
     */
    private final MonitorType monitorType;
    /**
     * the size of the monitor in inches
     */
    private final int size;
    /**
     * the resolution of the monitor in format: "widthXheight"
     */
    private final String resolution;
    /**
     * A list with all the ports that the monitor has
     */
    private final Port[] ports;
    /**
     * What backlight technology the monitor uses
     */
    private final Backlight backlight;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param model the model name of the monitor
     * @param releaseYear the release year of the monitor
     * @param manufacturer the manufacturer of the monitor
     * @param price the price of the monitor
     * @param monitorType the monitor type (enum MonitorType: MONITOR, PORTABLE, TV)
     * @param size the size of the monitor in inches
     * @param resolution the resolution of the monitor (format: "widthXheight")
     * @param ports an array of ports that the monitor has
     */
    public Monitor(String model, int releaseYear, String manufacturer, double price, MonitorType monitorType, int size, String resolution, Port[] ports, Backlight backlight) {
        super(model, releaseYear, manufacturer, price);
        this.monitorType = monitorType;
        this.size = size;
        this.resolution = resolution;
        this.ports = ports;
        this.backlight = backlight;
    }

    /**
     * @return the string representation of the monitor class
     */
    @Override
    public String toString() {
        return String.format("Monitor {\n\tCode: %d\n\tModel: %s\n\tRelease year: %d\n\tManufacturer: %s\n\tPrice: %f\n\tType: %s\n\tSize: %d\"\n\tResolution: %spx\n\tPorts: %s\n\t}", code, model, releaseYear, manufacturer, price, monitorType, size, resolution, Arrays.toString(ports));
    }

    /**
     * @return string representation of serialized class
     */
    @Override
    public String serialized() {
        return String.format("\tITEM\n\t{\n\t\tITEM_TYPE %s\n\t\tMODEL %s\n\t\tMODEL_YEAR %d\n\t\tMANUFACTURER %s\n\t\tPRICE %f\n\t\tMONITOR_TYPE %s\n\t\tSIZE %d\n\t\tRESOLUTION %s\n\t\tPORTS %s\n\t\tBACKLIGHT %s", this.getClass().getName().toLowerCase(), model, releaseYear, manufacturer, price, monitorType, size, resolution, Arrays.toString(ports), backlight);
    }
}
