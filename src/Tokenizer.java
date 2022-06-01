/**
 * Utility to validate tokens parsed from Parser class
 */
public class Tokenizer {
    String[] genericTokens = new String[] {
            "{", "}",

            "ITEM_TYPE",
            "MODEL",
            "PRICE"
    };

    String[] stockTokens = new String[] {
            "ITEM_LIST",
            "ITEM",

            "MODEL_YEAR",
            "MANUFACTURER",
            "PIECES"
    };

    String[] motherboardTokens = new String[] {
            "SOCKET",
            "MEMORY",
            "SATA_COUNT"
    };

    String[] cpuTokens = new String[] {
            "CLOCK",
            "CORES",
            "ONBOARD_GRAPHICS"
    };

    String[] ramTokens = new String[] {
            "DDR",
            "SIZE",
            "FREQUENCY"
    };

    String[] gpuTokens = new String[] {
            "CHIPSET",
            "MEMORY"
    };

    String[] hdTokens = new String[] {
            "STORAGE_TYPE",
            "SIZE",
            "CAPACITY"
    };

    String[] monitorTokens = new String[] {
            "MONITOR_TYPE",
            "SIZE",
            "RESOLUTION",
            "PORTS",
            "BACKLIGHT"
    };

    String[] keyboardTokens = new String[] {
            "CONNECTION"
    };

    String[] mouseTokens = new String[] {
            "CONNECTION",
            "SENSOR"
    };

    String[] printerTokens = new String[] {
            "PRINTER_TYPE",
            "COLORS"
    };

    String[] transactionTokens = new String[] {
            "NAME",
            "NUMBER"
    };

    String[] orderTokens = new String[] {
            "ORDER_LIST",
            "ORDER",

            "ORDER_DATE",
            "DELIVERY_DATE",
            "DELIVERY_STATUS"
    };

    String[] saleTokens = new String[] {
            "SALES_LIST",
            "SALE",

            "SALE_DATE"
    };
}
