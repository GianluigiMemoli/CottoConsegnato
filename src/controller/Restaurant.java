package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RestaurantBeanDAO;
import model.RestaurantBean;

/**
 * Servlet implementation class Restaurant
 */
@WebServlet("/Restaurant")
public class Restaurant extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Restaurant() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String owner = request.getParameter("owner");
		RestaurantBeanDAO restaurantRetriever = new RestaurantBeanDAO();
		RestaurantBean currentRestaurant = restaurantRetriever.doRetrieveByKey(owner);
		request.getSession().setAttribute("currentRestaurant", currentRestaurant);
		response.sendRedirect(request.getContextPath() + "/Restaurant.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
