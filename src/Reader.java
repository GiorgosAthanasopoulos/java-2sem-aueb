import java.io.File;
import java.util.HashMap;

/**
 * A class that will read catalogues from the filesystem and transform them into java objects
 */
public class Reader {
    /**
     * A method that will read catalogues from the filesystem and transform them into java objects
     */
    public static Catalogue readCatalogue(String catalogueClassName) {
        String extension = ".txt";
        String fileName = catalogueClassName + extension;
        File catalogueFile = new File(fileName);

        if(!catalogueFile.exists())
            throw new RuntimeException(String.format("File %s could not be found!", fileName));

        System.out.printf("Reading file: %s%n", fileName);

        return switch (catalogueClassName) {
            case "StockCatalogue" -> new StockCatalogue(new HashMap<>());
            case "OrdersCatalogue" -> new OrdersCatalogue();
            case "SalesCatalogue" -> new SalesCatalogue();
        };
    }
}
