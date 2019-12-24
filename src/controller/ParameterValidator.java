package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import model.RestaurantBean;
import model.UserBean;

public class ParameterValidator {
	public static UserBean fetchParameters(HttpServletRequest request) throws IllegalParameterException {
		String name, surname, email, password, type, birthDate;
		final String dateFormat = "yyyy-MM-dd";
		//check domain [A-z], minimum length 3, maximum 30
		final String noSymbolsPattern = "^[A-z]{3,30}$"; 
		//check domain [A-z][0-9][\W] (at least one from each domain required), minimum length 8, maximum 30
		final String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~]).{8,20}";
		//email regex
		final String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
		//piva regex
		final String pivaPattern = "[0-9]{11}";
		
		if(request.getParameter("name") == null) {
			throw new IllegalParameterException("name parameter invalid", "name");
		}
		
		name = request.getParameter("name");

		if(!Pattern.matches(noSymbolsPattern, name))
			throw new IllegalParameterException("name parameter invalid", "name");
		
		if(request.getParameter("surname") == null) {
			throw new IllegalParameterException("surname parameter invalid", "surname");
		}
		surname = request.getParameter("surname");
		
		if(!Pattern.matches(noSymbolsPattern, surname))
			throw new IllegalParameterException("surname parameter invalid", "surname");
				
		if(request.getParameter("type") == null) {
			throw new IllegalParameterException("type parameter invalid", "type");
		}

		type = request.getParameter("type");
		
		if(!(type.equals("cliente") || type.equals("ristoratore"))) {
			throw new IllegalParameterException("type parameter invalid value:"+type, "type");
		}
		
		if(type.equals("ristoratore")) {
			if(request.getParameter("piva") == null) {
				throw new IllegalParameterException("piva parameter invalid", "piva");
			} else {
				String piva = request.getParameter("piva");
				if(!Pattern.matches(pivaPattern, piva))
					throw new IllegalParameterException("piva parameter invalid", "piva");
			}
		}
		
		if(request.getParameter("password") == null) {
			throw new IllegalParameterException("password parameter invalid", "password");
		}
		password = request.getParameter("password");
		if(!Pattern.matches(passwordPattern, password))
			throw new IllegalParameterException("password parameter invalid", "password");
		

		if(request.getParameter("email") == null) {
			throw new IllegalParameterException("email parameter invalid", "email");
		}		
		email = request.getParameter("email");
		if(email.length() > 320) {
			throw new IllegalParameterException("email parameter invalid", "email");
		}
		
		if(!Pattern.matches(emailPattern, email))
			throw new IllegalParameterException("email parameter invalid", "email");

				
		if(request.getParameter("birthDate") == null) {
			throw new IllegalParameterException("birthDatenull parameter invalid", "birthDate");
		}
		
		birthDate = request.getParameter("birthDate");
		
		//Testing data format with this SimpleDateFormat object
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);		
		try {
			sdf.parse(birthDate);
		} catch(ParseException e) {
			throw new IllegalParameterException("birthDate parameter invalid", "birthDate");
		}	
		 
		return new UserBean(name, surname, email.toLowerCase(), password, birthDate, type);		
	}
	
	public static boolean validateLogin(HttpServletRequest request) {		
		//check domain [A-z][0-9][\W] (at least one from each domain required), minimum length 8, maximum 30
		final String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~]).{8,20}";
		//email regex
		final String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
		
		if(request.getParameter("email") == null) {
			throw new IllegalParameterException("email null", "email");
		}
		
		String email = request.getParameter("email");
		if(!Pattern.matches(emailPattern, email)) {
			throw new IllegalParameterException("email invalid", "email");

		}
		
		if(email.length() > 320) {
			throw new IllegalParameterException("email invalid", "email");
		}
				
		if(request.getParameter("password") == null) {
			throw new IllegalParameterException("password null", "password");
		}
		
		String password = request.getParameter("password");
		if(!Pattern.matches(passwordPattern, password)) {
			throw new IllegalParameterException("password invalid", "password");

		}
		
		return true;		
	}
	
	public static boolean validepIVA(String pIVA) {
		final String pIVAPattern = "[0-9]{11}";
		if(!Pattern.matches(pIVAPattern, pIVA)) {
			throw new IllegalParameterException("pIVA invalid", "pIVA");
		}
		return true;
	}
	public static RestaurantBean validateRestaurant(HttpServletRequest request) {
		//textOnly
		final String textOnlyPattern = "^([A-z-'a-zÀ-ÿ]+\\s*)+$"; 
		//province pattern
		final String provincePattern = "^[A-z]{2}$"; 
		//cap pattern
		final String capPattern = "^\\d{5}$";
		//check if params are set
		if(request.getParameter("restaurantName") == null)
			throw new IllegalParameterException("Restaurant name not set", "restaurantName"); 
		String restaurantName = request.getParameter("restaurantName");
		
		if(request.getParameter("restaurantCategory") == null)
			throw new IllegalParameterException("Restaurant category not set", "restaurantCategory"); 
		String restaurantCategory = request.getParameter("restaurantCategory");
		
		if(request.getParameter("restaurantStreet") == null)
			throw new IllegalParameterException("Restaurant street not set", "restaurantStreet"); 
		String restaurantStreet = request.getParameter("restaurantStreet");
		
		if(request.getParameter("restaurantCap") == null)
			throw new IllegalParameterException("Restaurant cap not set", "restaurantCap"); 
		String restaurantCap = request.getParameter("restaurantCap");
		
		if(request.getParameter("restaurantProvince") == null)
			throw new IllegalParameterException("Restaurant province not set", "restaurantProvince"); 
		String restaurantProvince = request.getParameter("restaurantProvince"); 
		
		if(request.getParameter("restaurantCity") == null)
			throw new IllegalParameterException("Restaurant city not set", "restaurantCity"); 
		String restaurantCity = request.getParameter("restaurantCity"); 
		
		if(((UserBean) request.getSession().getAttribute("currentUser")).getEmail() == null)
			throw new IllegalParameterException("email not in session", "email");
		String email = ((UserBean) request.getSession().getAttribute("currentUser")).getEmail(); 
		
		//validation 
		if(!Pattern.matches(textOnlyPattern, restaurantName))
			throw new IllegalParameterException("Restaurant name malformed", "restaurantName"); 
		
		if(!Pattern.matches(textOnlyPattern, restaurantCategory))
			throw new IllegalParameterException("Restaurant category malformed", "restaurantCategory"); 
		
		if(!Pattern.matches(textOnlyPattern, restaurantStreet))
			throw new IllegalParameterException("Restaurant street malformed", "restaurantStreet"); 
		
		if(!Pattern.matches(textOnlyPattern, restaurantCity))
			throw new IllegalParameterException("Restaurant city malformed", "restaurantCity"); 
		
		if(!Pattern.matches(capPattern, restaurantCap))
			throw new IllegalParameterException("Restaurant cap malformed", "restaurantCap"); 
		
		if(!Pattern.matches(provincePattern, restaurantProvince))
			throw new IllegalParameterException("Restaurant province malformed", "restaurantProvince"); 
		
		
		
		return new RestaurantBean(restaurantName, email, restaurantCategory, 
				restaurantStreet, restaurantCap, restaurantCity, restaurantProvince);		
	}
	
	public static UserBean updatedUserBean(HttpServletRequest request, UserBean userToUpdate) throws IllegalParameterException {
		String name, surname, email, birthDate;
		final String dateFormat = "yyyy-MM-dd";
		//check domain [A-z], minimum length 3, maximum 30
		final String noSymbolsPattern = "^[A-z]{3,30}$"; 		
		//email regex
		final String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
		
		if(request.getParameter("name") == null) {
			throw new IllegalParameterException("name parameter invalid", "name");
		}
		
		name = request.getParameter("name");

		if(!Pattern.matches(noSymbolsPattern, name))
			throw new IllegalParameterException("name parameter invalid", "name");
		
		if(request.getParameter("surname") == null) {
			throw new IllegalParameterException("surname parameter invalid", "surname");
		}
		surname = request.getParameter("surname");
		
		if(!Pattern.matches(noSymbolsPattern, surname))
			throw new IllegalParameterException("surname parameter invalid", "surname");

		if(request.getParameter("email") == null) {
			throw new IllegalParameterException("email parameter invalid", "email");
		}	
		
		email = request.getParameter("email");
		if(email.length() > 320) {
			throw new IllegalParameterException("email parameter invalid", "email");
		}
		
		if(!Pattern.matches(emailPattern, email))
			throw new IllegalParameterException("email parameter invalid", "email");

				
		if(request.getParameter("birthdate") == null) {
			throw new IllegalParameterException("birthDatenull parameter invalid", "birthDate");
		}
		
		birthDate = request.getParameter("birthdate");
		
		//Testing data format with this SimpleDateFormat object
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);		
		try {
			sdf.parse(birthDate);
		} catch(ParseException e) {
			throw new IllegalParameterException("birthdate parameter invalid", "birthDate");
		}	
		
		userToUpdate.setName(name);
		userToUpdate.setEmail(email);
		userToUpdate.setBirthDate(birthDate);
		userToUpdate.setSurname(surname);
		return userToUpdate;		
	}
	
	public static boolean isAValidPassword(String password) {
		final String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~]).{8,20}";
		return (Pattern.matches(passwordPattern, password));
			
	}
	
}
