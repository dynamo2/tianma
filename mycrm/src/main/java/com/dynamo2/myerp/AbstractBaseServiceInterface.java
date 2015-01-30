package com.dynamo2.myerp;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dynamo2.myerp.service.ServiceException;

public interface AbstractBaseServiceInterface<T> {

	@Transactional
	void newOrUpdate(T entity) throws ServiceException;
	
	@Transactional
	void newOrUpdateBySystem(T entity);

	T loadById(Long id);

	List<T> findByField(String fieldName, Object value);
	
	List<T> findByFields(String[] fieldNames, Object[] values);
	
	@Transactional
	void deleteById(Long id);

	public List<T> listAll();
}
