package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;

/**
 * Servlet implementation class Tester
 */
@WebServlet("/Tester")
public class Tester extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public Tester() throws SQLException {
        super();
        // TODO Auto-generated constructor stub
        ConnectionPoolManager.getConnection();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RestaurantImageBean img = new RestaurantImageBean("emailer@emailest.com", "natacos");
		RestaurantImageBeanDAO imgDAO = new RestaurantImageBeanDAO();
		imgDAO.doSave(img);
		/*CheckoutItemBeanDAO itemsaver = new CheckoutItemBeanDAO();
		CheckoutItemBean item = new CheckoutItemBean(2, 2, "Fanta");
		itemsaver.doSave(item);*/
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
 