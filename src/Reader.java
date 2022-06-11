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
		if(!valid(stockCatalogue, new String[]{"ITEM_TYPE", "MODEL", "MODEL_YEAR", "MANUFACTURER", "PRICE", "PIECES", "CLOCK", "CORES", "ONBOARD_GRAPHICS", "DDR", "SIZE", "FREQUENCY", "CHIPSET", "MEMORY", "SOCKET", "SATA_COUNT", "STORAGE_TYPE", "CAPACITY", "PRINTER_TYPE", "COLORS", "SENSOR", "CONNECTION", "MONITOR_TYPE", "RESOLUTION", "PORTS", "BACKLIGHT"}))
			throw new RuntimeException("File format in: " + stockCatalogue.getName() + " is incorrect!Please fix the errors to continue");
		
		Map<Product, Integer> stock = new HashMap<>();
		String input, value, context = null, key;
	
		Stack[] stacks = new Stack[26];
		Boolean[] provided = new Boolean[26];
		
		for(int i=0; i<26; i++) {
			stacks[i] = new Stack();
			provided[i] = false;
		}
		
		try {
			Scanner sc = new Scanner(stockCatalogue);
			
			while(sc.hasNextLine()) {
				key = sc.nextLine().trim().strip().stripIndent().stripLeading().stripTrailing();
				input = key.split(" ")[0].trim().strip().stripIndent().stripLeading().stripTrailing();
				value = input.split(" ")[1].trim().strip().stripIndent().stripLeading().stripTrailing();
				
				if(input.equals("ITEM_TYPE")) {
					context = value;
					provided[0] = true;
					if(value.contains("motherboard"))
						stacks[0].push(Motherboard.class);
					else if(value.contains("cpu"))
						stacks[0].push(Cpu.class);
					else if(value.contains("ram"))
						stacks[0].push(Ram.class);
					else if(value.contains("gpu"))
						stacks[0].push(Gpu.class);
					else if(value.contains("hd"))
						stacks[0].push(Hd.class);
					else if(value.contains("monitor"))
						stacks[0].push(Monitor.class);
					else if(value.contains("keyboard"))
						stacks[0].push(Keyboard.class);
					else if(value.contains("mouse"))
						stacks[0]	.push(Mouse.class);
					else if(value.contains("printer"))
						stacks[0]	.push(Printer.class);
				} if(input.startsWith("MODEL")) {
					provided[1] = true;
					stacks[1].push(value);
				} else if(input.equals("MODEL_YEAR")) {
					provided[2] = true;
					stacks[2].push(Integer.parseInt(value));
				} else if(input.equals("MANUFACTURER")) {
					provided[3] = true;
					stacks[3].push(value);
				} else if(input.equals("PRICE")) {
					provided[4] = true;
					stacks[4].push(Double.parseDouble(value));
				} else if(input.equals("PIECES")) {
					provided[5] = true;
					stacks[5].push(Integer.parseInt(value));
				} else if(input.equals("CLOCK")) {
					provided[6] = true;
					stacks[6].push(Double.parseDouble(value));
				} else if(input.equals("CORES")) {
					provided[7] = true;
					stacks[7].push(Integer.parseInt(value));
				} else if(input.equals("ONBOARD_GRAPHICS")) {
					provided[8] = true;
					stacks[8].push(value.equals("yes"));
				} else if(input.equals("DDR")) {
					provided[9] = true;
					stacks[9].push(value.contains("3") ? Ddr.DDR3 : value.contains("4") ? Ddr.DDR4 : Ddr.DDR5);
				} else if(input.equals("SIZE")) {
					provided[10] = true;
					stacks[10].push(Integer.parseInt(value));
				} else if(input.equals("FREQUENCY")) {
					provided[11] = true;
					stacks[11].push(Double.parseDouble(value));
				} else if(input.equals("CHIPSET")) {
					provided[12] = true;
					stacks[12].push(value.contains("nvidia") ? ChipsetManufacturer.NVIDIA : ChipsetManufacturer.AMD);
				} else if(input.equals("MEMORY")) {
					provided[13] = true;
					stacks[13].push(Integer.parseInt(value));
				} else if(input.equals("SOCKET")) {
					provided[14] = true;
					stacks[14].push(value.contains("amd") ? Socket.AMD : Socket.INTEL);
				} else if(input.equals("SATA_COUNT")) {
					provided[15] = true;
					stacks[15].push(Integer.parseInt(value));
				} else if(input.equals("STORAGE_TYPE")) {
					provided[16] = true;
					stacks[16].push(value.contains("HDD") ? StorageType.HDD : StorageType.SSD);
				} else if(input.equals("CAPACITY")) {
					provided[17] = true;
					stacks[17].push(Integer.parseInt(value));
				} else if(input.equals("PRINTER_TYPE")) {
					provided[18] = true;
					stacks[18].push(value.contains("inkjet") ? PrinterType.INKJET : PrinterType.LASER);
				} else if(input.equals("COLORS")) {
					provided[19] = true;
					stacks[19].push(value.contains("colored") ? Colors.COLORED : Colors.BLACKNWHITE);
				} else if(input.equals("SENSOR")) {
					provided[20] = true;
					stacks[20].push(value.contains("laser") ? Sensor.LASER : Sensor.OPTICAL);
				} else if(input.equals("CONNECTION")) {
					provided[21] = true;
					stacks[21].push(value.contains("wireless") ? Connection.WIRELESS : Connection.WIRED);
				} else if(input.equals("MONITOR_TYPE")) {
					provided[22] = true;
					stacks[22].push(value.contains("monitor") ? MonitorType.MONITOR : value.contains("tv") ? MonitorType.TV : MonitorType.PORTABLE);
				} else if(input.equals("RESOLUTION")) {
					provided[23] = true;
					stacks[23].push(value);
				} else if(input.equals("PORTS")) {
					provided[24] = true;
					stacks[24].push(value.split(","));
				} else if(input.equals("BACKLIGHT")) {
					provided[25] = true;
					stacks[25].push(value.contains("lcd") ? Backlight.LCD : Backlight.LED);
				}
				
				if(!provided[0] && !provided[1] && !provided[4]) {
					System.out.println("Not enough information was given for a product to be added to the stock so it is ignored!");
				
					for(int i=0; i<26; i++)
						if(provided[i])
							stacks[i].pop();
					
					continue;
				}
				
				if(!provided[2])
					stacks[2].push(0);
				if(!provided[3])
					stacks[3].push("");
				if(!provided[5])
					stacks[5].push(0);
				
				switch (context) {
					case "motherboard":
						if(!provided[13])
							stacks[13].push(32);
						if(!provided[14])
							stacks[14].push(Socket.AMD);
						if(!provided[15])
							stacks[15].push(4);
							
						break;
					case "cpu":
						if(!provided[6])
							stacks[6].push(2.8);
						if(!provided[7])
							stacks[7].push(6);
						if(!provided[8])
							stacks[8].push(false);
						
						break;
					case "ram":
						if(!provided[9])
							stacks[9].push(Ddr.DDR3);
						if(!provided[10])
							stacks[10].push(4);
						if(!provided[11])
							stacks[11].push(1600);
						
						break;
					case "gpu":
						if(!provided[12])
							stacks[12].push(ChipsetManufacturer.NVIDIA);
						if(!provided[13])
							stacks[13].push(6);
						
						break;
					case "hd":
						if(!provided[10])
							stacks[10].push(3.5);
						if(!provided[16])
							stacks[16].push(StorageType.HDD);
						if(!provided[17])
							stacks[17].push(1);
						
						break;
					case "monitor":
						if(!provided[10])
							stacks[10].push(24);
						if(!provided[22])
							stacks[22].push(MonitorType.MONITOR);
						if(!provided[23])
							stacks[23].push("1920x1080");
						if(!provided[24])
							stacks[24].push(new Port[]{Port.HDMI, Port.DP});
						if(!provided[25])
							stacks[25].push(Backlight.LED);
						
						break;
					case "keyboard":
						if(!provided[21])
							stacks[21].push(Connection.WIRED);
						
						break;
					case "mouse":
						if(!provided[20])
							stacks[20].push(Sensor.OPTICAL);
						if(!provided[21])
							stacks[21].push(Connection.WIRED);
						
						break;
					case "printer":
						if(!provided[18])
							stacks[18].push(PrinterType.INKJET);
						if(!provided[19])
							stacks[19].push(Colors.COLORED);
						
						break;
				}
				
				for(int i=0; i<26; i++)
					provided[i] = false;
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
	
	private static boolean valid(File file, String[] keys) {
		int openBrackets = 0, closeBrackets = 0, line = 1;
		boolean expectingOpenBracket = false, expectingCloseBracket = false, hadItemList = false;
		
		try {
			Scanner sc = new Scanner(file);
			String inp;
			
			while(sc.hasNextLine()) {
				inp = sc.nextLine().trim().strip().stripIndent().stripLeading().stripTrailing();
				
				if(inp.equals(" ") || inp.equals(""))
					continue;
				else if(inp.equals("{"))
					if(!expectingOpenBracket)
						throw new RuntimeException("Didnt expect open bracket, file: " + file.getName() + ", line: " + line);
					else {
						openBrackets++;
						expectingOpenBracket = false;
						expectingCloseBracket = true;
					}
				else if(inp.equals("}"))
					if(!expectingCloseBracket)
						throw new RuntimeException("Didnt expect close bracket, file: " + file.getName() + ", line: " + line);
					else {
						closeBrackets++;
						expectingCloseBracket = false;
						expectingOpenBracket = false;
					}
				else if(inp.equals("ITEM_LIST"))
					if(hadItemList)
						throw new RuntimeException("Duplicate token ITEM_LIST, file: " + file.getName() + ", line: " + line);
					else {
						hadItemList = true;
						expectingOpenBracket = true;
						expectingCloseBracket = false;
					}
                else if(inp.equals("ITEM")) {
					expectingOpenBracket = true;
					expectingCloseBracket = false;
				}
				else {
					if(!contains(keys, inp.split(" ")[0]))
						throw new RuntimeException("Unknown value: " + inp.split(" ")[0]);
				}
					
                line++;
			}
			
			sc.close();
			return !expectingCloseBracket && !expectingOpenBracket && hadItemList && closeBrackets == openBrackets;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static boolean contains(String[] keys, String value) {
		for(String key : keys)
			if(key.equals(value))
				return true;
		
		return false;
	}
}
