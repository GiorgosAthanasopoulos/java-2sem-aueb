/**
 * Child of product class
 * A class to generalize all products that are considered components
 */
public class Component extends Product {
    /**
     * discount for all the components
     */
    protected static int discount;

    /**
     * Action: initializes all the class attributes by calling
     * super constructor
     * @param model the model name of the product
     * @param releaseYear the release year of the product
     * @param manufacturer the manufacturer of the product
     * @param price the price of the product
     */
    public Component(String model, int releaseYear, String manufacturer, double price) {
        super(model, releaseYear, manufacturer, price);
        discount = 5;
    }

    /**
     * @return (int) the discount for all the components
     */
    public static int getDiscount() {
        return discount;
    }
}
