package com.dynamo2.myerp.dynamicform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.dynamo2.myerp.dynamicform.dao.entities.EmailBox;

@ManagedBean(name = "mailSenderBBean")
@ViewScoped
public class MailSenderBBean extends TimerTask {

	private static final String SYSTEM = "system";
	
	public static final String SYSTEM_EMAIL = "system@hsarz.com";

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailBoxService mailBoxService;
	
	@Autowired
	private VelocityEngine velocityEngine;

	private static final Log logger = LogFactory.getLog(MailSenderBBean.class);

	private void pollingEmail() {

		List<EmailBox> emails = mailBoxService.listAll();

		if (logger.isDebugEnabled()) {
			logger.debug(emails.size() + " mails waiting to send ....");
		}

		if (!emails.isEmpty()) {
			List<EmailBox> cleanupEmails = new ArrayList<EmailBox>(emails.size());
			for (EmailBox emailBox : emails) {
				try {
					sendEmail(emailBox.getMailTo(), emailBox.getMessages(), emailBox.getTitle());
					if (logger.isDebugEnabled()) {
						logger.debug("Sent email:[" + emailBox.getTitle() + "]");
					}
					
					cleanupEmails.add(emailBox);
				} catch (Exception e) {
					logger.warn("Send email to " + emailBox.getMailTo() + " failed. Title:[" + emailBox.getTitle()
							+ "]", e);
					
					emailBox.setStatus(EmailBox.STATUS.FAILED.name());
					emailBox.setErrMsg(e.getMessage());
					//mailBoxService.newOrUpdate(emailBox);
				}
			}

			cleanupEmail(cleanupEmails);
		}

	}

	private void cleanupEmail(List<EmailBox> emails) {
		for (EmailBox emailBox : emails) {
			mailBoxService.deleteById(emailBox.getId());
		}
	}

	protected void sendEmail(final String mailTo, final String messages, final String title) throws MailException,
			MessagingException {

		MimeMessageHelper message = new MimeMessageHelper(mailSender.createMimeMessage(), false, "UTF-8");
		message.setTo(mailTo);
		message.setFrom("system@hsarz.com");
		message.setSubject(title);
		message.setText(messages, true);

		mailSender.send(message.getMimeMessage());
	}

	@Override
	public void run() {
		try {
			pollingEmail();
		} catch (Exception e) {
			logger.error("Run Time task failed.", e);
		}
	}
	
	public String buildVMMessage(String tempFile,Map params){
		try {
			return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, tempFile,"UTF-8", params);
		}catch(Exception e){
			return "";
		}
	}

}
