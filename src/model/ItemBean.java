package model;

import java.util.logging.Logger;

public class ItemBean {
	
	public ItemBean(String name, String restaurant, String restaurantOwner, double price, boolean hasComposition, String category) {
		this.name = name;
		this.restaurant = restaurant;
		this.setRestaurantOwner(restaurantOwner);
		this.price = price;
		this.hasComposition = hasComposition;
		this.category = category; 
	}
	
	/*public ItemBean(String name, String restaurant, String restaurantOwner, double price, boolean hasComposition, String category, int id) {
		this.name = name;
		this.restaurant = restaurant;
		this.setRestaurantOwner(restaurantOwner);		
		this.price = price;
		this.hasComposition = hasComposition;
		this.category = category; 
		this.id = id;
	}
	*/
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public boolean hasComposition() {
		return hasComposition; 
	}
	
	public String getCategory() {
		return category; 
	}
	
	public String getRestaurantOwner() {
		return restaurantOwner;
	}

	public void setRestaurantOwner(String restaurantOwner) {
		this.restaurantOwner = restaurantOwner;
	}
/*
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
*/
	@Override
	public String toString() {
		return "ItemBean [name=" + name + ", restaurant=" + restaurant + ", price=" + price + ", hasComposition="
				+ hasComposition + ", category=" + category + "]";
	}
	

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub		
		Logger.getLogger("model").info("isEqual called");

		if(this == obj) {
			Logger.getLogger("model").info("this == obj");
			return true;
		}
		
		if(obj == null) {
			Logger.getLogger("model").info("obj è null");
			return false;
		}
		
		if(!(obj instanceof ItemBean)) {
			Logger.getLogger("model").info("non è instanza");
			return false;
		}
		
		ItemBean toCompare = (ItemBean) obj;
		Logger.getLogger("model").info(this + "==" + toCompare);

		boolean isEqual = (this.getRestaurant().equals(toCompare.getRestaurant()) &&(this.getName().equals(toCompare.getName())));;
		Logger.getLogger("model").info(""+isEqual);
		return 	isEqual;
	}


	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		result = 31 * result + name.hashCode() + restaurant.hashCode() + category.hashCode();
		return result;
	}
	
	//private int id;
	private String name;
	private String restaurant; // pIVA
	private String restaurantOwner;
	private double price;
	private boolean hasComposition; 
	private String category; 
}
