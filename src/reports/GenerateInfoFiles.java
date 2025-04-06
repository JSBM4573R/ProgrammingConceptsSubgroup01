package reports;

import java.io.*;
import java.util.*;

public class GenerateInfoFiles {

	public static void main(String[] args) {

		String pathGenerateFileProducts = "files/input/products/InfoProductosDisponibles.csv";
		// Crea un archivo plano con los productos disponibles
		writeFiles(pathGenerateFileProducts, infoProducts());

		String pathGenerateFileSellers = "files/input/sellers/InfoVendedores.csv";
		// Crear un archivo plano con información de los vendedores
		writeFiles(pathGenerateFileSellers, infoSellers());

		String pathGenerateFileSalesCamila = "files/input/sales/VentasCamila.csv";
		// Crea un archivo plano con las ventas de Camila
		writeFiles(pathGenerateFileSalesCamila, infoSaleCamila());

		String pathGenerateFileSalesSantiago = "files/input/sales/VentasSantiago.csv";
		// Crea un archivo plano con las ventas de Santiago
		writeFiles(pathGenerateFileSalesSantiago, infoSaleSantiago());

		String pathGenerateFileSalesMateo = "files/input/sales/VentasMateo.csv";
		// Crea un archivo plano con las ventas de Mateo
		writeFiles(pathGenerateFileSalesMateo, infoSaleMateo());

		System.out.println(
				"¡Finalización Exitosa de la clase GenerateInfoFiles! Se generaron los archivos planos en la ruta: files/input/*");
	}

	public static void writeFiles(String filePath, List<String> dataList) {
		// Usamos la clase BufferedWriter para poder usar sus metodos y escribir un
		// archivo plano

		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {

			// Usamos un bucle for para recorrer la lista de datos que nosotros creamos
			for (int i = 0; i < dataList.size(); i++) {
				// Usamos el metodo write del BufferedWriter para escribir las lineas del
				// archivo
				bufferedWriter.write(dataList.get(i));
				// Valida que el ultimo registro en el archivo plano no genere un salto de linea
				if (i != dataList.size() - 1) {
					// Generamos salto de linea para organizar el contenido del archivo
					bufferedWriter.newLine();
				}
			}

		} catch (IOException e) {
			System.out.println("Error al escribir el archivo: " + e.getMessage());
		}
	}

	public static List<String> infoProducts() {
		List<String> listProducts = new ArrayList<String>();
		
		String product1 = "001;Esfero;2500";
		listProducts.add(product1);
		String product2 = "003;Lápiz;1950";
		listProducts.add(product2);
		String product3 = "004;Borrador;850";
		listProducts.add(product3);
		String product4 = "002;Tajalápiz;1200";
		listProducts.add(product4);
		String product5 = "005;Escuadra;5800";
		listProducts.add(product5);
		
		return listProducts;
	}

	public static List<String> infoSaleCamila() {
		List<String> listSales = new ArrayList<String>();
		String encabezado = "CC;1023456789";
		listSales.add(encabezado);
		String sale1 = "002;5";
		listSales.add(sale1);
		String sale2 = "001;3";
		listSales.add(sale2);
		String sale3 = "003;7";
		listSales.add(sale3);
		return listSales;
	}

	public static List<String> infoSaleSantiago() {
		List<String> listSales = new ArrayList<String>();
		String encabezado = "CC;1019156723";
		listSales.add(encabezado);
		String sale1 = "003;8";
		listSales.add(sale1);
		String sale2 = "002;4";
		listSales.add(sale2);
		String sale3 = "001;6";
		listSales.add(sale3);
		return listSales;
	}

	public static List<String> infoSaleMateo() {
		List<String> listSales = new ArrayList<String>();
		String encabezado = "CC;1018456735";
		listSales.add(encabezado);
		String sale1 = "001;1";
		listSales.add(sale1);
		String sale2 = "003;7";
		listSales.add(sale2);
		String sale3 = "002;4";
		listSales.add(sale3);
		return listSales;
	}

	public static List<String> infoSellers() {
		List<String> listSellers = new ArrayList<String>();
		String seller1 = "CC;1023456789;Camila;Buitrago";
		listSellers.add(seller1);
		String seller2 = "CC;1019156723;Santiago;Torres";
		listSellers.add(seller2);
		String seller3 = "CC;1018456735;Mateo;Peralta";
		listSellers.add(seller3);
		return listSellers;
	}

}