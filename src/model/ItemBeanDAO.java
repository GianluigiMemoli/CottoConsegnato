package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ItemBeanDAO {
	private Logger log;
	public ItemBeanDAO(){
		log = Logger.getLogger("model");
	}
	public synchronized boolean doSave(ItemBean item) {
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("INSERT INTO menu_item(name, restaurant, price, hasComposition, category, restaurant_owner) VALUES(?, ?, ?, ?, ?, ?)");
				stmt.setString(1, item.getName());
				stmt.setString(2, item.getRestaurant());
				stmt.setDouble(3, item.getPrice());
				stmt.setBoolean(4, item.hasComposition());
				stmt.setString(5, item.getCategory());	
				stmt.setString(6, item.getRestaurantOwner());					
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

	public synchronized ArrayList<ItemBean> doRetrieveByKey(String restaurant, String restaurantOwner) {
		ArrayList<ItemBean> menu = null; 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM menu_item WHERE restaurant = ? AND restaurant_owner = ?"); 
			stmt.setString(1, restaurant);	
			stmt.setString(2, restaurantOwner);				
			ResultSet rs = stmt.executeQuery();
			menu = new ArrayList<ItemBean>();
			while(rs.next()) {
				menu.add(new ItemBean(
						rs.getString("name"),
						rs.getString("restaurant"),
						rs.getString("restaurant_owner"),						
						rs.getDouble("price"),
						rs.getBoolean("hasComposition"),
						rs.getString("category")						
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
		return menu;
	}
	
	public synchronized ItemBean doRetrieveByName(String restaurant, String restaurantOwner, String name) {
		ItemBean item = null; 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM menu_item WHERE restaurant = ? AND restaurant_owner = ? AND name = ?"); 
			stmt.setString(1, restaurant);			
			stmt.setString(2, restaurantOwner);
			stmt.setString(3, name);						
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				item = new ItemBean(
						rs.getString("name"),
						rs.getString("restaurant"),
						rs.getString("restaurant_owner"),						
						rs.getDouble("price"),
						rs.getBoolean("hasComposition"),
						rs.getString("category")
						);
			} else {Logger.getLogger("model").info("no item");}
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
		return item;
	}
	
	public synchronized boolean doRemoveByKey(String itemName, String restaurant, String restaurantOwner) {
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("DELETE FROM menu_item WHERE name = ? AND restaurant = ? AND restaurant_owner = ?");
				stmt.setString(1, itemName);
				stmt.setString(2, restaurant);	
				stmt.setString(3, restaurantOwner);					
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
	
}
