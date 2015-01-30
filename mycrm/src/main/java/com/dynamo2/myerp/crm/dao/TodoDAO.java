package com.dynamo2.myerp.crm.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.Todo;
import com.dynamo2.myerp.crm.dao.entities.resultmap.CustomerTodo;

@Repository("todoDAO")
public class TodoDAO extends AbstractBaseDAO<Todo> {
	@SuppressWarnings("unchecked")
	public List<Todo> listAllByForId(Long forId, String forType) {
		Query query = em.createQuery("from Todo t where t.forId=? and t.forType=? order by t.lastModified desc");
		query.setParameter(1, forId);
		query.setParameter(2, forType);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CustomerTodo> queryMyCustomerTodo(String account) {
		Query query = em.createNamedQuery("queryCustomerTodoByAccount");
		query.setParameter(1, account);
		query.setParameter(2, account);
		query.setParameter(3, account);
		query.setParameter(4, account);

		return query.getResultList();
	}

	public List<TodoCount> countAllFinishedTodoPerMonth() {
		Query query = em.createNamedQuery("countAllFinishedTodoPerMonth");
		List<Object[]> rst = query.getResultList();
		if (rst == null || rst.isEmpty()) {
			return Collections.emptyList();
		}

		List<TodoCount> counts = new ArrayList<TodoCount>(rst.size());
		for (Object[] obj : rst) {
			TodoCount c = new TodoCount();
			c.setYear((Integer) obj[0]);
			c.setMonth((Integer) obj[1]);
			c.setCount((Long) obj[2]);
			counts.add(c);
		}

		return counts;
	}
}
