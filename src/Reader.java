import java.util.HashMap;

public class Reader {
    public static StockCatalogue readStockCatalogue() {
        return new StockCatalogue(new HashMap<>());
    }

    public static OrdersCatalogue readOrdersCatalogue() {
        return new OrdersCatalogue();
    }

    public static SalesCatalogue readSalesCatalogue() {
        return new SalesCatalogue();
    }
}
