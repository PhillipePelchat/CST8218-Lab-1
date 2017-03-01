package SendResetEmailServlet;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class SendResetEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// String d_email = "emailfortesting2016@gmail.com", d_password =
	// "abcdeabcde", d_host = "smtp.gmail.com", d_port = "465",
	// m_to = "haider.hotmail@gmail.com", m_subject = "Testing", m_text = "Hey,
	// this is the testing email.";

	String message_subject = "Reset Password";
	String message_body = "Hello, this is an Administrator of Meal Review. Please click the following link to reset your password<br/>";
	String url = "http://localhost:3306/CST8218-Lab-1/recovery.jsp?email=";
	String to_email = "";

	public SendResetEmailServlet() {
		System.out.println("PassWordRecoveryServlet started");
	}

	// doGET method
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	// doPOST method
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// FIX IT URGENCY
		String email = request.getParameter("inputEmail");
		// String email = "haider.hotmail@gmail.com";
		url += email;
		to_email = email;
		message_body = message_body + "<a href=" + url + "> Click here to recover password</a>" + "<br><br> Thank you!";

		Properties props = new Properties();
		props.put("mail.smtp.user", d_email);
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
			// session.setDebug(true);

			MimeMessage msg = new MimeMessage(session);
			msg.setContent(message_body, "text/html; charset=utf-8");
			msg.setSubject(m_subject);
			msg.setFrom(new InternetAddress(d_email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
			Transport.send(msg);
			RequestDispatcher rd = request.getRequestDispatcher("confirmationMessage.jsp");
			rd.forward(request, response);

		} catch (Exception mex) {
			mex.printStackTrace();
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(d_email, d_password);
		}
	}

}
