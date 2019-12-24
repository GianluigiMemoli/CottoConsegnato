package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.UserBean;
import model.UserBeanDAO;

/**
 * Servlet implementation class UserUpdater
 */
@WebServlet("/UserUpdater")
public class UserUpdater extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdater() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserBean currentUser = ((UserBean)request.getSession().getAttribute("currentUser"));
		String oldEmail = currentUser.getEmail();
		log("oldemail: "+oldEmail);
		currentUser = ParameterValidator.updatedUserBean(request, currentUser);
		UserBeanDAO userbeanDAO = new UserBeanDAO();
		response.setContentType("text/plain");
		try {
			if(userbeanDAO.doUpdate(currentUser, oldEmail)) {
				request.getSession().setAttribute("currentUser", currentUser);
				response.setStatus(200);			
			} else {
				response.getWriter().write("Errore aggiornamento utente!");
				response.setStatus(500);		
			}
		} catch (SQLException e) {
			if(e instanceof SQLIntegrityConstraintViolationException) {
				response.getWriter().write("Email già in database");
				response.setStatus(500);					
			}
			e.printStackTrace();
		}
	}

	
}
