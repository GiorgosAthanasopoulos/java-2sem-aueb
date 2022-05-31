/**
 * A child of the product class
 * A class that generalizes all products that are considered as peripherals
 */
public class Peripheral extends Product {
    /**
     * the discount for all the peripherals
     */
    protected static int discount;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param model the model of the product
     * @param releaseYear the release year of the product
     * @param manufacturer the manufacturer of the product
     * @param price the price of the product
     */
    public Peripheral(String model, int releaseYear, String manufacturer, double price) {
        super(model, releaseYear, manufacturer, price);
        discount = 10;
    }

    /**
     * @return the discount of all the peripherals
     */
    public static int getDiscount() {
        return discount;
    }
}
