/**
 * type of transaction
 */
enum TransactionType {
    /**
     * SALE
     */
    SALE,
    /**
     * ORDER
     */
    ORDER
}

/**
 * A class that represents a transaction
 */
public class Transaction implements Serializable {
    /**
     * the unique transaction code
     */
    protected int code;
    /**
     * the product that was sold/ordered
     */
    protected Product product;
    /**
     * the name of the customer involved in the sale/order
     */
    protected String name;
    /**
     * the phone number of the customer involved in the sale/order
     */
    protected String number;
    /**
     * the date when the sale/order was placed
     */
    protected String date;
    /**
     * the price of the sale/order
     */
    protected double price;

    /**
     * Action: initializes all the class attributes
     * @param transactionType the type of transaction (enum TransactionType: SALE, ORDER)
     * @param product the product that is ordered/sold
     * @param name the name of the number involved in the sale/order
     * @param number the number of the customer involved in the sale/order
     * @param price the sale/order price
     */
    public Transaction(TransactionType transactionType, Product product, String name, String number, double price) {
        this.code = CodeGenerator.genCode(transactionType);
        this.product = product;
        this.name = name;
        this.number = number;
        this.date = Utils.getDate();
        this.price = price;
    }
    
    /**
     * Action: initializes all the class attributes by calling super constructor
     * @param code the unique code identifier of the transaction
     * @param product the product included in the transaction
     * @param name the name of the person involved in the transaction
     * @param number the number of the person involved in the transaction
     * @param saleDate the date this transaction was finished
     * @param price the price the person had to pay in order to finish the transaction
     */
    public Transaction(int code, Product product, String name, String number, String saleDate, double price) {
        this.code = code;
        this.product = product;
        this.name = name;
        this.number = number;
        this.date = saleDate;
        this.price = price;
    }

    /**
     * @return string representation of serialized class
     */
    @Override
    public String serialized() {
        return "";
    }
}
