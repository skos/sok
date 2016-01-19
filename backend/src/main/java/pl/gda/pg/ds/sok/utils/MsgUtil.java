package pl.gda.pg.ds.sok.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MsgUtil {
	
	public static final String TOKEN_PLACEHOLDER = "TOKEN_PLACEHOLDER";
	public static final String HOST_PLACEHOLDER = "HOST_PLACEHOLDER";
	public static final String ANSWER_PLACEHOLDER = "ANSWER_PLACEHOLDER";
	public static final String ANSWER_CANDIDATE = "ANSWER_CANDIDATE";
	
	private static final Logger logger = Logger.getLogger(MsgUtil.class);

	public static boolean sendMail(String mailTo, String recipientName, String subject, String body) {

		try {
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.host", PropertiesUtil.getProperty("mail.server"));
			Session session = Session.getInstance(properties);
			
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(PropertiesUtil.getProperty("mail.addressFrom"), PropertiesUtil.getProperty("mail.from")));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo, recipientName));
			msg.setSubject(subject);
			msg.setText(body, "UTF-8");
			Transport.send(msg);

			return true;
		} catch (AddressException e) {
			logger.fatal(e);
		} catch (MessagingException e) {
			logger.fatal(e);
		} catch (UnsupportedEncodingException e) {
			logger.fatal(e);
		} catch (NullPointerException e) {
			logger.fatal(e);
		}
		
		return false;
	}
}
