package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.ItemBeanDAO;
import model.RestaurantBean;
import model.RestaurantBeanDAO;
import model.UserBean;

/**
 * Servlet implementation class ItemRemover
 */
@WebServlet("/ItemRemover")
public class ItemRemover extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemRemover() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		try {
			JSONObject itemNameJSON = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream()));
			String itemName = (String) itemNameJSON.get("name");
			ItemBeanDAO itemBeanDAO = new ItemBeanDAO();
			String restaurantName = getRestaurantName(request.getSession());	
			String restaurantOwner = getRestaurantOwner(request.getSession());
			response.setContentType("text/plain");
			if(itemBeanDAO.doRemoveByKey(itemName, restaurantName, restaurantOwner)) {
				response.setStatus(200);
				response.getWriter().write("Prodotto rimosso correttamente!");
				response.getWriter().close();
			} else {
				response.setStatus(500);
				response.getWriter().write("Errore rimozione prodotto!");
				response.getWriter().close();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private String getRestaurantName(HttpSession session) {
		UserBean currentUser = (UserBean) session.getAttribute("currentUser");
		RestaurantBeanDAO restaurantRetriever = new RestaurantBeanDAO();
		RestaurantBean currentRestaurant = restaurantRetriever.doRetrieveByKey(currentUser.getEmail());
		return currentRestaurant.getName();
	}
	
	private String getRestaurantOwner(HttpSession session) {
		//The current user is a Restaurateur if is deleting an item
		//So I'm not going to get first the restaurant and then the email associated to it
		UserBean currentUser = (UserBean) session.getAttribute("currentUser");
		return currentUser.getEmail();
	}
}
