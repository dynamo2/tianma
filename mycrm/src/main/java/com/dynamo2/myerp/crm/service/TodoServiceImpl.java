package com.dynamo2.myerp.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.NotesDAO;
import com.dynamo2.myerp.crm.dao.TodoCount;
import com.dynamo2.myerp.crm.dao.TodoDAO;
import com.dynamo2.myerp.crm.dao.entities.Notes;
import com.dynamo2.myerp.crm.dao.entities.Todo;
import com.dynamo2.myerp.crm.dao.entities.resultmap.CustomerTodo;

@Service("todoService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TodoServiceImpl extends AbstractBaseService<Todo> implements TodoService {

	@Autowired
	private TodoDAO todoDAO;

	@Autowired
	private NotesDAO notesDAO;

	public List<Todo> listAll(Long forId, String forType) {
		return todoDAO.listAllByForId(forId, forType);
	}

	@Override
	protected AbstractBaseDAO<Todo> getDao() {
		return todoDAO;
	}

	public List<CustomerTodo> queryMyCustomerTodo() {
		return todoDAO.queryMyCustomerTodo(getCurrentAccount().getAccount());
	}

	//@Override
	public void retrieveNotes(List<Todo> todosList) {
		for (Todo todo : todosList) {
			List<Notes> notes = notesDAO.listAllByForId(todo.getId(), Notes.FOR_TODO);
			todo.setNotes(notes);
		}
	}

	public List<TodoCount> countAllFinishedTodoPerMonth() {
		return todoDAO.countAllFinishedTodoPerMonth();
	}
}
