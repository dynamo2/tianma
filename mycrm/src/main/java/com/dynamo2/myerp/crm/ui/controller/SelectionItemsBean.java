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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import com.dynamo2.myerp.crm.service.constant.CONTACT_TYPES_ENUM;
import com.dynamo2.myerp.crm.service.constant.CUSTOMER_INDUSTRIES_ENUM;
import com.dynamo2.myerp.crm.service.constant.CUSTOMER_QUALITIES_ENUM;
import com.dynamo2.myerp.crm.service.constant.CUSTOMER_TYPES_ENUM;
import com.dynamo2.myerp.crm.service.constant.HEAR_FROM_ENUM;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;
import com.dynamo2.myerp.crm.service.constant.TODO_TYPE_ENUM;
import com.dynamo2.myerp.service.PERMISSION_DEFINITION_ENUM;
import com.dynamo2.util.JSFUtils;

/**
 * A typical simple backing bean, that is backed to
 * <code>helloWorld.xhtml</code>
 */
@ManagedBean(name = "selectionItems")
@ApplicationScoped
public class SelectionItemsBean {

	private List<SelectItem> genderItems;

	private List<SelectItem> rolesItems;

	private List<SelectItem> customerTypeItems;

	private List<SelectItem> contactTypeItems;

	private List<SelectItem> customerQualityItems;

	private List<SelectItem> customerIndustryItems;

	private List<SelectItem> customerHearFromItems;

	private List<SelectItem> languageItems;

	private List<SelectItem> todoTypeItems;

	private List<SelectItem> customerOrDistributor;

	private List<SelectItem> permissionItems;

	@PostConstruct
	protected void init() {
		genderItems = Arrays.asList(new SelectItem("Male", "Male"), new SelectItem("Female", "Female"));

		rolesItems = new ArrayList<SelectItem>();
		for (Roles_ENUM r : Roles_ENUM.values()) {
			rolesItems.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}

		customerTypeItems = new ArrayList<SelectItem>();
		for (CUSTOMER_TYPES_ENUM r : CUSTOMER_TYPES_ENUM.values()) {
			customerTypeItems.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}

		contactTypeItems = new ArrayList<SelectItem>();
		for (CONTACT_TYPES_ENUM r : CONTACT_TYPES_ENUM.values()) {
			contactTypeItems.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}

		customerQualityItems = new ArrayList<SelectItem>();
		for (CUSTOMER_QUALITIES_ENUM r : CUSTOMER_QUALITIES_ENUM.values()) {
			customerQualityItems.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}

		customerIndustryItems = new ArrayList<SelectItem>();
		for (CUSTOMER_INDUSTRIES_ENUM r : CUSTOMER_INDUSTRIES_ENUM.values()) {
			customerIndustryItems.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}

		customerHearFromItems = new ArrayList<SelectItem>();
		for (HEAR_FROM_ENUM r : HEAR_FROM_ENUM.values()) {
			customerHearFromItems.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}

		todoTypeItems = new ArrayList<SelectItem>();
		for (TODO_TYPE_ENUM r : TODO_TYPE_ENUM.values()) {
			todoTypeItems.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}

		languageItems = Arrays.asList(new SelectItem("zh_CN", "中文"), new SelectItem("en", "English"));

		customerOrDistributor = Arrays.asList(new SelectItem(true, JSFUtils.getI18NMessage("distributor")),
				new SelectItem(false, JSFUtils.getI18NMessage("customer")));

		permissionItems = new ArrayList<SelectItem>();
		for (PERMISSION_DEFINITION_ENUM r : PERMISSION_DEFINITION_ENUM.values()) {
			if (!r.isAbstractPermission()) {
				permissionItems.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
			}
		}
	}

	public List<SelectItem> getGenders() {
		return genderItems;
	}

	public List<SelectItem> getRoles() {
		return rolesItems;
	}

	public List<SelectItem> getCustomerTypeItems() {
		return customerTypeItems;
	}

	public List<SelectItem> getContactTypeItems() {
		return contactTypeItems;
	}

	public List<SelectItem> getCustomerQualityItems() {
		return customerQualityItems;
	}

	public List<SelectItem> getCustomerHearFromItems() {
		return customerHearFromItems;
	}

	public List<SelectItem> getCustomerIndustryItems() {
		return customerIndustryItems;
	}

	public List<SelectItem> getLanguageItems() {
		return languageItems;
	}

	public List<SelectItem> getTodoTypeItems() {
		return todoTypeItems;
	}

	public List<SelectItem> getCustomerOrDistributor() {
		return customerOrDistributor;
	}

	public List<SelectItem> getPermissionItems() {
		return permissionItems;
	}
}
