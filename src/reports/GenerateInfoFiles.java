package reports;

public class GenerateInfoFiles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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