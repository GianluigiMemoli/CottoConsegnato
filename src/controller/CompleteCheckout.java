package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.CartBean;
import model.CheckoutBean;
import model.CheckoutBeanDAO;
import model.CheckoutItemBean;
import model.CheckoutItemBeanDAO;
import model.RestaurantBean;
import model.UserBean;
import model.ItemBean;

/**
 * Servlet implementation class CompleteCheckout
 */
@WebServlet("/CompleteCheckout")
public class CompleteCheckout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompleteCheckout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {			
			JSONObject dataFromRequest = getData(request);
			log(dataFromRequest.toJSONString());
			JSONObject address = getAddress(dataFromRequest);
			// gettin all data for a CheckoutBean instance
			String street = getStreet(address);
			String cap = getCap(address);
			String province = getProvince(address);
			int paymentMethod = getPaymentMethodID(dataFromRequest);

			double amount = getAmount(request.getSession());
			String clientEmail = getClientEmail(request.getSession());
			String restaurateurEmail = getRestaurateurEmail(request.getSession());

			// making a CheckoutBean instance			

			CheckoutBean completedCheckout = new CheckoutBean(amount, street, province, cap, clientEmail,
					restaurateurEmail, paymentMethod);
			CheckoutBeanDAO checkoutSaver = new CheckoutBeanDAO();
			int checkoutNumber = checkoutSaver.doSave(completedCheckout);
			if (checkoutNumber >= 0) {
				Hashtable itemsWithQuantity = mapProductWithQuantity(request.getSession());
				ArrayList<CheckoutItemBean> checkoutItems = makeCheckoutItemList(itemsWithQuantity, checkoutNumber);
				CheckoutItemBeanDAO checkoutItemSaver = new CheckoutItemBeanDAO();
				for (CheckoutItemBean item : checkoutItems) {
					if (!checkoutItemSaver.doSave(item)) {
						sendResponse("text/plain", "Error saving checkout item", 500, response);
					}
				}
				sendResponse("text/plain", "Ok", 200, response);
			} else {
				sendResponse("text/plain", "Error saving checkout", 500, response);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			sendResponse("text/plain", "Errore durante il parsing", 500, response);
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			sendResponse("text/plain", "Errore grave durante il salvataggio", 500, response);

			e.printStackTrace();
		}

	}

	private JSONObject getData(HttpServletRequest request) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(request.getReader());
	}

	private JSONObject getAddress(JSONObject data) throws IOException, ParseException {
		return (JSONObject) data.get("address");
	}

	private String getStreet(JSONObject data) {
		return (String) data.get("street");
	}

	private String getCap(JSONObject data) {
		return (String) data.get("CAP");
	}

	private String getProvince(JSONObject data) {
		return (String) data.get("province");
	}

	private int getPaymentMethodID(JSONObject data) {
		return Integer.parseInt((String) data.get("paymentMethod"));
	}

	private void sendResponse(String contentType, String responseContent, int statusCode, HttpServletResponse response)
			throws IOException {
		response.setContentType(contentType);
		response.setStatus(statusCode);
		response.getWriter().write(responseContent);
		response.getWriter().close();
	}

	private String getClientEmail(HttpSession session) {
		UserBean client = (UserBean) session.getAttribute("currentUser");
		return client.getEmail();
	}

	private String getRestaurateurEmail(HttpSession session) {
		RestaurantBean restaurant = (RestaurantBean) session.getAttribute("currentRestaurant");
		return restaurant.getOwner();
	}

	private double getAmount(HttpSession session) {
		CartBean cart = (CartBean) session.getAttribute("currentCart");
		return cart.getTotal();
	}

	private Hashtable<ItemBean, Integer> mapProductWithQuantity(HttpSession session) {
		// Mapping every item only a time if it's repeated, with his relative quantity
		CartBean currentCart = (CartBean) session.getAttribute("currentCart");
		ArrayList<ItemBean> products = currentCart.getProducts();
		Hashtable<ItemBean, Integer> productsMapped = new Hashtable<ItemBean, Integer>();
		for (ItemBean product : products) {
			if (productsMapped.containsKey(product)) {
				productsMapped.put(product, productsMapped.get(product) + 1);
				log(product.toString() + "is in table");
			} else {
				productsMapped.put(product, 1);
				log(product.toString() + "not in table");
			}
		}
		return productsMapped;
	}

	private ArrayList<CheckoutItemBean> makeCheckoutItemList(Hashtable<ItemBean, Integer> itemsWithQuantity,
			int checkoutNumber) {
		ArrayList<CheckoutItemBean> checkoutItems = new ArrayList<CheckoutItemBean>();
		Set<ItemBean> productsInTable = itemsWithQuantity.keySet();
		for (ItemBean product : productsInTable) {
			checkoutItems.add(new CheckoutItemBean(checkoutNumber, itemsWithQuantity.get(product), product.getName()));
		}
		return checkoutItems;
	}

}
