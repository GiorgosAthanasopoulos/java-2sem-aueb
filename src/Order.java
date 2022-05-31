/**
 * Delivery status
 */
enum DeliveryStatus {
    /**
     * DELIVERED
     */
    DELIVERED,
    /**
     * TO BE DELIVERED
     */
    TOBEDELIVERED
}

/**
 * A child of the transaction class
 * Represents an order
 */
public class Order extends Transaction {
    /**
     * Delivery date for the order
     */
    private final String deliveryDate;
    /**
     * the delivery status of the order
     */
    private DeliveryStatus deliveryStatus;
    /**
     * the product type
     */
    private final String productType;
    /**
     * the original price before discount
     */
    private final double originalPrice;

    /**
     * Action: initializes all the class attributes by calling super
     * constructor
     * @param product the product that is ordered
     * @param name the name of the customer that ordered
     * @param number the phone number of the customer that ordered
     * @param deliveryDate the delivery date of the order
     */
    public Order(Product product, String name, String number, String deliveryDate) {
        super(TransactionType.ORDER, product, name, number,
                product.getPrice() - (Component.class.isAssignableFrom(product.getClass())
                        ? Component.getDiscount() * product.getPrice() / 100
                        : Peripheral.getDiscount() * product.getPrice() / 100));
        this.deliveryDate = deliveryDate;
        this.deliveryStatus = DeliveryStatus.TOBEDELIVERED;

        productType = Component.class.isAssignableFrom(product.getClass()) ? "component" : "peripheral";
        originalPrice = product.price;
    }

    /**
     * Action: changes the delivery status of the order
     * @param deliveryStatus the delivery status of the order (enum)
     */
    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * @return the string representation of the order class
     */
    @Override
    public String toString() {
        return String.format(
                "Order {\n\tCode: %d\n\tType: %s\n\tProduct: %s\n\tName: %s\n\tNumber: %s\n\tPrice: original-%f, discountPercentage-%d, discount-%f, final-%f\n\tDelivery date: %s\n\tDelivery status: %s\n}",
                code, TransactionType.ORDER, product, name, number, originalPrice,
                productType.equals("component") ? Component.getDiscount() : Peripheral.getDiscount(),
                originalPrice - price, price, deliveryDate, deliveryStatus);
    }

    /**
     * @return string representation of serialized class
     */
    @Override
    public String serialized() {
        return String.format("\tORDER\n\t{\n\t\tCODE %d\n\t\tITEM_TYPE %s\n\t\tMODEL %s\n\t\tNAME %s\n\t\tNUMBER %s\n\t\tDATE %s\n\t\tPRICE %f\n\t\tDELIVERY_DATE %s", code, product.getClass().getName().toLowerCase(), product.preview(), name, number, date, price, deliveryDate);
    }
}