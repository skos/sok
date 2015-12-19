package pl.gda.pg.ds.sok.services.impl;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import pl.gda.pg.ds.sok.services.AboutService;
import pl.gda.pg.ds.sok.utils.PropertiesUtil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

@Path("/about")
public class AboutServiceImpl implements AboutService {

	private static final Logger logger = Logger.getLogger(AboutServiceImpl.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAbout() {
		Map<String,String> about = Maps.newHashMap();
		about.put("build", PropertiesUtil.getProperty("app.build"));
		about.put("version", PropertiesUtil.getProperty("app.version"));

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", "10.0.0.69");
		properties.put("mail.debug", "true");
		Session session = Session.getDefaultInstance(properties);

		logger.error(properties.getProperty("mail.smtp.host"));
		logger.error(properties.getProperty("mail.smtp.port"));

		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress(PropertiesUtil.getProperty("mail.addressFrom"), PropertiesUtil.getProperty("mail.from")));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("alan@malpiszon.net", "Alan"));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			msg.setSubject("a");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		try {
			msg.setText("b", "UTF-8");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		try {
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return Response.ok(about).build();
	}
}
