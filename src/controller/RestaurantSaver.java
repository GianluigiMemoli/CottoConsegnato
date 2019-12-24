package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RestaurantBean;
import model.RestaurantBeanDAO;
import model.UserBean;
/**
 * Servlet implementation class RestaurantSaver
 */
@WebServlet("/RestaurantSaver")
public class RestaurantSaver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantSaver() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		log("restsaving");		
		RestaurantBean restaurant = ParameterValidator.validateRestaurant(request); 
		RestaurantBeanDAO restaurantDAO = new RestaurantBeanDAO();
		String owner = ((UserBean) request.getSession().getAttribute("currentUser")).getEmail();
		//Checking if already exists
		if(restaurantDAO.doRetrieveByKey(owner) == null) {
			//if not save it
			if(!restaurantDAO.doSave(restaurant)) {
				response.setStatus(500);
			} else log("saved");
		} else {
			//if exists update it
			if(!restaurantDAO.doUpdate(restaurant)) {
				response.setStatus(500);
			} else log("updated"); 
		}
	}
}
