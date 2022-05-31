/**
 * enum for the chipset manufacturer values (NVIDIA/AMD)
 */
enum ChipsetManufacturer {
    /**
     * NVIDIA
     */
    NVIDIA,
    /**
     * AMD
     */
    AMD
}

/**
 * A child of the component class
 * Represents a gpu
 */
public class Gpu extends Component {
    /**
     * The chipset manufacturer of the gpu
     */
    private final ChipsetManufacturer chipsetManufacturer;
    /**
     * the memory capacity of the gpu
     */
    private final int memory;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param model the model name of the gpu
     * @param releaseYear the release year of the gpu
     * @param manufacturer the manufacturer of the gpu
     * @param price the price of the gpu
     * @param chipsetManufacturer the gpu chipset manufacturer (enum ChipsetManufacturer: NVIDIA, AMD)
     * @param memory the amount of gpu memory that the gpu has
     */
    public Gpu(String model, int releaseYear, String manufacturer, double price, ChipsetManufacturer chipsetManufacturer, int memory) {
        super(model, releaseYear, manufacturer, price);
        this.chipsetManufacturer = chipsetManufacturer;
        this.memory = memory;
    }

    /**
     * @return the string representation of the gpu class
     */
    @Override
    public String toString() {
        return String.format("Gpu {\n\tCode: %d\n\tModel: %s\n\tRelease year: %d\n\tManufacturer: %s\n\tPrice: %f\n\tChipset manufacturer: %s\n\tMemory: %d GB\n\t}", code, model, releaseYear, manufacturer, price, chipsetManufacturer, memory);
    }

    /**
     * @return string representation of serialized class
     */
    @Override
    public String serialized() {
        return String.format("\tITEM\n\t{\n\t\tITEM_TYPE %s\n\t\tMODEL %s\n\t\tMODEL_YEAR %d\n\t\tMANUFACTURER %s\n\t\tPRICE %f\n\t\tCHIPSET %s\n\t\tMEMORY %d", this.getClass().getName().toLowerCase(), model, releaseYear, manufacturer, price, chipsetManufacturer, memory);
    }
}
