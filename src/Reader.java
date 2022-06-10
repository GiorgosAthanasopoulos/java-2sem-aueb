import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
		String fileName = catalogueClassName + ".txt";
		File file = new File(fileName);
		
		System.out.println("Reading file: " + fileName);
		
		return switch (catalogueClassName) {
			case "StockCatalogue" -> readStockCatalogue(file);
			case "OrdersCatalogue" -> readOrdersCatalogue(file);
			case "SalesCatalogue" -> readSalesCatalogue(file);
			default -> null;
		};
	}
	
	private static Catalogue readStockCatalogue(File stockCatalogue) {
		Map<Product, Integer> stock = new HashMap<>();
		String input, value;
		
		//noinspection rawtypes
		List<Class> itemTypes = new ArrayList<>();
		List<String> models = new ArrayList<>();
		List<Integer> years = new ArrayList<>();
		List<String> manufacturers = new ArrayList<>();
		List<Double> prices = new ArrayList<>();
		List<Integer> pieces = new ArrayList<>();
		List<Double> clocks = new ArrayList<>();
		List<Integer> cores = new ArrayList<>();
		List<Boolean> onboardGraphics = new ArrayList<>();
		List<Ddr> ddrs = new ArrayList<>();
		List<Integer> sizes = new ArrayList<>();
		List<Double> frequencies = new ArrayList<>();
		List<ChipsetManufacturer> chipsets = new ArrayList<>();
		List<Integer> memories = new ArrayList<>();
		List<Socket> sockets = new ArrayList<>();
		List<Integer> sataCounts = new ArrayList<>();
		List<StorageType> storageTypes = new ArrayList<>();
		List<Integer> capacities = new ArrayList<>();
		List<PrinterType> printerTypes = new ArrayList<>();
		List<Colors> colors = new ArrayList<>();
		List<Sensor> sensors = new ArrayList<>();
		List<Connection> connections = new ArrayList<>();
		List<MonitorType> monitorTypes = new ArrayList<>();
		List<String> resolutions = new ArrayList<>();
		List<String[]> ports = new ArrayList<>();
		List<Backlight> backlights = new ArrayList<>();
		
		try {
			Scanner sc = new Scanner(stockCatalogue);
			
			while(sc.hasNextLine()) {
				input = sc.nextLine().trim().strip().stripIndent().stripLeading().stripTrailing();
				value = input.split(" ")[1];
				
				if(input.equals("ITEM_TYPE")) {
					if(value.contains("motherboard"))
						itemTypes.add(Motherboard.class);
					else if(value.contains("cpu"))
						itemTypes.add(Cpu.class);
					else if(value.contains("ram"))
						itemTypes.add(Ram.class);
					else if(value.contains("gpu"))
						itemTypes.add(Gpu.class);
					else if(value.contains("hd"))
						itemTypes.add(Hd.class);
					else if(value.contains("monitor"))
						itemTypes.add(Monitor.class);
					else if(value.contains("keyboard"))
						itemTypes.add(Keyboard.class);
					else if(value.contains("mouse"))
						itemTypes.add(Mouse.class);
					else if(value.contains("printer"))
						itemTypes.add(Printer.class);
				} if(input.startsWith("MODEL")) {
					models.add(value);
				} else if(input.equals("MODEL_YEAR")) {
					years.add(Integer.parseInt(value));
				} else if(input.equals("MANUFACTURER")) {
					manufacturers.add(value);
				} else if(input.equals("PRICE")) {
					prices.add(Double.parseDouble(value));
				} else if(input.equals("PIECES")) {
					pieces.add(Integer.parseInt(value));
				} else if(input.equals("CLOCK")) {
					clocks.add(Double.parseDouble(value));
				} else if(input.equals("CORES")) {
					cores.add(Integer.parseInt(value));
				} else if(input.equals("ONBOARD_GRAPHICS")) {
					onboardGraphics.add(value.equals("yes"));
				} else if(input.equals("DDR")) {
					ddrs.add(value.contains("3") ? Ddr.DDR3 : value.contains("4") ? Ddr.DDR4 : Ddr.DDR5);
				} else if(input.equals("SIZE")) {
					sizes.add(Integer.parseInt(value));
				} else if(input.equals("FREQUENCY")) {
					frequencies.add(Double.parseDouble(value));
				} else if(input.equals("CHIPSET")) {
					chipsets.add(value.contains("nvidia") ? ChipsetManufacturer.NVIDIA : ChipsetManufacturer.AMD);
				} else if(input.equals("MEMORY")) {
					memories.add(Integer.parseInt(value));
				} else if(input.equals("SOCKET")) {
					sockets.add(value.contains("amd") ? Socket.AMD : Socket.INTEL);
				} else if(input.equals("SATA_COUNT")) {
					sataCounts.add(Integer.parseInt(value));
				} else if(input.equals("STORAGE_TYPE")) {
					storageTypes.add(value.contains("HDD") ? StorageType.HDD : StorageType.SSD);
				} else if(input.equals("CAPACITY")) {
					capacities.add(Integer.parseInt(value));
				} else if(input.equals("PRINTER_TYPE")) {
					printerTypes.add(value.contains("inkjet") ? PrinterType.INKJET : PrinterType.LASER);
				} else if(input.equals("COLORS")) {
					colors.add(value.contains("colored") ? Colors.COLORED : Colors.BLACKNWHITE);
				} else if(input.equals("SENSOR")) {
					sensors.add(value.contains("laser") ? Sensor.LASER : Sensor.OPTICAL);
				} else if(input.equals("CONNECTION")) {
					connections.add(value.contains("wireless") ? Connection.WIRELESS : Connection.WIRED);
				} else if(input.equals("MONITOR_TYPE")) {
					monitorTypes.add(value.contains("monitor") ? MonitorType.MONITOR : value.contains("tv") ? MonitorType.TV : MonitorType.PORTABLE);
				} else if(input.equals("RESOLUTION")) {
					resolutions.add(value);
				} else if(input.equals("PORTS")) {
					ports.add(value.split(","));
				} else if(input.equals("BACKLIGHT")) {
					backlights.add(value.contains("lcd") ? Backlight.LCD : Backlight.LED);
				}
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return new StockCatalogue(stock);
	}
	
	private static Catalogue readOrdersCatalogue(File ordersCatalogue) {
		List<Order> orderList = new ArrayList<>();
		
		try {
			Scanner sc = new Scanner(ordersCatalogue);
			
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return new OrdersCatalogue(orderList);
	}
	
	private static Catalogue readSalesCatalogue(File salesCatalogue) {
		List<Sale> saleList = new ArrayList<>();
		
		try {
			Scanner sc = new Scanner(salesCatalogue);
			
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return new SalesCatalogue(saleList);
	}
}
