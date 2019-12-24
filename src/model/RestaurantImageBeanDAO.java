package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RestaurantImageBeanDAO {
	public synchronized boolean doSave(RestaurantImageBean image) {
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				Logger.getLogger("model").info("PRE committing");
				//Preparing the query string for insert
				stmt = conn.prepareStatement("INSERT INTO restaurant_image(restaurant_owner, image) VALUES(?, ?) ON DUPLICATE KEY UPDATE image = ?");
				stmt.setString(1, image.getOwner());
				stmt.setString(2, image.getImage());
				stmt.setString(3, image.getImage());
				Logger.getLogger("model").info("PRE committing");
				stmt.executeUpdate();
				conn.commit();
				Logger.getLogger("model").info(image.toString()); 
				Logger.getLogger("model").info("POST committing");

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
	
	public synchronized RestaurantImageBean doRetrieveByKey(String restaurantOwner) {
		RestaurantImageBean image = null; 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM restaurant_image WHERE restaurant_owner = ?"); 
			stmt.setString(1, restaurantOwner);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				image = new RestaurantImageBean(
						rs.getString("restaurant_owner"),
						rs.getString("image")
						);
			} else {Logger.getLogger("model").info("no image");}
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
		return image;
	}
}
