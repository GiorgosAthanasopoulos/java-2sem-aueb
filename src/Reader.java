import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Reader {
    public static StockCatalogue readStockCatalogue() {
        File stockFile = new File("StockCatalogue.txt");
        Map<Product, Integer> stock = new HashMap<>();

        if(!stockFile.exists())
            throw new RuntimeException("File StockCatalogue.txt could not be found!");

        System.out.println("Reading file: StockCatalogue.txt ...");

        return new StockCatalogue(stock);
    }

    public static OrdersCatalogue readOrdersCatalogue() {
        File ordersFile = new File("OrdersCatalogue.txt");

        if(!ordersFile.exists())
            throw new RuntimeException("File OrdersCatalogue.txt could not be found!");

        System.out.println("Reading file: OrdersCatalogue.txt");

        return new OrdersCatalogue();
    }

    public static SalesCatalogue readSalesCatalogue() {
        File salesFile = new File("SalesCatalogue.txt");

        if(!salesFile.exists())
            throw new RuntimeException("File SalesCatalogue.txt could not be found!");

        System.out.println("Reading file: SalesCatalogue.txt");

        return new SalesCatalogue();
    }
}
