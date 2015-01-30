package com.dynamo2.myerp.dynamicform.service;

import com.dynamo2.myerp.dynamicform.dao.FormMetadataDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 3/2/13
 * Time: 6:43 PM
 * To change this template use File | Settings | File Templates.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/test-application-config.xml"})
public class ServiceTest {
    @Autowired
    private FormMetadataService formMetadataService;

    @Test
    public void testSearch(){
    }
}
