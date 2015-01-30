package com.dynamo2.myerp.crm.dao;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.NavCategory;
import com.dynamo2.myerp.crm.dao.entities.NavForm;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 3/28/13
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository("navFormDAO")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NavFormDAO extends AbstractBaseDAO<NavForm> {
}
