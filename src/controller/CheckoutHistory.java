package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.CheckoutBean;
import model.CheckoutBeanDAO;
import model.CheckoutItemBean;
import model.CheckoutItemBeanDAO;
import model.RestaurantBean;
import model.RestaurantBeanDAO;
import model.RestaurantImageBean;
import model.RestaurantImageBeanDAO;
import model.UserBean;
/**
 * Servlet implementation class CheckoutHistory
 */
@WebServlet("/CheckoutHistory")
public class CheckoutHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutHistory() {
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
		/* [{number: , status: , items: [{name: , quantity}], total, restaurant:{name, }}, ... ]*/
		response.setContentType("application/json");
		String email = ((UserBean) request.getSession().getAttribute("currentUser")).getEmail();
		JSONArray content = getCheckoutDetails(email);
		response.getWriter().write(content.toJSONString());
		response.setStatus(200);
	}
	
	JSONArray getCheckoutDetails(String userEmail) {
		CheckoutBeanDAO checkoutRetriever = new CheckoutBeanDAO();
		ArrayList<CheckoutBean> checkouts = new ArrayList<CheckoutBean>();
		checkouts = checkoutRetriever.doRetrieveByUser(userEmail);
		JSONArray checkoutsAsJSON = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(CheckoutBean checkout : checkouts) {
			JSONObject currentCheckout = new JSONObject();
			currentCheckout.put("number", checkout.getNumber());
			currentCheckout.put("amount", checkout.getAmount());
			currentCheckout.put("deliveryAddress", checkout.getCompleteAddress());
			currentCheckout.put("orderDate", sdf.format(checkout.getOrderDate()));
			currentCheckout.put("status", checkout.getStatus());
			currentCheckout.put("restaurant", getRestaurant(checkout.getRestaurant()));			
			currentCheckout.put("items", getCheckoutsItems(checkout.getNumber()));
			checkoutsAsJSON.add(currentCheckout);
		}
		return checkoutsAsJSON;
	}
	

	private JSONArray getCheckoutsItems(int number) {
		CheckoutItemBeanDAO itemRetriever = new CheckoutItemBeanDAO();
		ArrayList<CheckoutItemBean> items = itemRetriever.doRetrieveByCheckout(number);		
		JSONArray itemsArray = new JSONArray();
		for(CheckoutItemBean item : items) {
			JSONObject itemGot = new JSONObject();
			itemGot.put("itemName", item.getItemName());
			itemGot.put("quantity", item.getQuantity());
			itemsArray.add(itemGot);
		}
		return itemsArray;
	}
	
	private JSONObject getRestaurant(String owner) {
		RestaurantBeanDAO restaurantGetter = new RestaurantBeanDAO();
		RestaurantBean restaurant = restaurantGetter.doRetrieveByKey(owner);
		JSONObject restaurantItem = new JSONObject();
		restaurantItem.put("name", restaurant.getName());
		restaurantItem.put("category", restaurant.getCategory());
		restaurantItem.put("owner", restaurant.getOwner());
		restaurantItem.put("address", restaurant.getCompleteAddress());		
		restaurantItem.put("imgsrc",  getImage(restaurant.getOwner()));	
		return restaurantItem;
	}
	
	private String getImage(String owner) {
		RestaurantImageBean image;
		RestaurantImageBeanDAO imageRetriever = new RestaurantImageBeanDAO();
		if((image = imageRetriever.doRetrieveByKey(owner)) == null){
			return "img/default";
		} else return image.getImage();
	}
}
