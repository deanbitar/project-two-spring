package com.chord.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * This class is used to send users am email containing their temporary password.
 * There is a single static method in this class.
 * 
 * @author Ezzdean Bietar
 *
 */
public class Email {

	/**
	 * This method will send an email to the address containing
	 * a message with the temporary password.
	 * 
	 * @param email - The Users's email.
	 * @param tempPass - The User's temporary password
	 */
	public static void sendTempPass(String email, String tempPass) {

		// Recipient's email ID needs to be mentioned.

		// Sender's email ID needs to be mentioned
		String from = "ReimbursementSpam3@gmail.com";
		String password = "p4ssw0rds";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			// Set Subject: header field
			message.setSubject("Temporary Password for Chordination Account");

			// Now set the actual message
			message.setText("Here is your temporary passsword:\n" + tempPass);

			// Send message
			Transport.send(message);
			System.out.println("Sent reset email successfully!");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
