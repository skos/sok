package pl.gda.pg.ds.sok.services.impl;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import pl.gda.pg.ds.sok.services.AboutService;
import pl.gda.pg.ds.sok.utils.PropertiesUtil;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
		properties.setProperty("mail.smtp.host", PropertiesUtil.getProperty("mail.server"));
		properties.put("mail.debug", "true");
		Session session = Session.getDefaultInstance(properties);

		logger.error(properties.getProperty("mail.smtp.host"));

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(PropertiesUtil.getProperty("mail.addressFrom"), PropertiesUtil.getProperty("mail.from")));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo, recipientName));
		msg.setSubject(subject);
		msg.setText(body, "UTF-8");
		Transport.send(msg);


		return Response.ok(about).build();
	}
}
