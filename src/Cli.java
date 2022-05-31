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
        // Create a list with the products in stock
        Map<Product, Integer> products = new HashMap<>();
        products.put(new Motherboard("1", 2000, "msi", 100, Socket.AMD, 32, 4), 0);
        products.put(new Motherboard("2", 2001, "asrock", 200, Socket.INTEL, 64, 6), 1);
        products.put(new Ram("3", 2002, "patriot", 50, Ddr.DDR3, 4, 1600), 1);
        products.put(new Ram("4", 2003, "corsair", 100, Ddr.DDR4, 8, 2666), 0);
        products.put(new Cpu("5", 2004, "amd", 150, 2.8, 6, true), 0);
        products.put(new Cpu("6", 2005, "intel", 250, 3.3, 8, false), 1);
        products.put(new Gpu("7", 2006, "nvidia", 350, ChipsetManufacturer.NVIDIA, 6), 1);
        products.put(new Gpu("8", 2007, "amd", 700, ChipsetManufacturer.AMD, 8), 0);
        products.put(new Hd("9", 2008, "western digital", 30, StorageType.HDD, 1.8, 256), 0);
        products.put(new Hd("10", 2009, "kingston", 80, StorageType.SSD, 2.5, 1), 1);
        products.put(new Monitor("11", 2010, "aoc", 250, MonitorType.MONITOR, 24, "1920x1080",
                new Port[] { Port.HDMI, Port.DP }), 1);
        products.put(new Monitor("12", 2011, "turbox", 350, MonitorType.TV, 27, "2560x1440",
                new Port[] { Port.USBC, Port.HDMI }), 0);
        products.put(new Mouse("13", 2012, "logitech", 75, Sensor.OPTICAL, Connection.WIRED), 0);
        products.put(new Mouse("14", 2013, "razer", 150, Sensor.LASER, Connection.WIRELESS), 1);
        products.put(new Keyboard("15", 2014, "hyperx", 200, Connection.WIRED), 1);
        products.put(new Keyboard("16", 2015, "epomaker", 250, Connection.WIRELESS), 0);
        products.put(new Printer("17", 2016, "hp", 500, PrinterType.INKJET, Colors.BLACKNWHITE), 0);
        products.put(new Printer("18", 2017, "epson", 1000, PrinterType.LASER, Colors.COLORED), 1);

        // initialize our stock, order and sale catalogues
        StockCatalogue stockCatalogue = new StockCatalogue(products);
        OrdersCatalogue ordersCatalogue = new OrdersCatalogue();
        SalesCatalogue salesCatalogue = new SalesCatalogue();

        // choice -> gets the input from user
        // res -> fetches the result from user selected function
        int choice;
        List<Catalogue> res = List.of(stockCatalogue, ordersCatalogue, salesCatalogue);

        logicLoop: while (true) {
            // print main menu, take input
            choice = Integer
                    .parseInt(Utils.checkInput(
                            "\n0. Overview products\n1. Overview orders\n2. Overview sales\n3. Exit\n\n",
                            "Enter action(0-3): ", "Invalid action(0-3)!", "0,1,2,3"));

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
                default -> System.out.println("Invalid command.\n");
            }

            ordersCatalogue = (OrdersCatalogue) res.get(1);
            salesCatalogue = (SalesCatalogue) res.get(2);
        }

        try {
            System.in.close();
        } catch (IOException ignored) {
        }
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
