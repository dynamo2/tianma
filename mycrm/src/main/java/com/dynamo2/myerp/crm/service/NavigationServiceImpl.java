package com.dynamo2.myerp.crm.service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.NavCategoryDAO;
import com.dynamo2.myerp.crm.dao.NavFormDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.NavCategory;
import com.dynamo2.myerp.crm.dao.entities.NavForm;
import com.dynamo2.myerp.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 3/28/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("navigationService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NavigationServiceImpl extends AbstractBaseService<NavCategory> implements NavigationService {
    @Autowired
    private NavCategoryDAO navCategoryDAO;

    @Autowired
    private NavFormDAO navFormDAO;

    @Override
    protected AbstractBaseDAO<NavCategory> getDao() {
        return this.navCategoryDAO;
    }

    public List<NavCategory> findCategories() {
        return navCategoryDAO.findByField("deleteFlag",
                NavCategory.DeleteFlag.NO.value());
    }

    public List<NavForm> findForms(String accountString) {
        return navFormDAO.findByField("account", accountString);
    }

    @Transactional
    public void saveOrUpdate(NavForm entity) throws ServiceException{
        this.navFormDAO.saveOrUpdate(entity,this.getCurrentAccountString());
    }

    public List<NavForm> findSystemForms(){
        return this.navFormDAO.findByField("type",NavForm.Type.SYSTEM.val());
    }

    @Transactional
    public boolean deleteNavCategory(Long id){
        if(id != null){
            NavCategory nc = this.loadById(id);
            if(nc != null){
                nc.setDeleteFlag(NavCategory.DeleteFlag.YES.value());
                try {
                    newOrUpdate(nc);
                    return true;
                } catch (ServiceException e) {
                }
            }
        }

        return false;
    }
}
