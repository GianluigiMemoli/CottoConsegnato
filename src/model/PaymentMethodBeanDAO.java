package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PaymentMethodBeanDAO {
	public synchronized boolean doSave(PaymentMethodBean paymentMethod) {
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("INSERT INTO payment_method(user, fullname, expiration_date, number, cvc) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, paymentMethod.getUser());
				stmt.setString(2, paymentMethod.getFullname());
				stmt.setString(3, paymentMethod.getExpirationDateString());
				stmt.setString(4, paymentMethod.getNumber()); 
				stmt.setString(5, paymentMethod.getCvc());
				stmt.executeUpdate();
				conn.commit();
				ResultSet keys = stmt.getGeneratedKeys();
				if(keys.next()) {
					Logger.getLogger("model").info("ID:" + keys.getInt(1));
				}
			} 			
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false; 
			} 
			finally {
				ConnectionPoolManager.releaseConnection(conn);
				try {
					stmt.close();					
				} 
				catch(SQLException exc) {
					exc.printStackTrace();
					return false;
				}
			}
			return true;					
		}
	
	public synchronized ArrayList<PaymentMethodBean> doRetrieveByKey(String userEmail) {
		ArrayList<PaymentMethodBean> paymentMethods = null; 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM payment_method WHERE user = ?"); 
			stmt.setString(1, userEmail);			
			ResultSet rs = stmt.executeQuery();
			paymentMethods = new ArrayList<PaymentMethodBean>();
			while(rs.next()) {
				paymentMethods.add(new PaymentMethodBean(
						rs.getString("user"),
						rs.getString("fullname"),
						rs.getString("number"),
						rs.getString("cvc"),
						rs.getString("expiration_date"),
						rs.getInt("id")
						));
			}
			
		ConnectionPoolManager.releaseConnection(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return paymentMethods;
	}
	
}
