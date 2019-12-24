package model;

import java.util.logging.Logger;

public class CheckoutItemBean {

	public CheckoutItemBean(int checkoutNumber, int quantity, String itemName) {
		super();
		this.checkoutNumber = checkoutNumber;
		this.quantity = quantity;
		this.itemName = itemName;
	}
	
	
	
	public int getCheckoutNumber() {
		return checkoutNumber;
	}
	
	public void setCheckoutNumber(int checkoutNumber) {
		this.checkoutNumber = checkoutNumber;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
		
		if(!(obj instanceof CheckoutItemBean)) {
			Logger.getLogger("model").info("non è instanza");
			return false;
		}
		
		CheckoutItemBean toCompare = (CheckoutItemBean) obj;
		Logger.getLogger("model").info(this + "==" + toCompare);

		boolean isEqual = (this.checkoutNumber == toCompare.getCheckoutNumber()) &&(this.itemName.equals(toCompare.getItemName()));		
		Logger.getLogger("model").info(""+isEqual);
		return 	isEqual;
	}


	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		result = 31 * result + itemName.hashCode();
		return result;
	}

	private int checkoutNumber;
	private int quantity;
	private String itemName;

}
