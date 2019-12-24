package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.*; 


/**
 * Servlet implementation class ItemSaver
 */
@WebServlet("/ItemSaver")
public class ItemSaver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemSaver() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Reading the stringified JSON, parse it as JSON 				
		JSONParser parser = new JSONParser();
		try {			
			JSONObject itemJSON = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream()));						
			ItemBean newItem = JSONtoItem(itemJSON, request.getSession());
			ItemBeanDAO itemDAO = new ItemBeanDAO(); 
			if(!itemDAO.doSave(newItem)) {
				log("error saving");
				response.setStatus(500);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().append("Errore salvataggio piatto, controlla se già esiste");
			} else {
				if(newItem.hasComposition()) {
					JSONObject compositionJSON = (JSONObject) itemJSON.get("composition");
					log("arrived composition: " + compositionJSON.toJSONString());
					if(!saveComposition(compositionJSON, newItem)) {
						response.setStatus(500);
						response.setCharacterEncoding("UTF-8");
						response.getWriter().append("Errore salvataggio ingredienti piatto");
					} else {
						response.setStatus(200);	
					}
				} else {
					response.setStatus(200);					
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			
		}		
	}
	
	private ItemBean JSONtoItem(JSONObject json, HttpSession session) {
		String name, category;
		double price;
		boolean hasComposition; 
		name = (String) json.get("name"); 
		category = (String) json.get("category"); 
		price = ((Number) json.get("price")).doubleValue();
		hasComposition = (boolean) json.get("hasComposition");
		RestaurantBeanDAO  restaurantGetter = new RestaurantBeanDAO();
		String owner = ((UserBean) session.getAttribute("currentUser")).getEmail();
		String restaurant = ((RestaurantBean)restaurantGetter.doRetrieveByKey(owner)).getName();			
		return new ItemBean(name, restaurant, owner, price, hasComposition, category); 		
	}


private boolean saveComposition(JSONObject composition, ItemBean item) {
	//getting ingredients
	Set<String> keys = composition.keySet();	
	CompositionBeanDAO compositionDAO = new CompositionBeanDAO();	
	CompositionBean newIngredient;
	//saving ingredients
	for(String ingredient : keys) {
		JSONObject currentIngredientObj = (JSONObject) composition.get(ingredient);		
		boolean isAllergen = (boolean)currentIngredientObj.get("isAllergen");
		newIngredient = new CompositionBean(item.getName(), ingredient, isAllergen, item.getRestaurant());
		if(!compositionDAO.doSave(newIngredient)) {
			log("error saving composition");
			return false;
		}
	}
	return true;
}

private String getRestaurantOwner(HttpSession session) {
	//The current user is a Restaurateur if is saving an item
	//So I'm not going to get first the restaurant and then the email associated to it
	UserBean currentUser = (UserBean) session.getAttribute("currentUser");
	return currentUser.getEmail();
}

}