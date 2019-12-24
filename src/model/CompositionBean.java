package model;

public class CompositionBean {
	
	public CompositionBean(String plate, String ingredient, boolean allergen, String restaurant) {		
		this.plate = plate;
		this.ingredient = ingredient;
		this.allergen = allergen;
		this.restaurant = restaurant;
	}	
	
	
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public boolean isAllergen() {
		return allergen;
	}
	public void setAllergen(boolean allergen) {
		this.allergen = allergen;
	}
	public String getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}


	@Override
	public String toString() {
		return "CompositionBean [plate=" + plate + ", ingredient=" + ingredient + ", allergen=" + allergen
				+ ", restaurant=" + restaurant + "]";
	}


	private String plate;
	private String ingredient;
	private boolean allergen; 
	private String restaurant;
	
}
