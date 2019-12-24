package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class RestaurantByProvince
 */
@WebServlet("/RestaurantByProvince")
public class RestaurantByProvince extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantByProvince() {
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
		JSONParser parser = new JSONParser();
		try {
			JSONObject province = (JSONObject) parser.parse(request.getReader());
			ArrayList<RestaurantBean> restaurants = getRestaurants((String) province.get("province"));
			JSONArray fetchedRestaurants = makeJSONArray(restaurants);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			log(fetchedRestaurants.toJSONString());
			response.getWriter().write(fetchedRestaurants.toJSONString());
			response.setStatus(200);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			response.setStatus(500);
			e.printStackTrace();
		}
	}
	
	private ArrayList<RestaurantBean> getRestaurants(String province){
		RestaurantBeanDAO restaurantGetter = new RestaurantBeanDAO();
		return restaurantGetter.doRetrieveByProvince(province.toUpperCase());
	}
	
	private String getImage(String owner) {
		RestaurantImageBean image;
		RestaurantImageBeanDAO imageRetriever = new RestaurantImageBeanDAO();
		if((image = imageRetriever.doRetrieveByKey(owner)) == null){
			return "img/default";
		} else return image.getImage();
	}
	
	private JSONArray makeJSONArray(ArrayList<RestaurantBean> restaurantList) throws IOException {
		JSONArray fetchedRestaurants = new JSONArray();
		for(RestaurantBean restaurant : restaurantList) {
			JSONObject restaurantItem = new JSONObject();
			restaurantItem.put("name", restaurant.getName());
			restaurantItem.put("category", restaurant.getCategory());
			restaurantItem.put("owner", restaurant.getOwner());
			restaurantItem.put("imgsrc",  getImage(restaurant.getOwner()));		
			fetchedRestaurants.add(restaurantItem);
		}
		return fetchedRestaurants;
	}
	
	
}
