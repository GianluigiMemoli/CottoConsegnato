package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 *
+----------+--------------+------+-----+---------+-------+
| Field    | Type         | Null | Key | Default | Extra |
+----------+--------------+------+-----+---------+-------+
| name     | varchar(30)  | NO   | PRI | NULL    |       |
| owner    | varchar(320) | NO   | PRI | NULL    |       |
| category | varchar(30)  | NO   |     | NULL    |       |
| street   | varchar(50)  | NO   |     | NULL    |       |
| cap      | int(11)      | NO   |     | NULL    |       |
| city     | varchar(50)  | NO   |     | NULL    |       |
+----------+--------------+------+-----+---------+-------+
----------+-------------+------+-----+---------+-------+

 * 
 * */
public class RestaurantBeanDAO {
	
	public synchronized boolean doSave(RestaurantBean newRestaurant) {
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("INSERT INTO Restaurant VALUES(?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1, newRestaurant.getName());
				stmt.setString(2, newRestaurant.getOwner());
				stmt.setString(3, newRestaurant.getCategory());
				stmt.setString(4, newRestaurant.getStreet());
				stmt.setString(5, newRestaurant.getCap());
				stmt.setString(6, newRestaurant.getCity());	
				stmt.setString(7, newRestaurant.getProvince());	
				
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

	public synchronized boolean doUpdate(RestaurantBean newRestaurant) {
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("UPDATE Restaurant SET name = ?, category = ?, street = ?, cap = ?, city = ?, province = ? WHERE owner = ?");
				stmt.setString(1, newRestaurant.getName());				
				stmt.setString(2, newRestaurant.getCategory());
				stmt.setString(3, newRestaurant.getStreet());
				stmt.setString(4, newRestaurant.getCap());
				stmt.setString(5, newRestaurant.getCity());	
				stmt.setString(6, newRestaurant.getProvince());	
				stmt.setString(7, newRestaurant.getOwner());
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
	
	
	public synchronized RestaurantBean doRetrieveByKey(String owner) {
		RestaurantBean restaurant = null; 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM Restaurant WHERE owner = ?"); 
			stmt.setString(1, owner);			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				restaurant = new RestaurantBean(
						rs.getString("name"),
						rs.getString("owner"),
						rs.getString("category"),
						rs.getString("street"),
						rs.getString("cap"),
						rs.getString("city"),
						rs.getString("province")
						);
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
		
		return restaurant;
	}
	
	public synchronized ArrayList<RestaurantBean> doRetrieveByProvince(String province) {
		ArrayList<RestaurantBean> restaurants = new ArrayList<RestaurantBean>(); 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM Restaurant WHERE province = ?"); 
			stmt.setString(1, province);			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				restaurants.add(new RestaurantBean(
						rs.getString("name"),
						rs.getString("owner"),
						rs.getString("category"),
						rs.getString("street"),
						rs.getString("cap"),
						rs.getString("city"),
						rs.getString("province")
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
		
		return restaurants;
	}
	
}
