package reports;

import java.io.*;

public class GenerateInfoFiles {

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
		
	}
	
	public static void readFile(File nameFile) {
		try {
            BufferedReader br = new BufferedReader(new FileReader(nameFile));
            String line;

            System.out.println("Contenido procesado del archivo:");
            
            // Leer línea por línea
            while ((line = br.readLine()) != null) { 
            	
            	// Separar por ";"
                String[] partes = line.split(";"); 

                // Mostrar cada parte en la consola
                for (String parte : partes) {
                    System.out.print(parte + " | "); 
                }
                
                // Salto de línea para mejor visualización
                System.out.println(); 
            }
            
            br.close();
            
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
	}
	
    public static void processFilesInFolder(String folderPath) {
    	File folder = new File(folderPath);
        
        // Verificar si la carpeta existe y es un directorio
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("El folder no existe o no es un directorio valido. Por favor, verifica he intenta nuevamente.");
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
	private String amount;
	
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", amount=" + amount + "]";
	}
	
}