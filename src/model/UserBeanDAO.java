package model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserBeanDAO {
	/*
	
	DESCRIBE Users;
	+------------------+--------------+------+-----+---------+-------+
	| Field            | Type         | Null | Key | Default | Extra |
	+------------------+--------------+------+-----+---------+-------+
	| name             | varchar(30)  | NO   |     | NULL    |       |
	| surname          | varchar(30)  | NO   |     | NULL    |       |
	| email            | varchar(320) | NO   | PRI | NULL    |       |
	| password         | varchar(30)  | NO   |     | NULL    |       |
	| type             | int(11)      | YES  |     | -1      |       |
	| birthDate        | date         | NO   |     | NULL    |       |
	| registrationDate | date         | NO   |     | NULL    |       |
	+------------------+--------------+------+-----+---------+-------+

*/
	
	public UserBeanDAO() {
		log = Logger.getLogger("model");
		log.setLevel(Level.INFO);
	}
	private Logger log;
	
	
	public synchronized boolean doSave(UserBean newUser){
		if(doRetrieveByKey(newUser.getEmail()) != null) {
			log.info("Already in db with email "+newUser.getEmail());
			return false;
			
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, newUser.getName());
			stmt.setString(2, newUser.getSurname());
			stmt.setString(3, newUser.getEmail());
			stmt.setString(4, newUser.getPassword());
			stmt.setString(5, newUser.getType());
			stmt.setString(6, newUser.getBirthDateString());
			stmt.setString(7, newUser.getRegistrationDateString());
			stmt.executeUpdate();
			conn.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionPoolManager.releaseConnection(conn);
			try {
				stmt.close();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	public synchronized UserBean doRetrieveByKey(String email) {
		/*This method returns an UserBean if the parameters email and password are associated
		  to an existing users in DB, otherwise returns null 
		*/
		UserBean user = null; 
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = ? ");
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			//if ResultSet is not empty get the values
			if(rs.next()) {
				user = new UserBean(
						rs.getString("name"),
						rs.getString("surname"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("type"),
						rs.getString("birthDate"),
						rs.getString("registrationDate")
						);
			}			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPoolManager.releaseConnection(conn);
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;		
	}
	
	public synchronized boolean doUpdate(UserBean newUser, String currentEmail) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("UPDATE users SET name = ?, surname = ?, email = ?, birthDate = ? WHERE email = ?");
				stmt.setString(1, newUser.getName());				
				stmt.setString(2,  newUser.getSurname());
				stmt.setString(3, newUser.getEmail());
				stmt.setString(4,  newUser.getBirthDateString());
				stmt.setString(5, currentEmail);
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
	
	public synchronized boolean doUpdatePassword(String newPassword, String currentEmail){
		Connection conn = null;
		PreparedStatement stmt = null;
			try {
				//Getting a connection from pool
				conn = ConnectionPoolManager.getConnection();
				//Preparing the query string for insert
				stmt = conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
				stmt.setString(1, newPassword);				
				stmt.setString(2,  currentEmail);;
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
