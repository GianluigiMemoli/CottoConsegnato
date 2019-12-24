package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserBean;
import model.UserBeanDAO;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private void _init(HttpServletRequest request) {
		request.setAttribute("infos", null);
		request.setAttribute("alerts", null);
        alerts = new ArrayList<String>();        
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//log("in login servlet");
		_init(request); 
		//Retriever for userLogged
		UserBeanDAO usrDAO = new UserBeanDAO();
		//The UserBean relative to the credentials will be stored in this object
		UserBean userLogged;
		if(ParameterValidator.validateLogin(request)) {
			userLogged = usrDAO.doRetrieveByKey(request.getParameter("email").toLowerCase());
			//Check if userLogged has retrieved something by email and check the password equality
			if(userLogged != null && userLogged.getPassword().equals(request.getParameter("password"))) {								
				//It's valid, Create a session 
				HttpSession userSession = request.getSession();
				//Keep session always alive
				userSession.setMaxInactiveInterval(-1);
				//Setting UserBean as session attribute
				userSession.setAttribute("currentUser", userLogged);			
				//log("login valido dispatch to home");
				//Home dispatching
				RequestDispatcher dispatcherToHome = getServletContext().getRequestDispatcher("/Home");
				dispatcherToHome.forward(request, response);
			} else {
				//If user's email is not in DB send a warning alert in the request						
				if(userLogged == null) {
					//No user in DB
					alerts.add("L'email inserita non è relativa a nessun account, registrati!");
					//log("utente non presente");
				} else if(!userLogged.getPassword().equals(request.getParameter("password"))) {
					//Wrong password
					alerts.add("Login incorretto.");
					//log("userLogged real pwd: "+userLogged.getPassword());
					//log("password: "+request.getParameter("password") +" sbagliata");
				}
				//log("aggiunto alert:"+alerts);
				request.setAttribute("alerts", alerts);
				//log("Dispatching");
				RequestDispatcher dispatcherToLogin = getServletContext().getRequestDispatcher("/jsp/LoginForm.jsp");
				dispatcherToLogin.forward(request, response);
			}
		}
	}
	
	private ArrayList<String> alerts;
	
}
