import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A class that will read catalogues from the filesystem and transform them into
 * java objects
 */
public class Reader {
	/**
	 * A method that will read catalogues from the filesystem and transform them
	 * into java objects
	 */
	public static Catalogue readCatalogue(String catalogueClassName) {
		String extension = ".txt";
		String fileName = catalogueClassName + extension;
		File catalogueFile = new File(fileName);

		if (!catalogueFile.exists()) {
			System.out.println("Catalogue file: " + catalogueClassName + " does not exist!");
		} else {
			System.out.printf("Reading file: %s%n", fileName);

			return switch (catalogueClassName) {
				case "StockCatalogue" -> readStockCatalogue(catalogueFile);
				case "OrdersCatalogue" -> readOrdersCatalogue(catalogueFile);
				case "SalesCatalogue" -> readSalesCatalogue(catalogueFile);
			};
		}

		return null;
	}

	private static Catalogue readStockCatalogue(File stockCatalogue) {
		Scanner sc = null;
		String inp, prev = "";
		HashMap<Product, Integer> stock = new HashMap<>();
		int line = 0;

		try {
			sc = new Scanner(stockCatalogue);

			while(sc.hasNextLine()) {
				inp = sc.nextLine().trim().strip();

				if(line == 0 && !inp.equals("ITEM_LIST") ||
				line == 1 && !inp.equals("{") ||
				!sc.hasNextLine() && !inp.equals("}") ||
				inp.equals(prev)) {
					System.out.println("Invalid format in file: " + stockCatalogue.getName());
					break;
				}

				line++;
				prev = inp;
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		sc.close();
		return new StockCatalogue(stock);
	}

	private static Catalogue readOrdersCatalogue(File ordersCatalogue) {
		return new OrdersCatalogue();
	}

	private static Catalogue readSalesCatalogue(File salesCatalogue) {
		return new SalesCatalogue();
	}
}
