package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompositionBeanDAO {
	public synchronized boolean doSave(CompositionBean composition) {
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("INSERT INTO composition(plate, ingredient, allergen, restaurant) VALUES(?, ?, ?, ?)");
				stmt.setString(1, composition.getPlate());
				stmt.setString(2, composition.getIngredient());				
				stmt.setBoolean(3, composition.isAllergen());
				stmt.setString(4, composition.getRestaurant());					
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
	public synchronized ArrayList<CompositionBean> doRetrieveByKey(String plate, String restaurant) {
		ArrayList<CompositionBean> composition = null; 
		PreparedStatement stmt = null;
		Connection conn = null; 
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM composition WHERE restaurant = ? AND plate = ?"); 
			stmt.setString(1, restaurant);		
			stmt.setString(2, plate);					
			ResultSet rs = stmt.executeQuery();
			composition = new ArrayList<CompositionBean>();
			while(rs.next()) {
				composition.add(new CompositionBean(
						rs.getString("plate"),
						rs.getString("ingredient"),
						rs.getBoolean("allergen"),
						rs.getString("restaurant")
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
		return composition;
	}

}
