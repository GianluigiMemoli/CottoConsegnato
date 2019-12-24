package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;
/**
 * Servlet implementation class PasswordChanger
 */
@WebServlet("/PasswordChanger")
public class PasswordChanger extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordChanger() {
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
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String currentPassword = getCurrentPassword(request.getSession());
		//comparing user input old password with real old password
		if(oldPassword.equals(currentPassword)) {
			String email = getUserEmail(request.getSession());
			UserBeanDAO userbeanDAO = new UserBeanDAO();
			if(ParameterValidator.isAValidPassword(newPassword)) {
				if(userbeanDAO.doUpdatePassword(newPassword, email)) {
					updateSession(request.getSession(), newPassword);
					response.setStatus(200);
					response.getWriter().write("Password cambiata");

				} else { 
					response.setContentType("text/plain");
					response.getWriter().write("Errore durante salvataggio password");
					response.setStatus(500);
				}
			} else {
				response.setContentType("text/plain");
				response.getWriter().write("Password non valida");
				response.setStatus(500);
			}			 
		} else {
			response.setContentType("text/plain");
			response.getWriter().write("Password non valida");
			response.setStatus(500);
		}
	}
	
	private void updateSession(HttpSession session, String newPassword) {
		((UserBean)session.getAttribute("currentUser")).setPassword(newPassword);
	}
	
	private String getCurrentPassword(HttpSession session) {
		return ((UserBean)session.getAttribute("currentUser")).getPassword();
	}
	
	private String getUserEmail(HttpSession session) {
		return ((UserBean)session.getAttribute("currentUser")).getEmail();
	}
	
	
}
