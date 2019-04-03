package test9;

import java.util.Date;

//TODO: Do the same modification as SKU
public class Merchandise {
	private int uniqueSkuNumber;
	private String name;
	private long amount;
	private Date expirationDate;
	// keep global for all Merchandise to make merchandise id as unique
	private static int pkey_next = 1; 
	
	//@ManyToOne
	private int merchandiseShelfID;
	
//	public Merchandise() {
//		this(0);
//	}
	
	public Merchandise(String name, long amount, Date expirationDate) {
		this.uniqueSkuNumber = pkey_next;
		pkey_next++;
		this.name = name;
		this.amount = amount;
		this.expirationDate = expirationDate;
	}
	
	public int getUniqueSkuNumber() {
		return uniqueSkuNumber;
	}

	// No set for uniqueSkuNumber
//	public void setMerchandiseID(int uniqueSkuNumber) {
//		this.uniqueSkuNumber = uniqueSkuNumber;
//	}
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}
