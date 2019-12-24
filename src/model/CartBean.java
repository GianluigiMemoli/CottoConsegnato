package model;

import java.util.ArrayList;
import java.util.logging.Logger;

public class CartBean {
	private Logger log;
	public CartBean(UserBean client, RestaurantBean restaurant) {
		log = Logger.getLogger("model");
		this.client = client;
		this.restaurant = restaurant;
		products = new ArrayList<ItemBean>();			
		total = 0;
	}
	
	public void addItemToCart(ItemBean newProduct) {
		products.add(newProduct);
		this.total += newProduct.getPrice();		
		log.info("added, new size: " + products.size());
	}
	
	public void removeItemFromCart(ItemBean removedProduct) {
		products.remove(removedProduct);
		this.total -= removedProduct.getPrice();	
		log.info("removed, new size: " + products.size());
	}
	
	
	
	public double getTotal() {
		return total;
	}
	
	public UserBean getClient() {
		return client;
	}
	
	public RestaurantBean getRestaurant() {
		return restaurant;
	}
	
	public ArrayList<ItemBean> getProducts(){
		return products;
	}
	
	
	private double total;
	private UserBean client; 
	private RestaurantBean restaurant;
	private ArrayList<ItemBean> products; 
}
