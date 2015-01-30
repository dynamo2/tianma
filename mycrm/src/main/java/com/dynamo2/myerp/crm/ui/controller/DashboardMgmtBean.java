/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.dynamo2.myerp.crm.ui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.dynamo2.myerp.crm.dao.TodoCount;
import com.dynamo2.myerp.crm.dao.entities.resultmap.CustomerTodo;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormMDStatisticsEntry;
import com.dynamo2.myerp.dynamicform.ui.controller.OrderBean;
import org.apache.commons.collections.CollectionUtils;

/**
 * A typical simple backing bean, that is backed to
 * <code>helloWorld.xhtml</code>
 */
@ManagedBean(name = "dashboardMgmtBean")
@ViewScoped
public class DashboardMgmtBean extends OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CustomerTodo> toMeNotStartTodoList = new LinkedList<CustomerTodo>();

	private List<CustomerTodo> toMeNotFinishedTodoList = new LinkedList<CustomerTodo>();

	private List<CustomerTodo> toMeFinishedTodoList = new LinkedList<CustomerTodo>();

	private List<CustomerTodo> createdByMeTodoList = new LinkedList<CustomerTodo>();

	private List<FormListEntry> myOperationHistory = new LinkedList<FormListEntry>();

	private List<TodoCount> allFinishedTodoCounts;

    private List<OrderBlock> orderBlocks;

	@PostConstruct
	public void init() {
		List<CustomerTodo> myCustomerTodoList = todoService.queryMyCustomerTodo();
		for (CustomerTodo customerTodo : myCustomerTodoList) {
			if (customerTodo.getAssigneeAccount().equals(getCurrentAccount().getAccount())) {
				if (customerTodo.getActualStart() == null) {
					toMeNotStartTodoList.add(customerTodo);
				} else if (customerTodo.getActualEnd() == null) {
					toMeNotFinishedTodoList.add(customerTodo);
				} else {
					toMeFinishedTodoList.add(customerTodo);
				}
			} else if (customerTodo.getCreatedBy().equals(getCurrentAccount().getAccount())) {
				createdByMeTodoList.add(customerTodo);
			}
		}

		myOperationHistory = formMetadataService.listFormDefaultOperatedByCurrentUser();

        this.initOrderBlocks();
		// TODO Fan comment it for now, we can support it later.
		// if (getAppSessionParams().isRoleSalesDirector()) {
		// allFinishedTodoCounts = todoService.countAllFinishedTodoPerMonth();
		// }
	}

    public List<OrderBlock> getOrderBlocks() {
        return this.orderBlocks;
    }

	public List<FormListEntry> getMyOperationHistory() {
		return myOperationHistory;
	}

	public List<CustomerTodo> getToMeNotStartTodoList() {
		return toMeNotStartTodoList;
	}

	public List<CustomerTodo> getToMeNotFinishedTodoList() {
		return toMeNotFinishedTodoList;
	}

	public List<CustomerTodo> getToMeFinishedTodoList() {
		return toMeFinishedTodoList;
	}

	public List<CustomerTodo> getCreatedByMeTodoList() {
		return createdByMeTodoList;
	}

    private void initOrderBlocks(){
        this.orderBlocks = new ArrayList<OrderBlock>();
        for(FormMetadata fmd : this.getReadableFormMetadataList()){
            this.orderBlocks.add(new OrderBlock(fmd,this.getStatisticsEntry(fmd.getId())));
        }
    }

    private FormMDStatisticsEntry getStatisticsEntry(Long fid){
        if(!CollectionUtils.isEmpty(this.getPublishedFormMDStatisticsEntries())){
            for(FormMDStatisticsEntry fmd : this.getPublishedFormMDStatisticsEntries()){
                if(fmd.getFormMetadataId() == fid){
                    return fmd;
                }
            }
        }

        return null;
    }

    public class OrderBlock {
        private FormMetadata formMetadata;
        private List<FormListEntry> formDatas;
        private FormMDStatisticsEntry formMDStatisticsEntry;

        public OrderBlock() {
        }

        public OrderBlock(FormMetadata formMetadata,FormMDStatisticsEntry entry) {
            this.formMetadata = formMetadata;
            this.formMDStatisticsEntry = entry;
        }

        public List<FormListEntry> getFormDatas() {
            return formDatas;
        }

        public FormMetadata getFormMetadata() {
            return formMetadata;
        }

        public FormMDStatisticsEntry getFormMDStatisticsEntry() {
            return formMDStatisticsEntry;
        }

        public boolean isCreatable() {
            return this.formMetadata.isCreatable(appSessionParams.getCurrentAccount().getAccount());
        }

        public boolean isReadable() {
            return this.formMetadata.isReadable(appSessionParams.getCurrentAccount().getAccount());
        }

        public void refresh(){
            this.loadFormDatas();
        }

        private void loadFormDatas() {
            if (this.formMetadata != null) {
                List<FormListEntry> list = formMetadataService.listFormDefault(this.formMetadata.getId());
                if(!CollectionUtils.isEmpty(list) && list.size() > 5){
                    list = list.subList(0,5);
                }

                replaceStatusToLabel(list,this.formMetadata.getId());

                this.formDatas = list;
            }
        }
    }
}
