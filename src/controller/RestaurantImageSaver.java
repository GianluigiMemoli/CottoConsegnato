package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import model.*;;

/**
 * 
 * Servlet implementation class RestaurantImageSaver
 */
@WebServlet("/RestaurantImageSaver")
public class RestaurantImageSaver extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RestaurantImageSaver() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = request.getReader();
		String rawData = br.readLine();
		log(rawData);
		JSONObject encodedRestaurantProfileImage;
		JSONParser parser = new JSONParser();
		try {
			encodedRestaurantProfileImage = (JSONObject) parser.parse(rawData);
			response.setContentType("text/plain");
			if (saveImage(encodedRestaurantProfileImage, request.getSession())) {
				response.setStatus(200);
			} else {
				response.setStatus(500);
				response.getWriter().write("Errore generazione file");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private boolean saveImage(JSONObject encodedImage, HttpSession session) {
		String owner = ((UserBean)session.getAttribute("currentUser")).getEmail();
		String imageBASE64 =	(String) encodedImage.get("encoded");
		RestaurantImageBean image = new RestaurantImageBean(owner, imageBASE64);
		RestaurantImageBeanDAO saver = new RestaurantImageBeanDAO();
		boolean result = saver.doSave(image);
		log(result + "< result");
		return result;
	}

}
