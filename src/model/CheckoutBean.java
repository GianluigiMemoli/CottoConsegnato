package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckoutBean {
	public CheckoutBean(double amount, String street, String province, String cap,
		String client, String restaurant, int paymentMethod) {
		super();
		this.orderDate = new Date();
		this.amount = amount;
		this.street = street;
		this.province = province;
		this.cap = cap;
		this.client = client;
		this.restaurant = restaurant;
		this.paymentMethod = paymentMethod;
	}
	
	
	
	public CheckoutBean(int number, double amount, String street, String province, String cap,
			String client, String restaurant, int paymentMethod, boolean accepted, boolean rejected) {
		super();
		this.number = number;		
		this.orderDate = new Date();
		this.amount = amount;
		this.street = street;
		this.province = province;
		this.cap = cap;
		this.client = client;
		this.restaurant = restaurant;
		this.paymentMethod = paymentMethod;
		this.accepted = accepted;
		this.rejected = rejected; 
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public int getNumber() {
		return number;
	}		
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public String getOrderDateAsString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.orderDate);
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getClient() {
		return client;
	}
	
	public void setClient(String client) {
		this.client = client;
	}
	
	public String getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
	
	public int getPaymentMethod() {
		return this.paymentMethod;
	}
	
	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public String getStatus() {
		if(rejected) 
			return "rifiutato";
		else
		if(accepted)
			return "accettato";
		else
		if(!rejected && !accepted)
			return "attesa";
		return "error";
	}
	
	public boolean isWaiting() {
		if(!accepted && !rejected)
			return true;
		return false;
	}
	
	public boolean isRejected() {
		return rejected;
		
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	
	public String getCompleteAddress() {
		return street + "," + province + "," + cap;
	}
	
	private boolean accepted, rejected;
	private int number;
	private Date orderDate;
	private double amount;
	private String street, province, cap;
	private String client;
	private String restaurant;
	private int paymentMethod;
}
