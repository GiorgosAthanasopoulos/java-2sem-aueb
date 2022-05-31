/**
 * A child of the transaction class
 * Represents a sale
 */
public class Sale extends Transaction {
    /**
     * the type of product that was sold
     */
    private final String productType;
    /**
     * the original price of the product
     */
    private final double originalPrice;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param product the product that is sold
     * @param name the name of the customer that the product was sold to
     * @param number the number of the customer that the product was sold to
     */
    public Sale(Product product, String name, String number) {
        super(TransactionType.SALE, product, name, number, product.getPrice() - (Component.class.isAssignableFrom(product.getClass()) ? Component.getDiscount() * product.getPrice() / 100 : Peripheral.getDiscount() * product.getPrice() / 100));

        productType = Component.class.isAssignableFrom(product.getClass()) ? "component" : "peripheral";
        originalPrice = product.price;
    }

    /**
     * @return the string representation of the sale class
     */
    @Override
    public String toString() {
        return String.format("Sale {\n\tCode: %d\n\tType: %s\n\tProduct: %s\n\tName: %s\n\tNumber: %s\n\tPrice: original-%f, discountPercentage-%d, discount-%f, final-%f\n}", code, TransactionType.SALE, product, name, number, originalPrice, productType.equals("component") ? Component.getDiscount() : Peripheral.getDiscount(), originalPrice - price, price);
    }
}
