package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorizedOwnerBeanDAO {
	public AuthorizedOwnerBeanDAO() {
		log = Logger.getLogger("model");
		log.setLevel(Level.INFO);
	}
	
	public synchronized boolean doSave(AuthorizedOwnerBean newOwner) {
		Connection conn = null;
		PreparedStatement stmt = null;
			log.info("if got true");
			try {
				conn = ConnectionPoolManager.getConnection();
				stmt = conn.prepareStatement("INSERT INTO AuthorizedOwner VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE administrator = ?, authorizationDate = ?, reject = ? ");
				stmt.setString(1, newOwner.getOwner());
				stmt.setString(2, newOwner.getAdministrator());
				stmt.setString(3, newOwner.getAuthorizationDateString());
				stmt.setString(4, newOwner.getpIVA());
				log.info(newOwner.isRejected() + "><");
				stmt.setBoolean(5, newOwner.isRejected());
				stmt.setString(6, newOwner.getAdministrator());
				stmt.setString(7, newOwner.getAuthorizationDateString());	
				stmt.setBoolean(8, newOwner.isRejected());

				stmt.execute();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				ConnectionPoolManager.releaseConnection(conn);
				log.info("connection released");
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
	
	public synchronized AuthorizedOwnerBean doRetrieveByKey(String email) {
		AuthorizedOwnerBean owner = null;
		PreparedStatement stmt = null; 
		Connection conn = null;
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM AuthorizedOwner WHERE owner = ?");
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				owner = new AuthorizedOwnerBean(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getBoolean(5)
						);			
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionPoolManager.releaseConnection(conn);
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return owner; 
	}
	
	public synchronized AuthorizedOwnerBean doRetrieveBypIVA(String pIVA) {
		AuthorizedOwnerBean owner = null;
		PreparedStatement stmt = null; 
		Connection conn = null;
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM AuthorizedOwner WHERE pIVA = ?");
			stmt.setString(1, pIVA);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				owner = new AuthorizedOwnerBean(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getBoolean(5)
						);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionPoolManager.releaseConnection(conn);
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		log.info("by piva: "+pIVA + " retrieved: "+owner);
		return owner; 
	}
	
	public synchronized ArrayList<AuthorizedOwnerBean> doRetrieveToBeAuthorized() {
		ArrayList<AuthorizedOwnerBean> notAuthorized = new ArrayList<AuthorizedOwnerBean>();
		PreparedStatement stmt = null; 
		Connection conn = null;
		try {
			conn = ConnectionPoolManager.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM authorizedowner WHERE administrator is NULL");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				notAuthorized.add( new AuthorizedOwnerBean(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getBoolean(5)
						));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionPoolManager.releaseConnection(conn);
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return notAuthorized; 
	}
	
	
	Logger log;
	
}
