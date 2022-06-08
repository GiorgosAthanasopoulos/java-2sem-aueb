import java.util.ArrayList;
import java.util.List;

/**
 * A child of the catalogue interface
 * A class to manage orders
 */
public class OrdersCatalogue implements Catalogue {
    /**
     * The list of the orders
     */
    @SuppressWarnings("FieldMayBeFinal")
    private List<Order> orderedProducts = new ArrayList<>();

    /**
     * constructor
     */
    public OrdersCatalogue(List<Order> orders) {
        orderedProducts.addAll(orders);
    }

    public OrdersCatalogue() {

    }

    /**
     * Action: adds a new order to the catalogue
     * @param order the order to add
     */
    public void addOrder(Order order) {
        orderedProducts.add(order);
    }

    /**
     * @return the list of orders that the class stores
     */
    public List<Order> getOrderCatalogue() {
        return orderedProducts;
    }

    /**
     * Action: removes an order from the catalogue based on its code (unique)
     * @param code the code of the order to remove
     */
    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public void remove(int code) {
        for(int i=0; i<orderedProducts.size(); i++)
            if(orderedProducts.get(i).code == code)
                orderedProducts.remove(i);
    }

    /**
     * @return the string representation of the orderscatalogue class
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        for(Order order : orderedProducts)
            res.append(order.code).append(". ").append(order).append("\n");

        return res.toString();
    }

    /**
     * @return string representation of serialized class
     */
    @Override
    public String serialized() {
        StringBuilder buff = new StringBuilder();

        buff.append("ORDER_LIST\n{\n");

        for(Order order : orderedProducts) {
            buff.append(order.serialized());
        }

        buff.append("\n}");

        return buff.toString();
    }
}
