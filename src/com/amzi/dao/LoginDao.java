package com.amzi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
	
	private static DatabaseDao db;
	private static Connection conn = null;
	/**
	 * Validates username and password and returns the user's ID
	 * @param name
	 * @param pass
	 * @return int userId
	 */
    public static int validate(String name, String pass) {        
        boolean status = false;
        conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int userId = 0;

        try {
            db = new DatabaseDao();
            conn = db.getConnection();
            
            // Check password with SHA()
            pst = conn.prepareStatement("select * from Account where Username=? and Password=SHA(?)");
            pst.setString(1, name);
            pst.setString(2, pass);

            rs = pst.executeQuery();
            status = rs.next();
            System.out.println("Query returned: " + status);
            
            if (status) {
            	userId = rs.getInt("idAccount");
            }

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            db.closeConnection();
            
        }
        return userId;
    }
    
    public static boolean signUp(String username, String email, String password){
    	conn = null;
    	PreparedStatement pst = null;
    	int rowCount = 0;
    	
    	try {
    		db = new DatabaseDao();
            conn = db.getConnection();
            
            // This checks if the client is attempting to sign up with a
 			// username or email already in use
 			PreparedStatement usrCheck = conn.prepareStatement("SELECT idAccount FROM Account WHERE Username=? OR Email=?");
 			usrCheck.setString(1, username);
 			usrCheck.setString(2, email);

 			if (!usrCheck.executeQuery().next()) // If insert is skipped, the
 													// row count is 0 and
 													// function returns false
 			{
 				pst = conn.prepareStatement("INSERT INTO Account (Username, AccountType, Email, Password) VALUES (?, 0, ?, SHA(?))"); 	// SHA() returns a string hash, mathematically one-way. There is no way
 												// to know what the password is, because it is not stored in the SQL
 												// server in plain text
 				pst.setString(1, username);
 				pst.setString(2, email);
 				pst.setString(3, password);
 				
 				rowCount = pst.executeUpdate(); // Catch how many rows were changed from the INSERT statement (Should be 1)
 			}
 			
    	} catch (Exception e) {
    		System.err.println(e);
    	} finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            db.closeConnection();
            
        }
    	return (rowCount > 0);
    }
    
    public static int emailValidation(String email){
    	int user_id = 0;
    	Connection conn = null;  
        PreparedStatement pst = null;  
        ResultSet rs = null;  

    	
        try {  
        	db = new DatabaseDao();
            conn = db.getConnection(); 
  
            pst = conn  
                    .prepareStatement("select * from users where email=?");  
            pst.setString(1, email);  
 
            rs = pst.executeQuery();  
            if(rs.next()){
           	 user_id = rs.getInt("id");
            }  
  
        } catch (Exception e) {  
            System.out.println(e);  
        } finally {  
            if (pst != null) {  
                try {  
                    pst.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (rs != null) {  
                try {  
                    rs.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            } 
            db.closeConnection();
        }  
  
    	return user_id;
    }
    
    public static int getUserLevel(int id){
    	boolean status = false;
    	int level = 0;
        conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            db = new DatabaseDao();
            conn = db.getConnection();
            
            // Check password with SHA()
            pst = conn.prepareStatement("SELECT AccountType FROM Account where idAccount=?");
            pst.setInt(1, id);

            rs = pst.executeQuery();
            status = rs.next();
            System.out.println("Query returned: " + status);
            
            if (status) {
            	level = rs.getInt("AccountType");
            }

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            db.closeConnection();   
        }
        return level;
    }
}
