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
	public static StockCatalogue readCatalogue(String catalogueClassName) {
		String fileName = catalogueClassName + ".txt";
		File file = new File(fileName);
		
		System.out.println("Reading file: " + fileName);
	
		return readStockCatalogue(file);
	}
	
	public static Catalogue readCatalogue(String catalogueClassName, StockCatalogue stockCatalogue) {
		String fileName = catalogueClassName + ".txt";
		File file = new File(fileName);
		
		System.out.println("Reading file: " + fileName);
		
		return switch (catalogueClassName) {
			case "StockCatalogue" -> readStockCatalogue(file);
			case "OrdersCatalogue" -> readOrdersCatalogue(file, stockCatalogue);
			case "SalesCatalogue" -> readSalesCatalogue(file, stockCatalogue);
			default -> null;
		};
	}
	
	@SuppressWarnings("all")
	private static StockCatalogue readStockCatalogue(File stockCatalogue) {
		// check file format valid
		if(!valid(stockCatalogue, new String[]{"ITEM_TYPE", "MODEL", "MODEL_YEAR", "MANUFACTURER", "PRICE", "PIECES", "CLOCK", "CORES", "ONBOARD_GRAPHICS", "DDR", "SIZE", "FREQUENCY", "CHIPSET", "MEMORY", "SOCKET", "SATA_COUNT", "STORAGE_TYPE", "CAPACITY", "PRINTER_TYPE", "COLORS", "SENSOR", "CONNECTION", "MONITOR_TYPE", "RESOLUTION", "PORTS", "BACKLIGHT"}, "ITEM_LIST", "ITEM"))
			throw new RuntimeException("File format in: " + stockCatalogue.getName() + " is invalid!");
		
		// stock map that will be used to create stock catalogue generated from file
		Map<Product, Integer> stock = new HashMap<>();
		String input, value = "", context = null, key;
	
		// stacks to hold product characteristics
		Stack[] stacks = new Stack[26];
		Boolean[] provided = new Boolean[26];
		
		//init arrays
		for(int i=0; i<26; i++) {
			stacks[i] = new Stack();
			provided[i] = false;
		}
		
		boolean needToRefreshProvide = false;
		
		// fill stacks
		try {
			Scanner sc = new Scanner(stockCatalogue);
			
			while(sc.hasNextLine()) {
				key = sc.nextLine().trim().strip().stripIndent().stripLeading().stripTrailing();
				
				if(key.equals("ITEM_LIST") || key.equals("ITEM") || key.equals(" ") || key.equals(""))
					continue;
				else if(key.equals("}")) {
					needToRefreshProvide = true;
					continue;
				} else if(key.equals("{")) {
					needToRefreshProvide = false;
					continue;
				}
				
				input = key.split(" ")[0];
				
				for(int i=1; i<key.split(" ").length; i++)
					value += key.split(" ")[i];
				
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
						stacks[0].push(Mouse.class);
					else if(value.contains("printer"))
						stacks[0].push(Printer.class);
				} else if(input.equals("MODEL")) {
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
					stacks[10].push(Double.parseDouble(value));
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
				
				// check important information provided
				if(!provided[0] && !provided[1] && !provided[4]) {
					System.out.println("Not enough information was given for a product to be added to the stock so it is ignored!");
				
					for(int i=0; i<26; i++)
						if(provided[i])
							stacks[i].pop();
					
					continue;
				}
				
				// check generic product attributes provided
				if(!provided[2])
					stacks[2].push(0);
				if(!provided[3])
					stacks[3].push("");
				if(!provided[5])
					stacks[5].push(0);
				
				// check specific product attributes
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
				
				if(needToRefreshProvide)
					for(int i=0; i<26; i++)
						provided[i] = false;
				
				value = "";
			}
			
			// generate products
			for(int i=stacks[0].size()-1; i>=0; i--) {
				String class_ = stacks[0].get(i).toString();
				
				if(class_.endsWith("Motherboard"))
					stock.put(new Motherboard((String) stacks[1].pop(),(Integer) stacks[2].pop(),(String) stacks[3].pop(),(Double) stacks[4].pop(),(Socket) stacks[14].pop(),(Integer) stacks[13].pop(),(Integer) stacks[15].pop()), (Integer) stacks[5].pop());
				 else if(class_.endsWith("Ram"))
					stock.put(new Ram((String) stacks[1].pop(),(Integer) stacks[2].pop(),(String) stacks[3].pop(),(Double) stacks[4].pop(),(Ddr) stacks[9].pop(),(Double) stacks[10].pop(),(Double) stacks[11].pop()), (Integer) stacks[5].pop());
				 else if(class_.endsWith("Cpu"))
					stock.put(new Cpu((String) stacks[1].pop(),(Integer) stacks[2].pop(),(String) stacks[3].pop(),(Double) stacks[4].pop(),(Double) stacks[6].pop(),(Integer) stacks[7].pop(),(Boolean) stacks[8].pop()), (Integer) stacks[5].pop());
				 else if(class_.endsWith("Hd"))
					stock.put(new Hd((String) stacks[1].pop(),(Integer) stacks[2].pop(),(String) stacks[3].pop(),(Double) stacks[4].pop(),(StorageType) stacks[16].pop(),(Double) stacks[10].pop(),(Integer) stacks[17].pop()), (Integer) stacks[5].pop());
				 else if(class_.endsWith("Gpu"))
					stock.put(new Gpu((String) stacks[1].pop(),(Integer) stacks[2].pop(),(String) stacks[3].pop(),(Double) stacks[4].pop(),(ChipsetManufacturer) stacks[12].pop(),(Integer) stacks[13].pop()), (Integer) stacks[5].pop());
				 else if(class_.endsWith("Monitor")) {
					String[] portsString = (String[]) stacks[24].pop();
					String[] portString = Arrays.toString(portsString).replace("[","").replace("]","").split(",");
					Port[] ports = new Port[portString.length];
					
					for(int j=0; j<ports.length; j++)
						if(portString[j].toLowerCase().contains("hdmi"))
							ports[j] = Port.HDMI;
						else if(portString[j].toLowerCase().contains("dp"))
							ports[j] = Port.DP;
						else
							ports[j] = Port.USBC;
					
					stock.put(new Monitor((String) stacks[1].pop(),(Integer) stacks[2].pop(),(String) stacks[3].pop(),(Double) stacks[4].pop(),(MonitorType) stacks[22].pop(),(Double) stacks[10].pop(),(String) stacks[23].pop(), ports, (Backlight) stacks[25].pop()), (Integer) stacks[5].pop());
				} else if(class_.endsWith("Keyboard"))
					stock.put(new Keyboard((String) stacks[1].pop(),(Integer) stacks[2].pop(),(String) stacks[3].pop(),(Double) stacks[4].pop(),(Connection) stacks[21].pop()), (Integer) stacks[5].pop());
				 else if(class_.endsWith("Mouse"))
					stock.put(new Mouse((String) stacks[1].pop(),(Integer) stacks[2].pop(),(String) stacks[3].pop(),(Double) stacks[4].pop(),(Sensor) stacks[20].pop(),(Connection) stacks[21].pop()), (Integer) stacks[5].pop());
				 else if(class_.endsWith("Printer"))
					stock.put(new Printer((String) stacks[1].pop(),(Integer) stacks[2].pop(),(String) stacks[3].pop(),(Double) stacks[4].pop(),(PrinterType) stacks[18].pop(),(Colors) stacks[19].pop()), (Integer) stacks[5].pop());
				
			}
			
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new StockCatalogue(stock);
	}
	
	@SuppressWarnings("unused, unchecked, rawtypes")
	private static OrdersCatalogue readOrdersCatalogue(File ordersCatalogue, StockCatalogue stockCatalogue) {
		if(!valid(ordersCatalogue, new String[]{"CODE", "ITEM_TYPE", "MODEL", "NAME", "NUMBER", "DATE", "PRICE", "DELIVERY_DATE", "DELIVERY_STATUS"}, "ORDER_LIST", "ORDER"))
			throw new RuntimeException("File format in : " + ordersCatalogue.getName() + " is invalid!");
	
		List<Order> orderList = new ArrayList<>();
		String input, value = "", key;
		
		Stack[] stacks = new Stack[9];
		Boolean[] provided = new Boolean[9];
		
		for (int i=0; i<9; i++) {
			stacks[i] = new Stack();
			provided[i] = false;
		}
		
		boolean needToRefreshProvide = false;
		
		try {
			Scanner sc = new Scanner(ordersCatalogue);
			
			while(sc.hasNextLine()) {
				key = sc.nextLine().trim().strip().stripIndent().stripLeading().stripTrailing();
				
				switch (key) {
					case "SALES_LIST":
					case "SALE":
					case " ":
					case "":
						continue;
					case "}":
						needToRefreshProvide = true;
						continue;
					case "{":
						needToRefreshProvide = false;
						continue;
				}
				
				input = key.split(" ")[0];
				
				for(int i=1; i<key.split(" ").length; i++)
					value += key.split(" ")[i];
				
				switch (input) {
					case "CODE" -> {
						provided[0] = true;
						
						int code =  Integer.parseInt(value);
						int max = -1;
					
						for(Product product : stockCatalogue.getCatalogue().keySet())
							if(product.code > max)
								max = product.code;
						
						stacks[0].push(max == code ? ++code : code);
					}
					case "ITEM_TYPE" -> {
						provided[1] = true;
						if (value.contains("motherboard"))
							stacks[1].push(Motherboard.class);
						else if (value.contains("cpu"))
							stacks[1].push(Cpu.class);
						else if (value.contains("ram"))
							stacks[1].push(Ram.class);
						else if (value.contains("gpu"))
							stacks[1].push(Gpu.class);
						else if (value.contains("hd"))
							stacks[1].push(Hd.class);
						else if (value.contains("monitor"))
							stacks[1].push(Monitor.class);
						else if (value.contains("keyboard"))
							stacks[1].push(Keyboard.class);
						else if (value.contains("mouse"))
							stacks[1].push(Mouse.class);
						else if (value.contains("printer"))
							stacks[1].push(Printer.class);
					}
					case "MODEL" -> {
						provided[2] = true;
						stacks[2].push(value);
					}
					case "NAME" -> {
						provided[3] = true;
						stacks[3].push(value);
					}
					case "NUMBER" -> {
						provided[4] = true;
						stacks[4].push(value);
					}
					case "DATE" -> {
						provided[5] = true;
						stacks[5].push(value);
					}
					case "PRICE" -> {
						provided[6] = true;
						stacks[6].push(Double.parseDouble(value));
					}
					case "DELIVERY_DATE" -> {
						provided[7] = true;
						stacks[7].push(value);
					}
					case "DELIVERY_STATUS" -> {
						provided[8] = true;
						stacks[8].push(value);
					}
				}
				
				if(needToRefreshProvide)
					for(int i=0; i<9; i++)
						provided[i] = false;
				
				value = "";
			}
			
			main:
			for(int i=stacks[0].size()-1; i>=0; i--) {
				String class_ = stacks[1].get(i).toString();
				String model = (String) stacks[2].pop();
				Map<Product, Integer> productList = stockCatalogue.getCatalogue();
				boolean found = false;
				Product desiredProduct = null;
				
				for(int j=0; j<provided.length; j++)
					if(!provided[i]) {
						System.out.println("Not enough information was given for product of class " + class_ + " so it is ignored!");
						continue main;
					}
				
				for(Product product : productList.keySet())
					if(product.model.equals(model) && product.getClass().toString().equals(class_)) {
						found = true;
						desiredProduct = product;
					}
				
				if(!found)
					System.out.println("Product with model: " + model + " was not found.Skipping...");
				else
					orderList.add(new Order((Integer) stacks[0].pop(), desiredProduct, (String) stacks[3].pop(), (String) stacks[4].pop(), (String) stacks[5].pop(), (Double) stacks[6].pop(), (String) stacks[7].pop(), stacks[8].pop().equals("TOBEDELIVERED") ? DeliveryStatus.TOBEDELIVERED : DeliveryStatus.DELIVERED));
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return new OrdersCatalogue(orderList);
	}
	
	@SuppressWarnings("rawtypes, unchecked")
	private static SalesCatalogue readSalesCatalogue(File salesCatalogue, StockCatalogue stockCatalogue) {
		if(!valid(salesCatalogue, new String[]{"CODE", "ITEM_TYPE", "MODEL", "NAME", "NUMBER", "SALE_DATE", "PRICE"}, "SALES_LIST", "SALE"))
			throw new RuntimeException("File format in: " + salesCatalogue.getName() + " is invalid!");
		
		List<Sale> salesList = new ArrayList<>();
		String input, value = "", key;
		
		Stack[] stacks = new Stack[7];
		Boolean[] provided = new Boolean[7];
		
		for(int i=0; i<7; i++) {
			stacks[i] = new Stack();
			provided[i] = false;
		}
		
		boolean needToRefreshProvide = false;
		
		try {
			Scanner sc = new Scanner(salesCatalogue);
		
			while (sc.hasNextLine()) {
				key = sc.nextLine().trim().strip().stripIndent().stripLeading().stripTrailing();
				
				switch (key) {
					case "SALES_LIST":
					case "SALE":
					case " ":
					case "":
						continue;
					case "}":
						needToRefreshProvide = true;
						continue;
					case "{":
						needToRefreshProvide = false;
						continue;
				}
				
				input = key.split(" ")[0];
				
				for(int i=1; i<key.split(" ").length; i++)
					value += key.split(" ")[i];
				
				switch (input) {
					case "CODE" -> {
						provided[6] = true;
						
						int code =  Integer.parseInt(value);
						int max = -1;
						
						for(Product product : stockCatalogue.getCatalogue().keySet())
							if(product.code > max)
								max = product.code;
						
						stacks[6].push(max == code ? ++code : code);
					}
					case "ITEM_TYPE" -> {
						provided[0] = true;
						if (value.contains("motherboard"))
							stacks[0].push(Motherboard.class);
						else if (value.contains("cpu"))
							stacks[0].push(Cpu.class);
						else if (value.contains("ram"))
							stacks[0].push(Ram.class);
						else if (value.contains("gpu"))
							stacks[0].push(Gpu.class);
						else if (value.contains("hd"))
							stacks[0].push(Hd.class);
						else if (value.contains("monitor"))
							stacks[0].push(Monitor.class);
						else if (value.contains("keyboard"))
							stacks[0].push(Keyboard.class);
						else if (value.contains("mouse"))
							stacks[0].push(Mouse.class);
						else if (value.contains("printer"))
							stacks[0].push(Printer.class);
					}
					case "MODEL" -> {
						provided[1] = true;
						stacks[1].push(value);
					}
					case "NAME" -> {
						provided[2] = true;
						stacks[2].push(value);
					}
					case "NUMBER" -> {
						provided[3] = true;
						stacks[3].push(value);
					}
					case "SALE_DATE" -> {
						provided[4] = true;
						stacks[4].push(value);
					}
					case "PRICE" -> {
						provided[5] = true;
						stacks[5].push(Double.parseDouble(value));
					}
				}
				
				if(needToRefreshProvide)
					for(int i=0; i<6; i++)
						provided[i] = false;
				
				value = "";
			}
			
			main:
			for(int i=stacks[0].size()-1; i>=0; i--) {
				String class_ = stacks[0].get(i).toString();
				String model = (String) stacks[1].pop();
				Map<Product, Integer> productList = stockCatalogue.getCatalogue();
				boolean found = false;
				Product desiredProduct = null;
				
				for(int j=0; j<provided.length; j++)
					if(!provided[i]) {
						System.out.println("Not enough information was given for product of class " + class_ + " so it is ignored!");
						continue main;
					}
				
				for(Product product : productList.keySet())
					if(product.model.equals(model) && product.getClass().toString().equals(class_)) {
						found = true;
						desiredProduct = product;
					}
				
				if(!found)
					System.out.println("Product with model: " + model + " was not found.Skipping...");
				else
					salesList.add(new Sale((Integer) stacks[6].pop(), desiredProduct, (String) stacks[2].pop(), (String) stacks[3].pop(), (String) stacks[4].pop(), (Double) stacks[5].pop()));
			}
			
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new SalesCatalogue(salesList);
	}
	
	private static boolean valid(File file, String[] keys, String start, String decr) {
		int openBrackets = 0, closeBrackets = 0, line = 1;
		boolean expectingOpenBracket = false, expectingCloseBracket = false, hadItemList = false, expectingItemListCloseBracket = false;
		
		try {
			Scanner sc = new Scanner(file);
			String inp, prev = "";
			
			while(sc.hasNextLine()) {
				inp = sc.nextLine().trim().strip().stripIndent().stripLeading().stripTrailing();
				
				if (" ".equals(inp) || "".equals(inp))
					continue;
				else if ("{".equals(inp)) {
					if (prev.equals(start)) {
						expectingItemListCloseBracket = true;
						expectingCloseBracket = false;
						expectingOpenBracket = false;
						openBrackets++;
					} else  if (expectingOpenBracket) {
						openBrackets++;
						expectingOpenBracket = false;
						expectingCloseBracket = true;
					} else
						throw new RuntimeException("Unexpected open bracket in file: " + file.getName() + ", line: " + line);
				} else if ("}".equals(inp))
					if(expectingCloseBracket) {
						closeBrackets++;
						expectingCloseBracket = false;
					} else if(expectingItemListCloseBracket) {
						closeBrackets++;
						expectingItemListCloseBracket = false;
					} else
						throw new RuntimeException("Unexpected close bracket in file: " + file.getName() + ", line: " + line);
				 else if (start.equals(inp))
					if(hadItemList)
						throw new RuntimeException("Token: " + start + " already exists in file: " + file.getName() + ", line: " + line);
					else {
						hadItemList = true;
						expectingOpenBracket = true;
						expectingCloseBracket = false;
					}
				else if (decr.equals(inp)) {
					expectingOpenBracket = true;
					expectingCloseBracket = false;
				} else
					if(Utils.contains(keys, inp.split(" ")[0]))
						throw new RuntimeException("Unexpected key: " + inp.split(" ")[0] + ", file: " + file.getName() + ", line: " + line);
					
                line++;
				prev = inp;
			}
			
			sc.close();
			return !(expectingCloseBracket || expectingOpenBracket || !hadItemList || closeBrackets != openBrackets || expectingItemListCloseBracket);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
