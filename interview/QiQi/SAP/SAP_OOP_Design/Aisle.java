package test9;

public class Aisle {
	private int aisleID;
	private int aisleIndex;
	//TODO: Use enum type AisleShelfPosition to replace String as key
//	private Map<AisleShelfPosition, Shelf> aisleShelves; //HS-A relationship
	
	private Shelf rightShelf;
	private Shelf leftShelf;
	
	// Style 1 to implement One To Many relationship
	//@ManyToOne
	//@JoinColumn
	private int aisleStoreID;
	
	//TODO: Need to resolve conflict on aisle id assign
//	public Aisle() {
//		this(0);
//	}
	
	public Aisle(int aisleID, int aisleIndex) {
		this.aisleID = aisleID;
		this.aisleIndex = aisleIndex;
		// initial as empty shelves map with limited size equal 2
		// since one aisle only contains right and left 2 shelves
//		this.aisleShelves = new HashMap<AisleShelfPosition, Shelf>(2); 
//		this.aisleShelves.put(AisleShelfPosition.left, null);
//		this.aisleShelves.put(AisleShelfPosition.right, null);
		this.rightShelf = new Shelf(String.valueOf(aisleID) + "_R", AisleShelfPosition.right);
		this.leftShelf = new Shelf(String.valueOf(aisleID) + "_L", AisleShelfPosition.left);
	}

	public int getAisleID() {
		return aisleID;
	}
	
	public void setAisleID(int aisleID) {
		this.aisleID = aisleID;
	}
	
	public int getAisleIndex() {
		return aisleIndex;
	}

	public void setAisleIndex(int aisleIndex) {
		this.aisleIndex = aisleIndex;
	}
	
	public Shelf getRightShelf() {
		return rightShelf;
	}

	public Shelf getLeftShelf() {
		return leftShelf;
	}
}
