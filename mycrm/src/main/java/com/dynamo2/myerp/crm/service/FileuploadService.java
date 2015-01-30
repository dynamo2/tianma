package com.dynamo2.myerp.crm.service;

import java.io.File;
import java.util.List;

import com.dynamo2.myerp.crm.dao.entities.Account;
import org.springframework.transaction.annotation.Transactional;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.crm.dao.entities.FileUpload;
import com.dynamo2.myerp.service.ServiceException;

public interface FileuploadService extends AbstractBaseServiceInterface<FileUpload> {
	@Transactional
	void saveFile(File file, FileUpload fileupload) throws ServiceException;

    public List<FileUpload> findFilesByCustomer(Account account, Long customerId);
}
