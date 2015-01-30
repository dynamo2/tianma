package com.dynamo2.myerp.crm.service;

import java.util.List;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.crm.dao.TodoCount;
import com.dynamo2.myerp.crm.dao.entities.Todo;
import com.dynamo2.myerp.crm.dao.entities.resultmap.CustomerTodo;

public interface TodoService extends AbstractBaseServiceInterface<Todo> {

	List<Todo> listAll(Long forId, String forType);

	List<CustomerTodo> queryMyCustomerTodo();

	List<TodoCount> countAllFinishedTodoPerMonth();

	void retrieveNotes(List<Todo> todosList);
}
