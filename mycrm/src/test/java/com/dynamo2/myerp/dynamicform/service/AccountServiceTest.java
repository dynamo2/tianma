package com.dynamo2.myerp.dynamicform.service;

import com.dynamo2.myerp.crm.dao.AccountDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.Person;
import com.dynamo2.myerp.crm.service.AccountService;
import com.dynamo2.myerp.crm.service.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.faces.bean.ManagedProperty;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 1/24/13
 * Time: 8:15 PM
 * To change this template use File | Settings | File Templates.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/test-application-config.xml"})
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

//    @Test
//    public void findByAccountAndChildren() throws Exception {
//        try {
//            Account fan = accountService.findWithChildrenByAccount("fan");
//
////            System.out.println("fan.list: " + fan.getChildren());
//            System.out.println("fan.list: " + fan.getChildren().get(0).getParent().getId());
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
}
