package reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		// Ruta donde se encuentra todos los archivos con la información de los vendedores.
		String pathFileSellers = "files/input/sellers";

		processFilesInFolder(pathFileSellers);

		// Ruta donde se encuentra el archivo con la información de las ventas.
		String pathFileSales = "files/input/sales";

		processFilesInFolder(pathFileSales);

		// Ruta donde se encuentra el archivo con la información de los productos disponibles.
		String pathFileProducts = "files/input/products";

		processFilesInFolder(pathFileProducts);

		System.out.println("¡Finalización Exitosa! Se procesaron todos los archivos planos pseudoaleatorios en la carpeta files.");
	}

	public static void readFile(File nameFile) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(nameFile));
			String line;

			System.out.println("Contenido que se logro leer del archivo:");

			// Leer línea por línea
			while ((line = bufferedReader.readLine()) != null) {

				// Separar por ";"
				String[] partes = line.split(";");

				// Mostrar cada parte en la consola
				for (String parte : partes) {
					System.out.print(parte + " | ");
				}

				// Salto de línea para mejor visualización
				System.out.println();
			}

			bufferedReader.close();

		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
	}

	public static void processFilesInFolder(String folderPath) {
		File folder = new File(folderPath);

		// Verificar si la carpeta existe y es un directorio
		if (!folder.exists() || !folder.isDirectory()) {
			System.out.println(
					"El folder no existe o no es un directorio valido. Por favor, verifica he intenta nuevamente.");
			return;
		}

		// Obtener la lista de archivos dentro de la carpeta
		File[] files = folder.listFiles();

		if (files == null || files.length == 0) {
			System.out.println("La carpeta esta vacia. Por favor, agrega los archivos he intenta nuevamente.");
			return;
		}

		// Iterar sobre cada archivo en la carpeta
		for (File file : files) {
			if (file.isFile()) {
				System.out.println("\nNombre del archivo plano: " + file.getName());
				readFile(file);
			}
		}

	}

	public List<Product> mapperProductosDisponibles(String[] partes) {
		List<Product> listProducts = new ArrayList<Product>();
		Product product = new Product();
		product.setId(Integer.valueOf(partes[0]));
		product.setName(partes[1]);

		return listProducts;
	}

}

class Seller {

	private Integer id;
	private String name;
	private String lastName;
	private String documentType;
	private String documentNumber;
	private Double salesAmount;

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

	public Double getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(Double salesAmount) {
		this.salesAmount = salesAmount;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", name=" + name + ", lastName=" + lastName + ", documentType=" + documentType
				+ ", documentNumber=" + documentNumber + ", salesAmount=" + salesAmount + "]";
	}

}

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

class Sale {

	private Integer id;
	private Integer idSeller;
	private List<Product> listProducts;

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

	public List<Product> getListProducts() {
		return listProducts;
	}

	public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", idSeller=" + idSeller + ", listProducts=" + listProducts + "]";
	}

}