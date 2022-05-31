import java.util.Map;
import java.util.HashMap;

/**
 * A child of the catalogue class
 * A class that manages stock
 */
public class StockCatalogue implements Catalogue {
    /**
     * the stock
     */
    private final Map<Product, Integer> stock;
    /**
     * map to help with the filter method
     */
    private static final Map<Integer, String> categories = Map.of(
            0, "motherboard",
            1, "cpu",
            2, "ram",
            3, "gpu",
            4, "hd",
            5, "monitor",
            6, "keyboard",
            7, "mouse",
            8, "printer"
    );

    /**
     * initializes the stock of the catalogue
     * @param stock list of products
     */
    public StockCatalogue(Map<Product, Integer> stock) {
        this.stock = stock;
    }

    /**
     * Action: filters the class catalogue by @param filter and returns
     * the list
     * @param filter the int filter (see Map categories) to apply to the
     * class's catalogue
     * @return the filtered catalogue
     */
    public Map<Product, Integer> filter(int filter) {
        Map<Product, Integer> res = new HashMap<>();

        for(Product product : stock.keySet())
            if(product.getClass().getName().toLowerCase().equals(categories.get(filter)))
                res.put(product, stock.get(product));

        return res;
    }

    /**
     * Action: updates or adds a catalogue entry
     * @param product the product
     * @param number the amount of similar products in stock
     */
    public void put(Product product, Integer number) {
        stock.put(product, number);
    }
}
