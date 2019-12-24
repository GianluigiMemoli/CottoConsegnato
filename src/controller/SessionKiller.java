package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionKiller
 */
@WebServlet("/SessionKiller")
public class SessionKiller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionKiller() {
        super();
        // TODO Auto-generated constructor stub
    }



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		doPost(req, resp);
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		log("session killer");
		response.setContentType("text/plain");
		if(session != null) {
			log("not null");
			session.setAttribute("currentUser", null);
			session.invalidate();
			response.setStatus(200);
		} else {
			log("null");
			response.getWriter().write("Session already expired");
			response.setStatus(500);
		}
		
		response.getWriter().close();
		return;
	}

}
