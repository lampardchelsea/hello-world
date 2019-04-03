package test9;

import java.util.HashMap;
import java.util.Map;

public class Shelf {
	// Change to string for convenience to concatenate Aisle id + "_R" or "_L"
	//@OneToMany
	private String shelfID;
	private AisleShelfPosition side;
//	private ShelfPosition position;
	// Style 2 to implement OneToMany relationship between
	// Shelf and Merchandise (Collection)
	/**
	 * Need to initialize something like below for collection JPA mapping
	 * https://stackoverflow.com/questions/3393649/storing-a-mapstring-string-using-jpa
	 *  @ElementCollection
	    @MapKeyColumn(name="name")
	    @Column(name="value")
	    @CollectionTable(name="example_attributes", joinColumns=@JoinColumn(name="example_id"))
	 */
	private Map<ShelfPosition, Merchandise> merchandiseOnShelf;
	
//	public Shelf() {
//		this(0);
//	}
	
	public Shelf(String shelfID, AisleShelfPosition side) {
		this.shelfID = shelfID;
		this.side = side;
		this.merchandiseOnShelf = new HashMap<ShelfPosition, Merchandise>(2);
	}
	
	public boolean addMerchandiseOnShelf(ShelfPosition position, Merchandise merchandise) {
		if(this.merchandiseOnShelf != null) {
			this.merchandiseOnShelf.put(position, merchandise);
			return true;
		}
		return false;
	}
	
	public Merchandise getMerchandiseOnShelf(ShelfPosition position) {
		if(this.merchandiseOnShelf != null) {
		    return this.merchandiseOnShelf.get(position);
		}
		return null;
	}
	
	public String getShelfID() {
		return shelfID;
	}
	
	public void setShelfID(String shelfID) {
		this.shelfID = shelfID;
	}
	
	public AisleShelfPosition getSide() {
		return side;
	}
}
