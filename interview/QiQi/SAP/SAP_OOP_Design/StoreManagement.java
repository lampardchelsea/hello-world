package test9;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StoreManagement {
	// TODO: Singleton design model	
	// Create an instance of the class at the time of class loading
//	private static final StoreManagement storeManagementinstance = new StoreManagement();
//	
//	// private constructor to prevent others from instantiating this class
//	private StoreManagement() {}
//	
//	// Provide a global point of access to the instance
//	public static StoreManagement getStoreManagementInstance() {
//		return storeManagementinstance;
//	}
	// Refer to
	// https://www.callicoder.com/java-singleton-design-pattern-example/
	// https://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
    // Since the line private static final Foo INSTANCE = new Foo(); 
	// is only executed when the class FooLoader is actually used, 
	// this takes care of the lazy instantiation, and it guaranteed to be thread safe.
	// we can call constructor as 
	// 		StoreManagement storeManagement = StoreManagement.getInstance();
	private static final long serialVersionUID = 1L;
    
    // Wrapped in a inner static class so that loaded only when required
    private static class StoreManagementLoader {
    	private static final StoreManagement INSTANCE = new StoreManagement();
    }
    
    public static StoreManagement getInstance() {
    	return StoreManagementLoader.INSTANCE;
    }
    
    // Serialization
    @SuppressWarnings("unused")
    private StoreManagement readResolve() {
    	return StoreManagementLoader.INSTANCE;
    }
    
	// Design API to get all merchandise details which over expiration date for given store id
    public String getOverExpirationDateMerchandiseDetails(List<Integer> stores) throws Exception {
    	String result = "";
    	// Get current date
    	Date today = new Date();
     	StoreMap map = new StoreMap();
    	Map<Integer, Store> storeMap = map.getStoreMap();
    	for(Integer storeID : stores) {
    		if(storeMap.containsKey(storeID)) {
    			Store store = storeMap.get(storeID);
    	    	Map<Merchandise, String> allMerchandiseOverExpirationDateInCurrentAisle = new HashMap<Merchandise, String>();
    			List<Aisle> aisles = store.getStoreAisles();
    			for(Aisle aisle : aisles) {
    				Shelf rightShelf = aisle.getRightShelf();
    				Shelf leftShelf = aisle.getLeftShelf();
    				Merchandise rightShelfFrontMerchandise = rightShelf.getMerchandiseOnShelf(ShelfPosition.front);
    				Merchandise rightShelfBackMerchandise = rightShelf.getMerchandiseOnShelf(ShelfPosition.back);
    				Merchandise leftShelfFrontMerchandise = leftShelf.getMerchandiseOnShelf(ShelfPosition.front);
    				Merchandise leftShelfBackMerchandise = leftShelf.getMerchandiseOnShelf(ShelfPosition.back);
    				if(rightShelfFrontMerchandise != null && rightShelfFrontMerchandise.getExpirationDate().compareTo(today) < 0) {
    					allMerchandiseOverExpirationDateInCurrentAisle.put(rightShelfFrontMerchandise, "right_shelf_front_position");
    				}
    				if(rightShelfBackMerchandise != null && rightShelfBackMerchandise.getExpirationDate().compareTo(today) < 0) {
    					allMerchandiseOverExpirationDateInCurrentAisle.put(rightShelfBackMerchandise, "right_shelf_back_position");
    				}
    				if(leftShelfFrontMerchandise != null && leftShelfFrontMerchandise.getExpirationDate().compareTo(today) < 0) {
    					allMerchandiseOverExpirationDateInCurrentAisle.put(leftShelfFrontMerchandise, "left_shelf_front_position");
    				}
    				if(leftShelfBackMerchandise != null && leftShelfBackMerchandise.getExpirationDate().compareTo(today) < 0) {
    					allMerchandiseOverExpirationDateInCurrentAisle.put(leftShelfBackMerchandise, "left_shelf_back_position");
    				}
        			String currentStoreDetails = generateStringToken(store, aisle, allMerchandiseOverExpirationDateInCurrentAisle);
        			result += currentStoreDetails;
    			}
    		}
    	}
    	return result;
    }
    
    public String generateStringToken(Store store, Aisle aisle, Map<Merchandise, String> allMerchandiseOverExpirationDateInCurrentAisle) {
    	StringBuilder sb = new StringBuilder();
    	for(Entry<Merchandise, String> entry : allMerchandiseOverExpirationDateInCurrentAisle.entrySet()) {
    		Merchandise merchandise = entry.getKey();
    		sb.append(store.getUniqueStoreID()).append(" ").append(aisle.getAisleIndex()).append(" ")
    		.append(entry.getValue()).append(" ").append(merchandise.getName()).append(" ").append(merchandise.getAmount())
    		.append(" ").append(merchandise.getExpirationDate().toString()).append("\n");
    	}
    	return sb.toString();
    }
    
	public boolean addAisles(Store store, Aisle aisle) {
		if(store.getStoreAisles() != null) {
			return store.getStoreAisles().add(aisle); // If add aisle success return true, else false
		}
		return false;
	}
}
