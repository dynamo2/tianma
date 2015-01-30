package com.dynamo2.myerp.dynamicform.service;

import java.util.Date;

import com.dynamo2.myerp.crm.dao.AccountDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/test-application-config.xml"})
public class MailSenderBeanTest {
	@Autowired
	private MailSenderBBean mailSender;

    @Autowired
    private AccountDAO accountDAO;
	
//	@Test
//	public void sendEmail() throws Exception {
//		try {
//			mailSender.sendEmail("wmx2003@gmail.com", "henshengcontent", "henshengtitle");
//			System.out.println("send ok");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//
//		Assert.assertNotNull(mailSender);
//	}



}
