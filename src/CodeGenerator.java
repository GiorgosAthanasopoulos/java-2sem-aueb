/**
 * A tool that generates codes for products, sales or orders
 */
public class CodeGenerator {
    /**
     * product code
     */
    private static int productCode = 0;
    /**
     * order code
     */
    private static int orderCode = 0;
    /**
     * sale code
     */
    private static int saleCode = 0;

    /**
     * @return (int) a program-generated code for the new product
     */
    public static int genCode() {
        return productCode++;
    }

    /**
     * @param transactionType the type of transaction to generate the code for
     * @return (int) a program-generated code for the new sale/order (depending on param transactionType)
     */
    public static int genCode(TransactionType transactionType) {
       return transactionType == TransactionType.ORDER ? orderCode++ : saleCode++; 
    }
}
