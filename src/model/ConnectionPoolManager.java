package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Logger;

public class ConnectionPoolManager {
	//This method should load the JDBC driver for Database manipulation 
	private static Logger log = Logger.getLogger("model");
	static {
        try {
        	availableConnections = new LinkedList<Connection>();
           // Class.forName("com.mysql.cj.jdbc.Driver");  
        	 Class.forName("com.mysql.jdbc.Driver");
            //log.info("Correctly loaded driver");
        }
        catch(ClassNotFoundException e1){
        	e1.printStackTrace();
        }
	}
	
	public static synchronized Connection createConnection() throws SQLException{
		//log.info("inner createConnection()");
		Connection newConnection = null; 
        final String URL = "jdbc:mysql://localhost:3306/CottoConsegnato?&serverTimezone=Europe/Rome";
        final String USR, PWD = USR =  "root";
        //Instantiation of a new connection
        //log.info("before creating connection");
        newConnection = DriverManager.getConnection(URL, USR, PWD);
        //log.info("after creating connection");
        newConnection.setAutoCommit(false);
        //log.info("before setting auto-commit off");
        return newConnection;
	}
	
	public static synchronized Connection getConnection() throws SQLException {
		Connection connection = null; 
		//If there is any connection not in use
		if(!availableConnections.isEmpty()) {
			connection = availableConnections.get(0);
			availableConnections.remove(0);
			try {
				//Test if the connection is open
				if(connection.isClosed()) {
					connection = getConnection();
				}
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			}
		} else {
			//There is no connection available, creating a new one
			connection = createConnection();
		}
		return connection;
	}
	
	public static synchronized void releaseConnection(Connection connection) {
		if(connection != null)
			availableConnections.add(connection);
	}
	
	private static LinkedList<Connection> availableConnections;
}
