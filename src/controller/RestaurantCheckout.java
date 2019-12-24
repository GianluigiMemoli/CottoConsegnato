package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CheckoutBeanDAO;

/**
 * Servlet implementation class RestaurantOrders
 */
@WebServlet("/RestaurantOrders")
public class RestaurantCheckout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantCheckout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CheckoutBeanDAO validator = new CheckoutBeanDAO();
		String attribute = "";
		int number = -1;
		if(request.getParameter("accept") != null) {
			number = Integer.parseInt(request.getParameter("accept"));
			attribute ="accepted";
			log(attribute);			
		}
		if(request.getParameter("reject") != null) {
			number = Integer.parseInt(request.getParameter("reject"));
			attribute ="rejected";
			log(attribute);
		}
		response.setContentType("text/plain");
		try {
			if(validator.doAcceptOrRejectByNumber(number, attribute)){
				response.getWriter().write("Ordine modificato");
			} else {
				response.getWriter().write("Errore");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().write("Errore grave");

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
