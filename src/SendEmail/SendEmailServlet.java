package SendEmail;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class SendEmailServlet {

//	String d_email = "emailfortesting2016@gmail.com", d_password = "abcdeabcde", d_host = "smtp.gmail.com", d_port = "465",
//			m_to = "haider.hotmail@gmail.com", m_subject = "Testing", m_text = "Hey, this is the testing email.";


	String f_email = "emailfortesting2016@gmail.com";
	String f_email_password = "abcdeabcde";
	String d_host = "smtp.gmail.com";
	String d_port = "465"; 
	
	String message_subject ="Reset Password";
	String message_body = "Hello, this is an Administrator of Meal Review. Please click the following link to reset your password<br/>";
	String url = "http://localhost:3306/CST8218-Lab-1/recovery.jsp?email=";
	String to_email = "";
	
	
	public SendEmailServlet(String email ) {
		url += email;
		
		message_body = message_body + "<a href="+url+"> Click here to recover password</a>";
		
		Properties props = new Properties();
		props.put("mail.smtp.user", f_email);
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", d_port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		SecurityManager security = System.getSecurityManager();

		try {
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);

			MimeMessage msg = new MimeMessage(session);
			msg.setText(message_body, "text/html");
			msg.setSubject(message_subject);
			msg.setFrom(new InternetAddress(f_email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
			Transport.send(msg);
		} catch (Exception mex) {
			mex.printStackTrace();
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(f_email, f_email_password);
		}
	}
}

