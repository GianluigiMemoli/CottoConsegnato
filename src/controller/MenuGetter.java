package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.*;
/**
 * Servlet implementation class MenuGetter
 */
@WebServlet("/MenuGetter")
public class MenuGetter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuGetter() {
        super();
        // TODO Auto-generated constructor stub
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//getting restaurant name
		String restaurant = getRestaurantName(request.getSession()); 
		String restaurantOwner = getRestaurantOwner(request.getSession());
		//getting menu items
		ItemBeanDAO menuItemRetriever = new ItemBeanDAO();
		ArrayList<ItemBean> menuItems = menuItemRetriever.doRetrieveByKey(restaurant, restaurantOwner);
		if(menuItems != null) {
			//Setting up JSON array of item to send
			JSONArray itemArray = makeMenuJSON(menuItems);
			//send
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			//log(itemArray.toJSONString());
			response.getWriter().write(itemArray.toJSONString());
		}
	}
	
	private JSONArray makeMenuJSON(ArrayList<ItemBean> menuItems) {
		JSONObject menuJSON = new JSONObject();
		ArrayList<CompositionBean> composition;
		CompositionBeanDAO compositionRetriever = new CompositionBeanDAO();
		JSONArray itemArrayJSON = new JSONArray();
		for(ItemBean currentItem : menuItems) {
			JSONObject item = new JSONObject();
			item.put("itemName", currentItem.getName());
			item.put("itemPrice", currentItem.getPrice());
			item.put("itemCategory", currentItem.getCategory());
			item.put("hasComposition", currentItem.hasComposition());
			//if has composition make a json with it and then put into item
			if(currentItem.hasComposition()) {
				composition = compositionRetriever.doRetrieveByKey(currentItem.getName(), currentItem.getRestaurant());		
				JSONObject compositionJSON = new JSONObject();
				for(CompositionBean ingredient : composition) {
					compositionJSON.put(ingredient.getIngredient(), ingredient.isAllergen());
				}					
				item.put("composition", compositionJSON);
			}
			itemArrayJSON.add(item);
		}
		return itemArrayJSON;
	}
	
	private String getRestaurantName(HttpSession session) {
		UserBean currentUser = (UserBean) session.getAttribute("currentUser");
		RestaurantBean currentRestaurant;
		if(currentUser.getType().equals("ristoratore")) {
			RestaurantBeanDAO restaurantRetriever = new RestaurantBeanDAO();
			currentRestaurant = restaurantRetriever.doRetrieveByKey(currentUser.getEmail());
		} else {
			currentRestaurant = (RestaurantBean) session.getAttribute("currentRestaurant");			
		}
		
		return currentRestaurant.getName();		
	}
	
	private String getRestaurantOwner(HttpSession session) {
		UserBean currentUser = (UserBean) session.getAttribute("currentUser");
		RestaurantBean currentRestaurant;
		if(currentUser.getType().equals("ristoratore")) {
			return currentUser.getEmail();
		} else {
			currentRestaurant = (RestaurantBean) session.getAttribute("currentRestaurant");
			return currentRestaurant.getOwner();
		}
		
	}
	
}
