package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserBean {
	
	//The date from forms field come as Strings
	public UserBean(String name, String surname, String email, String password, String birthDate, String type) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		try {
			this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.type = type;
		registrationDate = new Date();
	}
	
	//Constructor for initializing an UserBean from DB Data ()
	public UserBean(String name, String surname, String email, String password, String type, String birthDate, String registrationDate){
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password; 
		this.type = type;
		setBirthDate(birthDate);
		setRegistrationDate(registrationDate);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public String getBirthDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(birthDate);
	}
	
	public void setBirthDate(String birthDate) {
		try {
			this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Date getRegistrationDate() {
		return registrationDate;
	}
	
	public String getRegistrationDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(registrationDate);
	}
	
	public void setRegistrationDate(String regDate)  {
		try {
			this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "Class: UserBean\n"
				+ " name: " + name 
				+ " surname: " + surname
				+ " email: " + email
				+ " password: "+ password
				+ " birthDate: " + getBirthDateString() 
				+ " regDate: " + getRegistrationDateString()
				+ " type: " + type;
	}	
	
	
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private Date registrationDate;
	private Date birthDate;
	private String type;
	
}
