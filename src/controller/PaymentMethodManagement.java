package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.PaymentMethodBean;
import model.PaymentMethodBeanDAO;
import model.UserBean;

/**
 * Servlet implementation class PaymentMethodManagement
 */
@WebServlet("/PaymentMethodManagement")
public class PaymentMethodManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentMethodManagement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		try {
			doAction(getRequest(request), request, response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			sendResponse("text/plain", "Errore durante il parsing", 500, response);
			e.printStackTrace();
		}
	}

	private JSONObject getRequest(HttpServletRequest request) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject data = (JSONObject) parser.parse(request.getReader());		
		return data;		
	}
	
	private void doAction(JSONObject objectRetrieved, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log(objectRetrieved.toJSONString());
		String action = (String) objectRetrieved.get("action");
		if(action.equals("put")) {
			JSONObject paymentMethod = (JSONObject) objectRetrieved.get("paymentMethod");
			paymentMethod.put("user", getUserEmail(request));
			if(savePaymentMethod(paymentMethod)) {
				sendResponse("text/plain", "Metodo salvato", 200, response);
			} else {
				sendResponse("text/plain", "Errore salvataggio metodo", 500, response);
			}
			return;
		}		
		if(action.equals("get")) {
			JSONArray paymentMethodsRetrieved = getPaymentMethods(request);
			sendResponse("application/json", paymentMethodsRetrieved.toJSONString(), 200, response);
		}
	}
	
	private JSONArray getPaymentMethods(HttpServletRequest request) {
		JSONArray arrayOfPaymentMethods = new JSONArray();
		String userEmail = getUserEmail(request);
		PaymentMethodBeanDAO paymentMethodsRetriever = new PaymentMethodBeanDAO();		
		ArrayList<PaymentMethodBean> paymentMethodsList = paymentMethodsRetriever.doRetrieveByKey(userEmail);
		for(PaymentMethodBean method : paymentMethodsList) {
			JSONObject newPaymentMethod = new JSONObject();
			newPaymentMethod.put("cardNumber", censureNumber(method.getNumber()));
			newPaymentMethod.put("expirationDate", collapseDate(method.getExpirationDateString()));
			newPaymentMethod.put("id", method.getId());
			arrayOfPaymentMethods.add(newPaymentMethod);			
		}
		return arrayOfPaymentMethods;
	}
	
	private boolean savePaymentMethod(JSONObject newPaymentMethod) {
		PaymentMethodBeanDAO paymentSaver = new PaymentMethodBeanDAO();		
		PaymentMethodBean paymentMethod = new PaymentMethodBean(
				(String)newPaymentMethod.get("user"),
				(String)newPaymentMethod.get("fullname"),
				(String)newPaymentMethod.get("cardNumber"),
				(String)newPaymentMethod.get("cvc"),
				(String)newPaymentMethod.get("expirationMonth"),
				(String)newPaymentMethod.get("expirationYear")
				);
		return paymentSaver.doSave(paymentMethod);
	}
	
	private String collapseDate(String date) {
		//date will be in format yyyy-mm-dd
		//i want to show it as yy-mm
		return date.substring(2, 7);
	}
	
	private String censureNumber(String number) {
		//This method is to show just the first 4 digits and last 4
		//Not very secure because in case of number length = 10, only 2 char are censured
		final int FIRST_CENSURED_CHAR = 5;
		final int FIRST_CLEAR_CHAR = number.length() - 4;
		String censured = number.substring(0, FIRST_CENSURED_CHAR);
		censured += "****";
		censured += number.substring(FIRST_CLEAR_CHAR);
		return censured;
	}
	
	private String getUserEmail(HttpServletRequest request) {
		UserBean user = (UserBean) request.getSession().getAttribute("currentUser");
		return user.getEmail();
	}
	
	private void sendResponse(String contentType, String responseContent, int statusCode, HttpServletResponse response) throws IOException {
		response.setContentType(contentType);
		response.setStatus(statusCode);
		response.getWriter().write(responseContent);
		response.getWriter().close();
	}
}
