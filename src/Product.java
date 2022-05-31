/**
 * A class to represent products
 */
public class Product implements Serializable {
    /**
     * the model name of the product
     */
    protected String model;
    /**
     * the release year of the product
     */
    protected int releaseYear;
    /**
     * the manufacturer of the product
     */
    protected String manufacturer;
    /**
     * the price of the product
     */
    protected double price;

    /**
     * the unique product code
     */
    protected final int code;

    /**
     * Action: initializes all the class attributes
     * @param model the model name of the product
     * @param releaseYear the release year of the product
     * @param manufacturer the manufacturer of the product
     * @param price the price of the product
     */
    public Product(String model, int releaseYear, String manufacturer, double price) {
        this.model = model;
        this.releaseYear = releaseYear;
        this.manufacturer = manufacturer;
        this.price = price;
        this.code = CodeGenerator.genCode();
    }

    /**
     * @return the preview of the product (model)
     */
    public String preview() {
        return model;
    }

    /**
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return string representation of serialized class
     */
    @Override
    public String serialized() {
        return "";
    }
}
