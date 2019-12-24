package model;

public class RestaurantImageBean {
	
		
	public RestaurantImageBean(String owner, String image) {
		super();
		this.owner = owner;
		this.image = image;
	}
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "RestaurantImageBean [owner=" + owner + ", image=" + image + "]";
	}

	private String owner;
	private String image;
}
