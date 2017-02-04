package com.amzi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
	
	private static DatabaseDao db;
	private static Connection conn = null;
	
    public static boolean validate(String name, String pass) {        
        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            db = new DatabaseDao();
            conn = db.getConnection();
            
            
            pst = conn.prepareStatement("select * from Account where Username=? and Password=SHA(?)");
            pst.setString(1, name);
            pst.setString(2, pass);

            rs = pst.executeQuery();
            status = rs.next();
            System.out.println("Query returned: " + status);

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
        return status;
    }
}
