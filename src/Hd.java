/**
 * type of hd storage
 */
enum StorageType {
    /**
     * hard drive disk
     */
   HDD,
    /**
     * solid state disk
     */
   SSD
}

/**
 * A child of the component class
 * Represents a hard drive
 */
public class Hd extends Component {
    /**
     * the type of hd
     */
    private final StorageType storageType;
    /**
     * the size of the hd
     */
    private final double size;
    /**
     * the storage capacity of the hd
     */
    private final int capacity;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param model the model name of the hd
     * @param releaseYear the release year of the hd
     * @param manufacturer the manufacturer of the hd
     * @param price the price of the hd
     * @param storageType the type of hd
     * @param size the size of the hd
     * @param capacity the storage capacity of the hd
     */
    public Hd(String model, int releaseYear, String manufacturer, double price, StorageType storageType, double size, int capacity) {
        super(model, releaseYear, manufacturer, price);
        this.storageType = storageType;
        this.size = size;
        this.capacity = capacity;
    }

    /**
     * @return the string representation of the hd class
     */
    @Override
    public String toString() {
        return String.format("Hd {\n\tCode: %s\n\tModel: %s\n\tRelease year: %d\n\tManufacturer: %s\n\tPrice: %f\n\tType: %s\n\tSize: %f\"\n\tCapacity: %d " + (capacity < 3 ? "TB" : "GB") + "\n\t}", code, model, releaseYear, manufacturer, price, storageType, size, capacity);
    }
}
