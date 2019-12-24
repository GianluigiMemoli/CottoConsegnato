package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.RestaurantBean;
import model.RestaurantBeanDAO;
import model.UserBean;

/**
 * Servlet implementation class RestaurantGetter
 */
@WebServlet("/RestaurantGetter")
public class RestaurantGetter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantGetter() {
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
		//Getting UserBean from Session
		UserBean usr = (UserBean) request.getSession().getAttribute("currentUser");
		log(usr.getEmail()); 
		//Retrieving RestaurantBean by owner email (if is alreaady set)
		RestaurantBeanDAO restaurantDAO = new RestaurantBeanDAO(); 
		RestaurantBean restaurant = restaurantDAO.doRetrieveByKey(usr.getEmail()); 
		//Making JSON to send back
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		JSONObject restaurantJSON = new JSONObject(); 
		if(restaurant == null) {
			response.getWriter().print(restaurantJSON.toJSONString());
		} else {
			restaurantJSON.put("name", restaurant.getName()); 
			restaurantJSON.put("category", restaurant.getCategory()); 
			restaurantJSON.put("street", restaurant.getStreet()); 
			restaurantJSON.put("cap", restaurant.getCap()); 
			restaurantJSON.put("city", restaurant.getCity()); 
			restaurantJSON.put("province", restaurant.getProvince()); 			
			response.getWriter().print(restaurantJSON.toJSONString());
		}
	}

}
