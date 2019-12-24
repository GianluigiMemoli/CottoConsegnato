package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AuthorizedOwnerBean;
import model.AuthorizedOwnerBeanDAO;
import model.UserBean;
import model.UserBeanDAO;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public Registration(){
        super();
}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().println("ofra");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			log("email: "+request.getParameter("email"));		
			UserBean registeredUser = ParameterValidator.fetchParameters(request); 
			log("UserBean created successfully!!");
			log(registeredUser.toString());
			UserBeanDAO userDAO = new UserBeanDAO();
				if(userDAO.doSave(registeredUser)) {
					log("NEW USER IN DB!");
					ArrayList<String> infos = new ArrayList<String>();				
					infos.add("Registrazione avvenuta con successo");
					request.setAttribute("infos", infos);
					//if is a restaurant owner check his partita iva and setup an authorization request that will be approved by admin
					if(registeredUser.getType().equals("ristoratore")) {
						AuthorizedOwnerBean authOwnerBean = new AuthorizedOwnerBean(registeredUser.getEmail(), request.getParameter("piva"));
						AuthorizedOwnerBeanDAO authDAO = new AuthorizedOwnerBeanDAO(); 
						if(!authDAO.doSave(authOwnerBean)) {
							log("error while saving authorization for owner!!");
						}
					}
					RequestDispatcher dispatcherToLogin = request.getRequestDispatcher("jsp/LoginForm.jsp");
					dispatcherToLogin.forward(request, response);
				
			} else {
				ArrayList<String> alerts = new ArrayList<String>();				
				alerts.add("Email già  registrata");
				request.setAttribute("alerts", alerts);
				RequestDispatcher dispatchToRegistration = request.getRequestDispatcher("jsp/RegistrationForm.jsp");
				dispatchToRegistration.forward(request, response);
			}
		} catch(IllegalParameterException exc) {
			log.warning("Fault with parameter: " + exc.getFaultyParameter() + "");
			throw new ServletException(exc);
		}
	}
	
	
	private Logger log = Logger.getLogger("controller");
}
