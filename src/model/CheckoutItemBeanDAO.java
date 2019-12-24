package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckoutItemBeanDAO {
	public synchronized boolean doSave(CheckoutItemBean item) {
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("INSERT INTO checkout_item(checkout, quantity, item_name) VALUES(?, ?, ?)");
				stmt.setInt(1, item.getCheckoutNumber());
				stmt.setInt(2, item.getQuantity());
				stmt.setString(3, item.getItemName());									
				stmt.executeUpdate();
				conn.commit();
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
	
	public synchronized ArrayList<CheckoutItemBean> doRetrieveByCheckout(int number) {
		ArrayList<CheckoutItemBean> checkoutItems = null; 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM checkout_item WHERE checkout = ?"); 
			stmt.setInt(1, number);			
			ResultSet rs = stmt.executeQuery();
			checkoutItems = new ArrayList<CheckoutItemBean>();
			while(rs.next()) {
				checkoutItems.add(new CheckoutItemBean(
						rs.getInt("checkout"),
						rs.getInt("quantity"),
						rs.getString("item_name")						
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
		return checkoutItems;
	}
}
