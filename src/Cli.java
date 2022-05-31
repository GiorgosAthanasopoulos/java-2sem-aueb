import java.util.*;
import java.io.IOException;

/**
 * The cli program
 */
public class Cli {
    /**
     * Main logic loop for the program.
     * 1) Initializes the 3 catalogues (stock, orders, sales)
     * 2) Starts the main loop, takes input from user and executes commands
     */
    public static void loop() {
        // initialize our stock, order and sale catalogues
        StockCatalogue stockCatalogue;
        OrdersCatalogue ordersCatalogue;
        SalesCatalogue salesCatalogue;
        try {
            stockCatalogue = Reader.readStockCatalogue();
            ordersCatalogue = Reader.readOrdersCatalogue();
            salesCatalogue = Reader.readSalesCatalogue();
        } catch (Exception ignored) {
            System.out.println("An error occurred while reading the files!");
            return;
        }

        // choice -> gets the input from user
        // res -> fetches the result from user selected function
        int choice;
        List<Catalogue> res = List.of(stockCatalogue, ordersCatalogue, salesCatalogue);

        logicLoop:
        while (true) {
            // print main menu, take input
            choice = Integer
                    .parseInt(Utils.checkInput(
                            "\n0. Overview products\n1. Overview orders\n2. Overview sales\n3. Exit\n4. Save catalogues\n\n",
                            "Enter action(0-4): ", "Invalid action(0-4)!", "0,1,2,3,4"));

            // fetch result based on user input
            switch (choice) {
                case 0 -> {
                    res = overviewProducts(stockCatalogue, ordersCatalogue, salesCatalogue);
                    stockCatalogue = (StockCatalogue) res.get(0);
                }
                case 1 -> res = overviewOrders(stockCatalogue, ordersCatalogue, salesCatalogue);
                case 2 -> System.out.print(salesCatalogue);
                case 3 -> {
                    break logicLoop;
                }
                case 4 -> {
                    saveCatalogues(stockCatalogue, ordersCatalogue, salesCatalogue);
                }
                default -> System.out.println("Invalid command.\n");
            }

            ordersCatalogue = (OrdersCatalogue) res.get(1);
            salesCatalogue = (SalesCatalogue) res.get(2);
        }

        try {
            System.in.close();
        } catch (IOException ignored) {
        }
        saveCatalogues(stockCatalogue, ordersCatalogue, salesCatalogue);
    }

    static void saveCatalogues(StockCatalogue stockCatalogue, OrdersCatalogue ordersCatalogue, SalesCatalogue salesCatalogue) {
        Writer.writeCatalogues(List.of(stockCatalogue, ordersCatalogue, salesCatalogue));
    }

    /**
     * Action: Runs when user selected action is overview of products.Takes product filters as user input,
     * enables the user to pick a product from the output list and gives him the option to purchase/order it.
     * Note: For the go back option it uses labelled loops
     * @param stockCatalogue program's stock catalogue
     * @param ordersCatalogue program's orders catalogue
     * @param salesCatalogue program's sales catalogue
     * @return List of Catalogues: returns the updated catalogues as a list after modifying them
     */
    static List<Catalogue> overviewProducts(StockCatalogue stockCatalogue, OrdersCatalogue ordersCatalogue,
            SalesCatalogue salesCatalogue) {
        category_: while (true) {
            String category = Utils.checkInput("", "Enter category(0. component, 1. peripheral, 2. go back): ",
                    "Invalid category(must be 0 for component, 1 for peripheral or 2 to go back)!", "0,1,2");

            if (category.equals("2"))
                break category_;

            type_: while (true) {
                String types = category.equals("0") ? "0. motherboard, 1. cpu, 2. ram, 3. gpu, 4. hd, 5. go back"
                        : "0. monitor, 1. keyboard, 2. mouse, 3.printer, 4. go back";
                String type = Utils.checkInput("",
                        "Enter type(" + types + "): ",
                        "Invalid type(" + types.replace(".", " for").replace(",", " or") + ")!",
                        category.equals("0") ? "0,1,2,3,4,5" : "0,1,2,3,4");

                if (type.equals(category.equals("0") ? "5" : "4"))
                    break type_;

                product_: while (true) {
                    Map<Product, Integer> products = stockCatalogue
                            .filter(category.equals("0") ? Integer.parseInt(type) : 5 + Integer.parseInt(type));
                    StringBuilder chk = new StringBuilder();
                    int index = 0;

                    // print preview of products
                    for (Product product : products.keySet()) {
                        chk.append(index == products.keySet().size() ? index : index + ",");
                        System.out.println(
                                "\n" + index++ + ". " + product.getClass().getName() + " " + product.preview()
                                        + ", Stock: "
                                        + products.get(product));
                    }
                    System.out.println();

                    index--;

                    // get product from user
                    int product = Integer.parseInt(
                            Utils.checkInput("", "Enter index(0-" + index + ", or 100 to go back): ",
                                    "Invalid index(0-" + index + ", or 100 to go back)!",
                                    chk.toString() + ",100"));

                    if (product == 100)
                        break product_;

                    Product selected = (Product) products.keySet().toArray()[product];

                    // print selected product characteristics
                    System.out.println(selected + "\n");

                    choice_: while (true) {
                        String choice = Utils.checkInput("",
                                "Do you want to buy this product(0. yes, 1. no, 2. go back)? ",
                                "Invalid choice(0. yes, 1. no, 2. go back)!", "0,1,2");

                        if (choice.equals("2"))
                            break choice_;

                        if (choice.equals("1"))
                            break category_;

                        if (choice.equals("0")) {
                            @SuppressWarnings("resource")
                            Scanner sc = new Scanner(System.in);
                            String choice1 = null;

                            // get sale-order info
                            choice1_: while (true) {
                                if (!(products.get(selected) >= 1)) {
                                    choice1 = Utils.checkInput("",
                                            "This product is out or stock.Do you want to order it(0. yes, 1. no, 2. go back)? ",
                                            "Invalid choice(0. yes, 1. no, 2. go back)!", "0,1,2");

                                    if (Objects.equals(choice1, "1"))
                                        break category_;
                                    if (choice1.equals("2"))
                                        break choice1_;
                                }

                                System.out.println("Enter your name: ");
                                String name = sc.nextLine();

                                System.out.println("Enter your number: ");
                                String number = sc.next();

                                // check if stock is available else try to order
                                if (products.get(selected) >= 1) {
                                    // sale
                                    salesCatalogue.addSale(new Sale(selected, name, number));
                                    stockCatalogue.put(selected, products.get(selected) - 1);

                                    break category_;
                                } else {
                                    if (Objects.equals(choice1, "0")) {
                                        // order
                                        String date = Utils.checkDate("Enter delivery date: ",
                                                "Invalid delivery date!");
                                        ordersCatalogue.addOrder(new Order(selected, name, number, date));

                                        break category_;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return List.of(stockCatalogue, ordersCatalogue, salesCatalogue);
    }

    /**
     * Action: Runs when user selected action is overview of orders. Takes index of order to overview
     * from the user as input, prints order and enables the user to deliver the order.
     * Note: For the go back option it uses labelled loops
     * @param stockCatalogue program's stock catalogue
     * @param ordersCatalogue program's orders catalogue
     * @param salesCatalogue program's sales catalogue
     * @return List of Catalogues: returns the updated catalogues as a list after modifying them
     */
    static List<Catalogue> overviewOrders(StockCatalogue stockCatalogue, OrdersCatalogue ordersCatalogue,
            SalesCatalogue salesCatalogue) {
        List<Order> orderCatalogueLocal = ordersCatalogue.getOrderCatalogue();
        int index = 0;
        StringBuilder chk = new StringBuilder();

        for (Order order : orderCatalogueLocal) {
            // print order catalogue
            System.out.println(index + ". " + order.getClass().getName() + ", code: " + order.code + "\n");

            chk.append(index).append(",");
            index++;
        }

	// check format: 0,1 0,1,2 ...
	if(chk.length() >= 2 && chk.length() % 2 == 0)
        	chk.deleteCharAt(chk.length() - 1);

        if (index == 0)
            return List.of(stockCatalogue, ordersCatalogue, salesCatalogue);
        index--;

        choice_: while (true) {
            int choice = Integer.parseInt(
                    Utils.checkInput("", "Enter index(0-" + index + ", or 100 to go back): ",
                            "Invalid index(0-" + index + " or 100 to go back)!",
                            chk.toString() + ",100"));

            if (choice == 100)
                break choice_;

            // print selected order characteristics
            System.out.println("\n" + orderCatalogueLocal.get(choice) + "\n");

            deliver_: while (true) {
                String deliver = Utils.checkInput("", "Do you want to deliver this order(0. yes, 1. no, 2. go back)? ",
                        "Invalid choice(0. yes, 1. no, 2. go back)!", "0,1,2");

                if (deliver.equals("2"))
                    break deliver_;
                if (deliver.equals("1"))
                    break choice_;

                if (deliver.equals("0")) {
                    orderCatalogueLocal.get(choice).setDeliveryStatus(DeliveryStatus.DELIVERED);

                    Order order = orderCatalogueLocal.get(choice);
                    salesCatalogue.addSale(new Sale(order.product, order.name, order.number));

                    // remove the order from the ordersCatalogue and place it in the salesCatalogue
                    ordersCatalogue.remove(orderCatalogueLocal.get(choice).code);

                    break choice_;
                }
            }
        }

        return List.of(stockCatalogue, ordersCatalogue, salesCatalogue);
    }
}
