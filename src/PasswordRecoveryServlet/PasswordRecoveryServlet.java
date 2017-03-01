package PasswordRecoveryServlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amzi.dao.DatabaseDao;
import com.amzi.dao.LoginDao;
import com.amzi.servlets.RegisterServlet;

import SendEmail.Main;


public class PasswordRecoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DatabaseDao db;
//    String url = "jdbc:mysql://localhost:3306/";  
//    String dbName = "mealreview";  
//    String driver = "com.mysql.jdbc.Driver";  
//    String userName = "root";  
//    String password = "password";   
    int user_id=0;
    String user_Email ="";

	public PasswordRecoveryServlet() {
		System.out.println("PassWordRecoveryServlet started");
	}

	// doGET method
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		user_Email = email;
		
		String newPassWord = request.getParameter("newPassword");
		String confirmedPassword = request.getParameter("confirmedPassword");
		
		if(!(newPassWord.equals(confirmedPassword))){
			out.print("<br/><br/><br/><div class=\"alert alert-danger\"><strong>The password doesn't match please try again!!!</strong></div>");
			String url = "recovery.jsp?email=";
			url += user_Email ;
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);
		}else{
			user_id = LoginDao.emailValidation(email);
			
			if(updatePassword(newPassWord, user_id)){
				session.setAttribute("resetPassword", "valid");
				RequestDispatcher rd=request.getRequestDispatcher("login.jsp");    
	            rd.forward(request,response);
			}else{
				out.print("<br/><br/><br/><div class=\"alert alert-danger\"><strong>Password is FAILED TO UPDATE</strong></div>");
				RequestDispatcher rd=request.getRequestDispatcher("passwordRecovery.jsp");    
	            rd.forward(request,response);
			}
			
		}
	}

	// doPOST method
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
			try {
				sendEmail();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		//If email is already validated 
		if (session.getAttribute("emailValidation") == null) {
			String email = request.getParameter("inputEmail");
			user_id = LoginDao.emailValidation(email);
			if (user_id != 0 ) {
				session.setAttribute("emailValidation", "valid");
				RequestDispatcher rd = request.getRequestDispatcher("recovery.jsp");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("passwordRecovery.jsp");
				rd.forward(request, response);
			}
		}else{
			String newPassWord = request.getParameter("newPassword");
			String confirmedPassword = request.getParameter("confirmedPassword");
			boolean result = newPassWord.equals(confirmedPassword);
			
			//If confirmed password is not equal to password return immediately
			if(!(newPassWord.equals(confirmedPassword))){
				out.print("<br/><br/><br/><div class=\"alert alert-danger\"><strong>The password doesn't match please try again!!!</strong></div>");
				return;
			}
			
			if(updatePassword(newPassWord, user_id)){
				session.setAttribute("passwordUpdated", "valid");
				RequestDispatcher rd=request.getRequestDispatcher("login.jsp");    
	            rd.forward(request,response);
			}else{
				out.print("<br/><br/><br/><div class=\"alert alert-danger\"><strong>Password is FAILED TO UPDATE</strong></div>");
				RequestDispatcher rd=request.getRequestDispatcher("passwordRecovery.jsp");    
	            rd.forward(request,response);
			}
		}*/
	}
	
	protected boolean updatePassword(String newPassword, int user_id){
		 boolean status = false;
		 Connection conn = null;  
         PreparedStatement pst = null;  
         
         int resultValue;
         
         try {  
        	 db = new DatabaseDao();
             conn = db.getConnection(); 
             
             
             pst = conn  
                     .prepareStatement("UPDATE users SET users.password = SHA(?) WHERE users.id = ? ");  
             
             pst.setString(1, newPassword);  
             pst.setInt(2, user_id);
             resultValue = pst.executeUpdate();
             
             if(resultValue > 0){
            	 status = true;
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
             db.closeConnection();
         }  
		return status;
	}
	

	public static void sendEmail() throws Exception{
		//SendEmailServlet sendEmail = new SendEmailServlet("haider.hotmail@gmail.com");
		Main m = new Main("haider.hotmail@gmail.com");
		
	}

}