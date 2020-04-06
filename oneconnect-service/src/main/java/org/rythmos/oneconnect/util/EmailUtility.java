package org.rythmos.oneconnect.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailUtility {
	public static Logger logger = LoggerFactory.getLogger(EmailUtility.class);
	private String from = OneConnectConstants.MAIL_EMAIL;

	private String password = OneConnectConstants.MAIL_PASSWORD;

	private String host = OneConnectConstants.MAIL_HOST;

	private String port = OneConnectConstants.MAIL_PORT;

	private String name = OneConnectConstants.MAIL_NAME;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean sendEmail(String to, String subject, String messageBody) throws Exception {

		Properties props = new Properties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from, name));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to, to));
			msg.setSubject(subject);
			msg.setText(messageBody);
			Transport.send(msg);
			return true;
		} catch (AddressException e) {
			logger.info(e.getMessage());
			return false;
		} catch (MessagingException e) {
			logger.info(e.getMessage());
			return false;
		}
	}
}