package com.dynamo2.myerp.dynamicform.service;

import com.dynamo2.myerp.crm.dao.FileuploadDAO;
import com.dynamo2.myerp.crm.dao.NavCategoryDAO;
import com.dynamo2.myerp.crm.dao.NavFormDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.FileUpload;
import com.dynamo2.myerp.crm.dao.entities.NavCategory;
import com.dynamo2.myerp.crm.dao.entities.NavForm;
import com.dynamo2.myerp.crm.service.AccountService;
import com.dynamo2.myerp.crm.service.NavigationService;
import com.dynamo2.myerp.crm.service.NavigationServiceImpl;
import com.dynamo2.myerp.dynamicform.dao.DynamicFormDefaultDAO;
import com.dynamo2.myerp.dynamicform.dao.FormFieldMetadataDAO;
import com.dynamo2.myerp.dynamicform.dao.FormMetadataDAO;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormMDStatisticsEntry;
import com.dynamo2.myerp.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 2/5/13
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/test-application-config.xml"})
public class DAOTest {
    @Autowired
    private FormMetadataDAO formMetaDataDao;

    @Autowired
    private DynamicFormDefaultDAO dynamicFormDefaultDAO;

    @Autowired
    private FormMetadataDAO formMetadataDAO;

    @Autowired
    private FileuploadDAO fileuploadDAO;

    @Autowired
    private NavFormDAO navFormDAO;

    @Autowired
    private NavCategoryDAO navCategoryDAO;

    @Autowired
    private NavigationService navigationService;

//    @Test
//    public void testListByParentForm(){
//        List<FormMetadata> formList = this.formMetaDataDao.listByParentForm(13L);
//
//        System.out.println("ok");
//    }
//
//    @Test
//    public void testListFormsToParentForm(){
//        List<FormListEntry> formList = dynamicFormDefaultDAO.listFormsToParentForm("",7L);
//        System.out.println(formList);
//    }

//    @Test
//    public void testStatisticsByStatus(){
//        List<FormMDStatisticsEntry> l = this.formMetadataDAO.statisticsByStatus();
//        for(FormMDStatisticsEntry fe:l){
//            System.out.print("FormMetadataId = " + fe.getFormMetadataId());
//            System.out.print(", NewCount = " + fe.getNewCount());
//            System.out.print(", EndCount = " + fe.getEndCount());
//            System.out.print(", ProceeingCount = " + fe.getProceeingCount());
//            System.out.println("");
//        }
//    }

//    @Test
//    public void testFindFilesByCustomerAndAccount(){
//        Account a = new Account();
//        a.setAccount("fan");
//        List<FileUpload> l = this.fileuploadDAO.findFilesByCustomerAndAccount(a,1688L);
//
//        System.out.println("====================");
//        System.out.println(l);
//    }

//    @Test
//    public void testSaveNavForm(){
//        NavForm nf = new NavForm();
//
//        nf.setLabel("客户列表");
//        nf.setType(NavForm.Type.SYSTEM.val());
//        nf.setUrl("customer/customer_list.jsf");
//        nf.setAccount("fan");
//
//        //navFormDAO.saveOrUpdate(nf,"fan");
//
//        NavCategory nc = new NavCategory();
//        nc.setLabel("技术支持");
//
//        //navCategoryDAO.saveOrUpdate(nc,"fan");
//
//        try {
//            navigationService.saveOrUpdate(nf);
//        } catch (ServiceException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }
}
