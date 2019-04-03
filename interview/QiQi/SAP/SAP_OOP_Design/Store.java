package test9;

import java.util.ArrayList;
import java.util.List;

public class Store {
	private int uniqueStoreID;
	// Style 2 to implement OneToMany relationship between
	// Store and Aisle (Collection) 
	//@OneToMany(mappedBy="owner")
	private List<Aisle> storeAisles; //HS-A relationship
//	private static int pkey_next = 0;
	
	//TODO: Need to resolve conflict on store id assign
//	public Store() {
//		this(pkey_next);
//		this.uniqueStoreID = pkey_next;
//		pkey_next++;
//		this.storeAisles = new ArrayList<Aisle>();
//	}
	
	public Store(int storeID) {
		this.uniqueStoreID = storeID;
		this.storeAisles = new ArrayList<Aisle>(); // initial as empty aisle list
	}
	
	public Store(int storeID, List<Aisle> storeAisles) {
		this.uniqueStoreID = storeID;
		this.storeAisles = new ArrayList<Aisle>(storeAisles); // initial by given aisle list
	}
	
	// TODO: addAisles method should move to StoreManagement class to follow DAO pattern
//	public boolean addAisles(Aisle aisle) {
//		if(this.storeAisles != null) {
//			return this.storeAisles.add(aisle); // If add aisle success return true, else false
//		}
//		return false;
//	}
	
	public int getUniqueStoreID() {
		return uniqueStoreID;
	}

	public void setUniqueStoreID(int storeID) {
		this.uniqueStoreID = storeID;
	}
	
	public List<Aisle> getStoreAisles() {
		return storeAisles;
	}

	public void setStoreAisles(List<Aisle> storeAisles) {
		this.storeAisles = storeAisles;
	}
}
