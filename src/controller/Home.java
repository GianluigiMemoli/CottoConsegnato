package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AuthorizedOwnerBean;
import model.AuthorizedOwnerBeanDAO;
import model.UserBean;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
    }

	private void _init(HttpServletRequest request) {
		request.setAttribute("infos", null);
		request.setAttribute("alerts", null);
        infos = new ArrayList<String>();
        alerts = new ArrayList<String>();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		_init(request);
		HttpSession session = request.getSession();
		UserBean currentUser = (UserBean) session.getAttribute("currentUser");	
		//log("userType: "+currentUser.getType());
		if(currentUser.getType().equals("ristoratore")) {
			manageOwner(currentUser, request, response);
		} else if(currentUser.getType().equals("cliente")) {
			manageClient(currentUser, request, response);
		} else if(currentUser.getType().equals("admin")) {
			manageAdmin(currentUser, request, response);
		}
	}
	
	private void manageOwner(UserBean owner, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		AuthorizedOwnerBeanDAO authOwner = new AuthorizedOwnerBeanDAO();
		AuthorizedOwnerBean authorized = authOwner.doRetrieveByKey(owner.getEmail());
		////log("in manageOwner");
		if(authorized.isWaiting()) {
			//dispatch to piva form with info alerts
			//log("is waiting");
			infos.add("La registrazione è avvenuta. Attendi l'autorizzazione di un admin.");
			request.setAttribute("infos", infos);
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/LoginForm.jsp");
			dispatcher.forward(request, response);
		} else if(!authorized.isRejected()){
			//Redirect to Restaurateur managing page
			response.sendRedirect(request.getContextPath() + "/Restaurateur.jsp");
		} else {
			//Authorization rejected
			alerts.add("La richiesta di registrazione è stata rifiutata."); 
			request.setAttribute("alerts", alerts);
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/LoginForm.jsp");
			dispatcher.forward(request, response);
			
		}
	}
	
	private void manageClient(UserBean client, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + "/Home.jsp");
	}
	
	private void manageAdmin(UserBean admin, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + "/Admin.jsp");
	}
	
	ArrayList<String> 	infos, //Contains low priority messages 
						alerts;//High priority messages 
}
























