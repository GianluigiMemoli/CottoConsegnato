package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.CartBean;
import model.ItemBean;
import model.ItemBeanDAO;
import model.RestaurantBean;
import model.UserBean;

/**
 * Servlet implementation class CartManager
 */
@WebServlet("/CartManager")
public class CartManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartManager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		try {
			JSONObject cartInteraction = getCartInteraction(request);
			CartBean currentCart;
			if((currentCart = (CartBean) request.getSession().getAttribute("currentCart")) == null) {
				//no cart in session
				currentCart = initCart(request.getSession());
				request.getSession().setAttribute("currentCart", currentCart);
				//log"Made new cart due to not present");
			} else{//checking if cart is an instance from another Restaurant
				RestaurantBean currentRestaurant =(RestaurantBean) request.getSession().getAttribute("currentRestaurant");
				if(!currentCart.getRestaurant().equals(currentRestaurant)) {
					currentCart = initCart(request.getSession());
					request.getSession().setAttribute("currentCart", currentCart);
					//log"Made new cart due to not equal to present");
				}
			}
			
			JSONObject JSONresponse;
			JSONresponse = doAction(cartInteraction, currentCart);
			//saving changes
			request.getSession().setAttribute("currentCart", currentCart);
			
			if(JSONresponse != null) {
				response.setContentType("application/json");			
				response.getWriter().write(JSONresponse.toJSONString());
				response.setStatus(200); 
			} else {
				response.setStatus(500);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			response.setStatus(500);
			e.printStackTrace();
		}
	} 
	
	private JSONObject getCartInteraction(HttpServletRequest request) throws IOException, ParseException {
		JSONObject cartInteraction;
		JSONParser parser = new JSONParser();
		cartInteraction = (JSONObject) parser.parse(request.getReader());
		return cartInteraction;
	}
	
	private CartBean initCart(HttpSession session) {
		UserBean currentUser = (UserBean) session.getAttribute("currentUser");
		RestaurantBean currentRestaurant = (RestaurantBean) session.getAttribute("currentRestaurant");
		return new CartBean(currentUser, currentRestaurant);
	}
	
	private JSONObject doAction (JSONObject interaction, CartBean cart) {
		String action = (String) interaction.get("action");		
		if(action.equals("add") || action.equals("remove")) {
			return addOrRemove(interaction, cart);
		}
		else if(action.equals("get"))
			return getProducts(cart);
		return null;
	}
	
	private JSONObject getProducts(CartBean cart) {	
		JSONObject response = new JSONObject();
		ArrayList<ItemBean> products = cart.getProducts();
		log(products.toString());
		for(ItemBean product : products) {
			if(!response.containsKey(product.getName())){
				
				response.put(product.getName(), 1);
			} else response.put(product.getName(), (int)response.get(product.getName()) + 1);
		}
		response.put("total", cart.getTotal());
		
		return response;
	}
	
	private JSONObject addOrRemove(JSONObject interaction, CartBean cart) {
		String action = (String) interaction.get("action");
		String productName = (String) interaction.get("product"); 
		ItemBean productInvolved;
		ItemBeanDAO productRetriever = new ItemBeanDAO();
		productInvolved = productRetriever.doRetrieveByName(cart.getRestaurant().getName(), cart.getRestaurant().getOwner(), productName);
		if(action.equals("add")) {
			cart.addItemToCart(productInvolved);			
		} else if(action.equals("remove")) {
			cart.removeItemFromCart(productInvolved);
		}
		log("total: "+ cart.getTotal());
		JSONObject response = new JSONObject();
		response.put("total", cart.getTotal());
		
		return response;
	}
	
	
	
}

