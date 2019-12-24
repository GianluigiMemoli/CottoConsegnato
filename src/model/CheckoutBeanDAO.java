package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

public class CheckoutBeanDAO {
	public synchronized int doSave(CheckoutBean checkout) throws SQLException {
		Connection conn = null;
		int currentID = -1;
		PreparedStatement stmt = null;
		// Getting a connection from pool
		conn = ConnectionPoolManager.getConnection();
		// Preparing the query string for insert
		stmt = conn.prepareStatement(
				"INSERT INTO checkout (orderDate, amount, street, province, cap, user, restaurant, payment_method) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, checkout.getOrderDateAsString());
		stmt.setDouble(2, checkout.getAmount());
		stmt.setString(3, checkout.getStreet());
		stmt.setString(4, checkout.getProvince());
		Logger.getLogger("model").info(checkout.getCap() + "<< cap");
		stmt.setString(5, checkout.getCap());
		stmt.setString(6, checkout.getClient());
		stmt.setString(7, checkout.getRestaurant());
		stmt.setInt(8, checkout.getPaymentMethod());
		stmt.executeUpdate();
		conn.commit();
		ResultSet keys = stmt.getGeneratedKeys();
		if (keys.next())
			currentID = keys.getInt(1);
		ConnectionPoolManager.releaseConnection(conn);
		stmt.close();

		return currentID;
	}
	
	public synchronized ArrayList<CheckoutBean> doRetrieveByUser(String userEmail) {
		ArrayList<CheckoutBean> checkouts = null; 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM checkout WHERE user = ?"); 
			stmt.setString(1, userEmail);			
			ResultSet rs = stmt.executeQuery();
			checkouts = new ArrayList<CheckoutBean>();
			while(rs.next()) {
				checkouts.add(new CheckoutBean(
						rs.getInt("number"),
						rs.getDouble("amount"),
						rs.getString("street"),
						rs.getString("province"),
						rs.getString("cap"),
						rs.getString("user"),
						rs.getString("restaurant"),
						rs.getInt("payment_method"),
						rs.getBoolean("accepted"),
						rs.getBoolean("rejected")
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
		return checkouts;
	}
	
	public synchronized ArrayList<CheckoutBean> doRetrieveByRestaurateur(String restaurateurEmail) {
		ArrayList<CheckoutBean> checkouts = null; 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM checkout WHERE restaurant = ?"); 
			stmt.setString(1, restaurateurEmail);			
			ResultSet rs = stmt.executeQuery();
			checkouts = new ArrayList<CheckoutBean>();
			while(rs.next()) {
				checkouts.add(new CheckoutBean(
						rs.getInt("number"),
						rs.getDouble("amount"),
						rs.getString("street"),
						rs.getString("province"),
						rs.getString("cap"),
						rs.getString("user"),
						rs.getString("restaurant"),
						rs.getInt("payment_method"),
						rs.getBoolean("accepted"),
						rs.getBoolean("rejected")
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
		return checkouts;
	}
	
	public synchronized boolean doAcceptOrRejectByNumber(int number, String attribute) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("UPDATE checkout SET "+attribute+" = 1  WHERE number = ?");
				stmt.setInt(1, number);									
				stmt.executeUpdate();
				conn.commit();
			} 			
			catch (SQLException e) {
				// TODO Auto-generated catch block
				if(e instanceof SQLIntegrityConstraintViolationException)
					throw e;
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
	
}
