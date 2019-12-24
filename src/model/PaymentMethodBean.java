package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentMethodBean {			
	
	public PaymentMethodBean(String user, String fullname, String number, String cvc, String expirationMonth, String expirationYear) {
		super();
		this.user = user;
		this.fullname = fullname;
		this.number = number;
		this.cvc = cvc;
		try {
			this.expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse("20"+expirationYear+"-"+expirationMonth+"-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}		
	
	public PaymentMethodBean(String user, String fullname, String number, String cvc, String expirationDate, int id) {
		super();
		this.user = user;
		this.fullname = fullname;
		this.number = number;
		this.cvc = cvc;
		this.id = id;
		try {
			this.expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(expirationDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}		
	
	
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getCvc() {
		return cvc;
	}
	
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public String getExpirationDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(expirationDate);
	}
	
	
	@Override
	public String toString() {
		return "PaymentMethodBean [user=" + user + ", fullname=" + fullname + ", number=" + number + ", cvc=" + cvc
				+ ", id=" + id + ", expirationDate=" + expirationDate.toString() + ", toString()=" + super.toString() + "]";
	}



	private String user, fullname, number, cvc;
	private int id;
	private Date expirationDate;
}
