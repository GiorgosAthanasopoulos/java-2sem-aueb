/**
 * A child of the component class
 * Represents a cpu
 */
public class Cpu extends Component {
    /**
     * the clock frequency of the cpu
     */
    private final double clock;
    /**
     * the amount of cores that the cpu has
     */
    private final int cores;
    /**
     * whether the cpu has onboard graphics or not
     */
    private final boolean onboardGraphics;

    /**
     * Action: Initializes all the class attributes by calling super
     * constructor
     * @param model the model name of the cpu
     * @param releaseYear the release year of the cpu
     * @param manufacturer the manufacturer of the cpu
     * @param price the price of the cpu
     * @param clock the cpu core clock
     * @param cores the number of cpu cores
     * @param onboardGraphics (boolean) whether the cpu has integrated graphics
     */
    public Cpu(String model, int releaseYear, String manufacturer, double price, double clock, int cores, boolean onboardGraphics) {
        super(model, releaseYear, manufacturer, price);
        this.clock = clock;
        this.cores = cores;
        this.onboardGraphics = onboardGraphics;
    }

    /**
     * @return the string representation of the cpu class
     */
    @Override
    public String toString() {
        return String.format("Cpu {\n\tCode: %d\n\tModel: %s\n\tRelease year: %d\n\tManufacturer: %s\n\tPrice: %f\n\tClock: %f GHz\n\tCore count: %d\n\tOnboard graphics: %s\n\t}", code, model, releaseYear, manufacturer, price, clock, cores, (onboardGraphics ? "yes" : "no"));
    }
}
