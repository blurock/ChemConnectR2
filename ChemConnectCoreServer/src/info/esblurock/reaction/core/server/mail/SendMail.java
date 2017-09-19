package info.esblurock.reaction.core.server.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	private static final Logger log = Logger.getLogger(SendMail.class.getName());

	public SendMail() {
	}

	public boolean sendMail(String to, String from, String subject, String html) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		boolean b = false;
		try {
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(from, from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to, to));
			msg.setSubject(subject);
			msg.setContent(html, "text/html");
			Transport.send(msg);
			b = true;
		} catch (AddressException e) {
			log.log(Level.SEVERE, "", e);
		} catch (MessagingException e) {
			log.log(Level.SEVERE, "", e);
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE, "", e);
		}
		return b;
	}

	public boolean sendMail(String[] to, String from, String subject, String html) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		boolean b = false;
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from, from));
			for (int i = 0; i < to.length; i++) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i], to[i]));
			}
			msg.setSubject(subject);
			// msg.setText(msgBody);
			msg.setContent(html, "text/html");
			Transport.send(msg);
			b = true;
		} catch (AddressException e) {
			log.log(Level.SEVERE, "", e);
		} catch (MessagingException e) {
			log.log(Level.SEVERE, "", e);
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE, "", e);
		}
		return b;
	}

}