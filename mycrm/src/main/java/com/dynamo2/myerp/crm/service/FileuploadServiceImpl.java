package com.dynamo2.myerp.crm.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.FileuploadDAO;
import com.dynamo2.myerp.crm.dao.entities.FileUpload;
import com.dynamo2.myerp.service.ServiceException;

@Service("fileuploadService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileuploadServiceImpl extends AbstractBaseService<FileUpload> implements FileuploadService {

	@Autowired
	private FileuploadDAO dao;

	@Override
	protected AbstractBaseDAO<FileUpload> getDao() {
		return dao;
	}

	//@Override
	public void saveFile(File file, FileUpload fileupload) throws ServiceException {
		File newFile = new File(fileupload.getAbsolutePath());
		
		if (newFile.exists()) {
			throw new ServiceException(file.getAbsolutePath() + " is existed.");
		}
		
		newFile.getParentFile().mkdirs();
		try {
			FileCopyUtils.copy(file, newFile);
			newOrUpdate(fileupload);
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

    //@Override
    public List<FileUpload> findFilesByCustomer(Account account,Long customerId){
        if(account != null && account.hasRole(Roles_ENUM.ROLE_SALES_DIRECTOR.toString())){
            return this.findByField("customerId", customerId);
        }

        return this.dao.findFilesByCustomerAndAccount(this.getCurrentAccount(),customerId);
    }

}
