package model;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AuthorizedOwnerBean {
	Logger log; 
	public AuthorizedOwnerBean(String owner, String pIVA) {
		//Chiamato per registrare la richesta di autorizzazione da DAO
		this.owner = owner; 
		this.pIVA = pIVA; 
		this.rejected = false; 
		administrator = null; 	
		log = Logger.getLogger("model"); 
		log.setLevel(Level.INFO);
	}
	
	public AuthorizedOwnerBean(String owner, String administrator, String authorizationDate, String pIVA, boolean rejected) {
		//Chiamato quando si ottiene da DAO 
		log = Logger.getLogger("model"); 
		log.setLevel(Level.INFO);
		this.owner = owner; 
		this.pIVA = pIVA; 
		this.administrator = administrator;
		this.rejected = rejected; 
		if(authorizationDate != null) {
			try {
				this.authorizationDate = new SimpleDateFormat("yyyy-MM-dd").parse(authorizationDate);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}	
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAdministrator() {
		return administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public Date getAuthorizationDate() {
		return authorizationDate;
	}

	public void setAuthorizationDate(Date authorizationDate) {
		this.authorizationDate = authorizationDate;
	}

	public String getpIVA() {
		return pIVA;
	}

	public void setpIVA(String pIVA) {
		this.pIVA = pIVA;
	}
	
	public void reject() {
		this.rejected = true;
	}
	
	public String getAuthorizationDateString() {
		if(authorizationDate == null)
			return null; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(authorizationDate);
	}
	

	public boolean isRejected() {
		return rejected; 
	}
	
	public boolean isWaiting() {
		//ritorna true se non rifiutato ma non autorizzato		
		return (!rejected && administrator == null); 		
	}

	private String owner;
	private String administrator;
	private Date authorizationDate = null;
	private String pIVA; 
	private boolean rejected; 
				   
				   
}
