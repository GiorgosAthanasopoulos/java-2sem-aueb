import java.util.ArrayList;
import java.util.List;

/**
 * A child of the catalogue inteface
 * A class that manages sales
 */
public class SalesCatalogue implements Catalogue {
    /**
     * the sold products
     */
    @SuppressWarnings({"FieldMayBeFinal", "MismatchedQueryAndUpdateOfCollection"})
    private List<Sale> soldProducts = new ArrayList<>();

    /**
     * Action: adds a new sale to the catalogue
     * @param sale the sale to add
     */
    public void addSale(Sale sale) {
        soldProducts.add(sale);
    }

    /**
     * @return the string representation of the salescatalogue class
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        for (Sale sale : soldProducts)
            res.append(sale).append("\n");

        return res.toString();
    }
}
