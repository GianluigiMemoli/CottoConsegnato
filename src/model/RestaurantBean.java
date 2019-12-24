package model;

public class RestaurantBean {
	
		
	public RestaurantBean(String name, String owner, String category, String street, String cap, String city, String province) {
		this.name = name;
		this.category = category;
		this.owner = owner;
		this.street = street;
		this.cap = cap;
		this.city = city;
		this.province = province.toUpperCase();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setowner(String owner) {
		this.owner = owner;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setProvince(String province) {
		province = this.province;
	}
	
	public String getProvince() {
		return province; 
	}
	
	@Override
	public String toString() {
		return "RestaurantBean [name=" + name + ", category=" + category + ", owner=" + owner + ", street=" + street
				+ ", cap=" + cap + ", city=" + city + ", province=" + province + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj)
			return true;
		
		if(obj == null)
			return false;
		
		if(!(obj instanceof RestaurantBean))
			return false;
		
		RestaurantBean toCompare = (RestaurantBean) obj;
		
		return (this.getOwner().equals(toCompare.getOwner()));		
	}
	

	public String getCompleteAddress() {
		return street + "," + province + "," + cap;
	}

	private String name ;
	private String category;
	private String owner;
	private String street;
	private String cap;
	private String city;
	private String province; 
}
