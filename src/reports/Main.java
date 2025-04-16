package reports;

import java.io.*;
import java.util.*;

/**
 * Esta clase Main genera la creación de un archivo con la información de todos
 * los vendedores, de a uno por línea. Al frente del nombre de cada vendedor,
 * separado por punto y coma, debe estar la cantidad de dinero que recaudó según
 * los archivos. El archivo debe estar ordenado por cantidad de dinero, de mayor
 * a menor, de a un vendedor por línea. Es básicamente un archivo de reporte de
 * ventas de los vendedores, del mejor al peor; un archivo CSV.
 * 
 * Adicionalmente crea un archivo con la información de los productos vendidos
 * por cantidad, ordenados en forma descendente. Deben ir el nombre y el precio,
 * separados por punto y coma, y de a un producto por línea. Es básicamente un
 * archivo plano CSV
 * 
 * De igual forma contiene los metodos para la genración de los test
 * correspondientes que permiten validar el funcionamiento del programa.
 * 
 * El programa muestra un mensaje de finalización exitosa o un mensaje de error,
 * en caso de que algo salga mal.
 */
public class Main {

	/**
	 * Lista de productos obtenida del archivo escrito
	 */
	public static List<Product> products = new ArrayList<Product>();

	/**
	 * Lista de vendedores obtenida del archivo escrito
	 */
	public static List<Seller> sellers = new ArrayList<Seller>();

	/**
	 * Lista de ventas obtenidas de los archivos escritos
	 */
	public static List<Sale> sales = new ArrayList<Sale>();

	/**
	 * Metodo Main o ejecutable de la logica del codigo
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Ruta del archivo con la información de los vendedores
		String pathFileSellers = "files/input/sellers";
		processFilesInFolder(pathFileSellers);

		// Ruta del archivo con la información de los productos
		String pathFileProducts = "files/input/products";
		processFilesInFolder(pathFileProducts);

		// Ruta donde se encuentran los archivos con la información de las ventas
		String pathFileSales = "files/input/sales";
		processFilesInFolder(pathFileSales);

		// Generamos los test
		testFiles();

		System.out.println(
				"¡Finalización Exitosa de los test! Se generaron todos los archivos de test en la ruta: files/test/*");

		// Generamos el reporte total de ventas por cada vendedor
		generateSalesReport();

		// Generamos el reporte total de la cantidad de productos vendidos
		generateSalesProductsReport();

		System.out.println(
				"¡Finalización Exitosa de la clase Main! Se generaron todos los reportes en la ruta: files/output/*");

		System.out.println("¡No olvides de darle un Refresh a la carpeta raiz para visualizarlos!");

	}

	/**
	 * Metodo que permite leer linea por linea los archivos planos
	 * 
	 * EXTRA DEL PROYECTO: posibilidad de procesar más de un archivo por vendedor.
	 * dentro de éste método se realiza la modificación para que el sistema 
	 * pueda leer multiples archivos de ventas que pertenecen a un solo vendedor, el reporte
	 * total de ventas suma todos las ventas de los diferentes archivos asociados a un vendedor
	 * 
	 * @param nameFile
	 */
	public static void readFile(File nameFile) {

	    try {
	        BufferedReader bufferedReader = new BufferedReader(new FileReader(nameFile));
	        String line;

	        // Creamos una secuencia para poder settear un id al objeto
	        int sequence = 1;
	        int idSeller = 0;
	        //Ésta variable lee la primera línea con la información del vendedor de cada archivo de ventas  
	        boolean isFirstLine = true;

	        // Leer línea por línea
	        while ((line = bufferedReader.readLine()) != null) {

	            // Separamos por ";"
	            String[] partes = line.split(";");

	            // Filtramos por el archivo de vendedores
	            if (nameFile.toString().contains("sellers")) { 
	                sellers.add(mapperSellers(partes, sequence));
	                ++sequence;
	            // Filtramos por el archivo de productos
	            } else if (nameFile.toString().contains("products")) { 
	                products.add(mapperAvailableProducts(partes));
	            // Filtramos por el archivo de ventas
	            } else if (nameFile.toString().contains("sales")) { 
	            	// Identificamos el vendedor que esta en la primera linea
	            	if (isFirstLine && partes[0].contains("CC")) { 
	                    Seller seller = findSellerByDocumentNumber(sellers, partes[1]);
	                    // si el vendedor existe se guarda el ID en la variable idSeller
	                    if (seller != null) {
	                        idSeller = seller.getId();
	                    } else {
	                        System.out.println("No se encontró vendedor con documento: " + partes[1] + " en el archivo: " + nameFile.getName());
	                    }
	                    //se marca como false para indicar que ya se proceso la primera linea del archivo
	                    isFirstLine = false;
	                } else {
	                    // Solo procesamos líneas de venta si hemos identificado un vendedor
	                    if (idSeller > 0) {
	                        sales.add(mapperSales(partes, sequence, idSeller));
	                        ++sequence;
	                    }
	                }
	            }
	        }

	        // Limpiamos el buffered en memoria
	        bufferedReader.close();

	    } catch (IOException e) {
	        System.out.println("Error al leer el archivo: " + e.getMessage());
	    }
	}

	/**
	 * Metodo que crea un archivo con la información de todos los vendedores, de a
	 * uno por línea. Al frente del nombre de cada vendedor, separado por punto y
	 * coma, debe estar la cantidad de dinero que recaudó según los archivos. El
	 * archivo debe estar ordenado por cantidad de dinero, de mayor a menor, de a un
	 * vendedor por línea. Es básicamente un archivo de reporte de ventas de los
	 * vendedores, del mejor al peor; un archivo CSV.
	 */
	public static void generateSalesReport() {
		Seller seller = null;
		String pathGenerateFileSalesReport = "files/output/reporteTotalVentas.csv";
		Map<String, Double> salesPerSeller = new HashMap<>();

		// Recorremos la lista de ventas generadas
		for (Sale item : sales) {
			seller = findSellerById(sellers, item.getIdSeller());
			if (seller != null) {
				// Multiplicamos el precio unitario por la cantidad de producto vendido
				double valueSale = item.getProductAmount()
						* findProductById(products, item.getProduct().getId()).getUnitPrice();

				String key = seller.getName() + ";" + seller.getLastName();

				// Acumulamos la venta por vendedor
				salesPerSeller.put(key, salesPerSeller.getOrDefault(key, 0.0) + valueSale);
			}
		}

		// Creamos una lista con las entradas del Map
		List<Map.Entry<String, Double>> orderedList = new ArrayList<>(salesPerSeller.entrySet());

		// Ordenamos con Comparator clasico (de mayor a menor)
		Collections.sort(orderedList, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> valueOne, Map.Entry<String, Double> valueTwo) {
				return Double.compare(valueTwo.getValue(), valueOne.getValue());
			}
		});

		List<String> lineas = new ArrayList<>();
		// Incluimos una cabezera para identificar cada columna en el archivo
		String header = "NOMBRE;APELLIDO;TOTAL VENTAS";
		lineas.add(header);

		// Usamos un for each para poder recorrer la lista ordenada
		for (Map.Entry<String, Double> entry : orderedList) {
			String finalLine = entry.getKey() + ";" + entry.getValue();
			lineas.add(finalLine);
		}

		// Re-utilizamos el metodo writeFiles de la clase GenerateInfoFiles
		GenerateInfoFiles.writeFiles(pathGenerateFileSalesReport, lineas);
	}

	/**
	 * Metodo que crea un archivo con la información de los productos vendidos por
	 * cantidad, ordenados en forma descendente. Deben ir el nombre y el precio,
	 * separados por punto y coma, y de a un producto por línea. Es básicamente un
	 * archivo plano CSV.
	 */
	public static void generateSalesProductsReport() {
		Map<Integer, Integer> saleAmountProducts = new HashMap<Integer, Integer>();
		Map<String, Integer> salesPerAmountProduct = new HashMap<>();
		String pathGenerateFileSalesProductReport = "files/output/reporteCantidadTotalProductosVendidos.csv";

		// Recorremos la lista de ventas generadas
		for (Sale sale : sales) {
			// Buscamos el producto por el id
			Product product = findProductById(products, sale.getProduct().getId());
			if (!saleAmountProducts.containsKey(product.getId())) {
				// Si no encontramos el producto en el Map lo agregamos con la cantidad inicial
				saleAmountProducts.put(product.getId(), sale.getProductAmount());
			} else {
				// Si encontramos el producto en el Map unicamente sumamos la cantidad
				saleAmountProducts.merge(product.getId(), sale.getProductAmount(), Integer::sum);

			}
			String key = product.getName() + ";" + product.getUnitPrice();
			salesPerAmountProduct.put(key, saleAmountProducts.get(product.getId()));
		}

		// Creamos una lista con las entradas del Map
		List<Map.Entry<String, Integer>> orderedList = new ArrayList<>(salesPerAmountProduct.entrySet());

		// Ordenamos con Comparator clasico (de mayor a menor)
		Collections.sort(orderedList, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> valueOne, Map.Entry<String, Integer> valueTwo) {
				return Integer.compare(valueTwo.getValue(), valueOne.getValue());
			}
		});

		List<String> lines = new ArrayList<>();
		// Incluimos una cabezera para identificar cada columna en el archivo
		String header = "NOMBRE PRODUCTO;PRECIO UNITARIO;CANTIDAD VENDIDA";
		lines.add(header);

		// Usamos un for each para poder recorrer la lista ordenada
		for (Map.Entry<String, Integer> entry : orderedList) {
			String finalLine = entry.getKey() + ";" + entry.getValue();
			lines.add(finalLine);
		}

		// Re-utilizamos el metodo writeFiles de la clase GenerateInfoFiles
		GenerateInfoFiles.writeFiles(pathGenerateFileSalesProductReport, lines);
	}

	/**
	 * Metodo que valida si los archivos suministrados existen en el folderPath
	 * indicado Si existen llamara el metodo readFile()
	 * 
	 * @param folderPath
	 */
	public static void processFilesInFolder(String folderPath) {
	    File folder = new File(folderPath);

	    // Verificar si la carpeta existe y es un directorio
	    if (!folder.exists() || !folder.isDirectory()) {
	        System.out.println(
	                "El folder no existe o no es un directorio valido. Por favor, verifica e intenta nuevamente.");
	        return;
	    }

	    // Obtener la lista de archivos dentro de la carpeta
	    File[] files = folder.listFiles();

	    if (files == null || files.length == 0) {
	        System.out.println("La carpeta esta vacia. Por favor, agrega los archivos he intenta nuevamente.");
	        return;
	    }

	    // Iterar sobre cada archivo en la carpeta y leerlo
	    for (File file : files) {
	        if (file.isFile()) {
	            if (folderPath.contains("sales")) {
	                // reiniciamos la secuencia para cada archivo de ventas
	                readFile(file);
	            } else {
	                readFile(file);
	            }
	        }
	    }
	}

	/**
	 * Metodo que permite generar una busqueda de un vendedor por su numero de
	 * documento de identificación
	 * 
	 * @param listSeller
	 * @param documentNumber
	 * @return
	 */
	public static Seller findSellerByDocumentNumber(List<Seller> listSeller, String documentNumber) {
		for (Seller item : listSeller) {
			if (item.getDocumentNumber().equals(documentNumber)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Metodo que permite generar una busqueda de un producto por su id
	 * 
	 * @param listProduct
	 * @param id
	 * @return
	 */
	public static Product findProductById(List<Product> listProduct, int id) {
		for (Product item : listProduct) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Metodo que permite generar una busqueda de un vendedor por su id
	 * 
	 * @param listSeller
	 * @param id
	 * @return
	 */
	public static Seller findSellerById(List<Seller> listSeller, int id) {
		for (Seller item : listSeller) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Metodo que mapea los productos disponibles obtenidos de la lectura del
	 * archivo
	 * 
	 * @param parts
	 * @return
	 */
	public static Product mapperAvailableProducts(String[] parts) {
		Product product = new Product();

		product.setId(Integer.valueOf(parts[0]));
		product.setName(parts[1]);
		product.setUnitPrice(Double.valueOf(parts[2]));

		return product;
	}

	/**
	 * Metodo que mapea las ventas obtenidas de la lectura de los archivos
	 * 
	 * @param parts
	 * @param idSale
	 * @param idSeller
	 * @return
	 */
	public static Sale mapperSales(String[] parts, int idSale, int idSeller) {
		Sale sale = new Sale();

		sale.setId(idSale);
		sale.setIdSeller(idSeller);
		Product product = new Product();
		product.setId(Integer.valueOf(parts[0]));
		sale.setProduct(product);
		sale.setProductAmount(Integer.valueOf(parts[1]));

		return sale;
	}

	/**
	 * Metodo que mapea la informacion de los vendedores obtenida de la lectura del
	 * archivo
	 * 
	 * @param parts
	 * @param id
	 * @return
	 */
	public static Seller mapperSellers(String[] parts, int id) {
		Seller seller = new Seller();

		seller.setId(id);
		seller.setDocumentType(parts[0]);
		seller.setDocumentNumber(parts[1]);
		seller.setName(parts[2]);
		seller.setLastName(parts[3]);

		return seller;
	}

	/**
	 * Para propósitos de prueba, se crean métodos de generación de archivos de
	 * prueba para el programa en cuestión.
	 */
	public static void testFiles() {
		Random random = new Random();
		createSalesMenFile(random.nextInt(3) + 1, "Andres", Long.valueOf(1));
		createProductsFile(products.size());
		createSalesManInfoFile(random.nextInt(3) + 1);
	}

	/**
	 * 5. Archivos de prueba a. Dada una cantidad, un nombre y un id, crea un
	 * archivo pseudoaleatorio de ventas de un vendedor con el nombre y el id dados.
	 * 
	 * @param randomSalesCount
	 * @param name
	 * @param id
	 */
	public static void createSalesMenFile(int randomSalesCount, String name, long id) {
		String testPathCreateSalesMenFile = "files/test/createSalesMenFile.csv";
		List<String> listSales = new ArrayList<String>();

		String header = String.valueOf(id) + ";" + name;
		listSales.add(header);
		for (int i = 0; i <= randomSalesCount; i++) {
			listSales.add(products.get(i).getId() + ";" + i);
		}

		GenerateInfoFiles.writeFiles(testPathCreateSalesMenFile, listSales);
	}

	/**
	 * 5. Archivos de prueba b. crea un archivo con información pseudoaleatoria de
	 * productos, con los datos de productsCount productos.
	 * 
	 * @param productsCount
	 */
	public static void createProductsFile(int productsCount) {
		String testPathCreateProductsFile = "files/test/createProductsFile.csv";
		List<String> listSales = new ArrayList<String>();

		for (int i = 0; i < productsCount; i++) {
			listSales.add(
					products.get(i).getId() + ";" + products.get(i).getName() + ";" + products.get(i).getUnitPrice());
		}

		GenerateInfoFiles.writeFiles(testPathCreateProductsFile, listSales);
	}

	/**
	 * 5. Archivos de prueba c. crea un archivo con información de salesmanCount
	 * vendedores; el número de estos según lo indique el argumento entero. La
	 * información debe ser generada de manera pseudoaleatoria y ser coherente, es
	 * decir, los nombres y apellidos pueden ser extraídos de listas de nombres
	 * reales de personas.
	 * 
	 * @param salesmanCount
	 */
	public static void createSalesManInfoFile(int salesmanCount) {
		String testPathCreateSalesManInfoFile = "files/test/createSalesManInfoFile.csv";
		List<String> listSales = new ArrayList<String>();
		Random random = new Random();
		for (int i = 0; i < salesmanCount; i++) {
			listSales.add("CC" + 101914510 + random.nextInt(9) + 1 + ";" + sellers.get(random.nextInt(2) + 1).getName()
					+ ";" + sellers.get(random.nextInt(2) + 1).getLastName());
		}

		GenerateInfoFiles.writeFiles(testPathCreateSalesManInfoFile, listSales);
	}
}

/**
 * Clase que contiene los atributos y metodos para el objeto Vendedor
 */
class Seller {

	private Integer id;
	private String name;
	private String lastName;
	private String documentType;
	private String documentNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", name=" + name + ", lastName=" + lastName + ", documentType=" + documentType
				+ ", documentNumber=" + documentNumber + "]";
	}

}

/**
 * Clase que contiene los atributos y metodos para el objeto Producto
 */
class Product {

	private Integer id;
	private String name;
	private Double unitPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", unitPrice=" + unitPrice + "]";
	}

}

/**
 * Clase que contiene los atributos y metodos para el objeto Venta
 */
class Sale {

	private Integer id;
	private Integer idSeller;
	private Product product;
	private Integer productAmount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdSeller() {
		return idSeller;
	}

	public void setIdSeller(Integer idSeller) {
		this.idSeller = idSeller;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Integer productAmount) {
		this.productAmount = productAmount;
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", idSeller=" + idSeller + ", product=" + product + ", productAmount=" + productAmount
				+ "]";
	}

}