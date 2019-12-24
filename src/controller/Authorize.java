package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AuthorizedOwnerBean;
import model.AuthorizedOwnerBeanDAO;
import model.UserBean;

/**
 * Servlet implementation class Authorize
 */
@WebServlet("/Authorize")
public class Authorize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authorize() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log("auth");
		String admin = ((UserBean)request.getSession().getAttribute("currentUser")).getEmail();
		AuthorizedOwnerBean owner;
		AuthorizedOwnerBeanDAO ownerDAO = new AuthorizedOwnerBeanDAO();
		String email;
		if(request.getParameter("accept") != null) {
			email = request.getParameter("accept");
			owner = ownerDAO.doRetrieveByKey(email);
			owner.setAdministrator(admin);
			owner.setAuthorizationDate(new Date());
			if(ownerDAO.doSave(owner)) {
				response.setContentType("text/plain");
				response.getWriter().write("Ristoratore abilitato");
				response.setStatus(200);
			} else {
				response.setContentType("text/plain");
				response.getWriter().write("Errore");
				response.setStatus(500);
			}
		}
		if(request.getParameter("reject") != null) {
			email = request.getParameter("reject");
			owner = ownerDAO.doRetrieveByKey(email);
			log("owner: "+ owner);
			owner.setAdministrator(admin);
			owner.setAuthorizationDate(new Date());		
			owner.reject();
			if(ownerDAO.doSave(owner)) {
				response.setContentType("text/plain");
				response.getWriter().write("Ristoratore rifiutato");
				response.setStatus(200);
			} else {
				response.setContentType("text/plain");
				response.getWriter().write("Errore");
				response.setStatus(500);
			}
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
