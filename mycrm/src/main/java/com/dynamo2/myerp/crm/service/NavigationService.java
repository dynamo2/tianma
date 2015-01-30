package com.dynamo2.myerp.crm.service;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.crm.dao.entities.NavCategory;
import com.dynamo2.myerp.crm.dao.entities.NavForm;
import com.dynamo2.myerp.service.ServiceException;

import java.util.List;

public interface NavigationService extends AbstractBaseServiceInterface<NavCategory> {

    public List<NavCategory> findCategories();

    public List<NavForm> findForms(String accountString);

    public void saveOrUpdate(NavForm entity) throws ServiceException;

    public List<NavForm> findSystemForms();

    public boolean deleteNavCategory(Long id);
}
