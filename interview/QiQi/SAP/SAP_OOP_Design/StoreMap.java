package test9;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// TODO: Relational DB to keep update for the store mapping list
// here to mock up the store map
public class StoreMap {
	public Map<Integer, Store> storeMap;
	
	public StoreMap() {
		storeMap = new HashMap<Integer, Store>();
		Store storeOne = new Store(1);
		Aisle storeOneAisleOne = new Aisle(1,1);
		Shelf leftShelf = storeOneAisleOne.getLeftShelf();
		Shelf rightShelf = storeOneAisleOne.getRightShelf();
		Merchandise leftShelfFrontMerchandise = new Merchandise("AAA", 100, new Date(System.currentTimeMillis()-24*60*60*1000));
		leftShelf.addMerchandiseOnShelf(ShelfPosition.front, leftShelfFrontMerchandise);
//		storeOne.addAisles(storeOneAisleOne);
		StoreManagement storeManagement = StoreManagement.getInstance();
		storeManagement.addAisles(storeOne, storeOneAisleOne);
//		Store storeTwo = new Store(2);
//		Store storeThree = new Store(3);
		storeMap.put(1, storeOne);
//		storeMap.put(2, storeTwo);
//		storeMap.put(3, storeThree);
	}
	
	public Map<Integer, Store> getStoreMap() {
		return storeMap;
	}

	public void setStoreMap(Map<Integer, Store> storeMap) {
		this.storeMap = storeMap;
	}
}
