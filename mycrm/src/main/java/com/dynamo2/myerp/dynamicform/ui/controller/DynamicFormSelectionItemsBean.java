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
package com.dynamo2.myerp.dynamicform.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import com.dynamo2.myerp.dynamicform.service.FormFieldTypes_ENUM;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService.FORM_CATEGORY_ENUM;
import com.dynamo2.myerp.dynamicform.service.FormMetadataStatus_ENUM;
import com.dynamo2.myerp.dynamicform.service.FormRecordStatus_ENUM;
import com.dynamo2.util.JSFUtils;

/**
 * A typical simple backing bean, that is backed to
 * <code>helloWorld.xhtml</code>
 */
@ManagedBean(name = "dynamicFormSelectionItems")
@ApplicationScoped
public class DynamicFormSelectionItemsBean {

	private List<SelectItem> fieldType;

	// TODO Change to form metadata status
	private List<SelectItem> formStatus;

	private List<SelectItem> formRecordStatus;

	private List<SelectItem> trueOrFalse;

	private List<SelectItem> formCategories;

	private List<SelectItem> operations;

	@PostConstruct
	protected void init() {
		fieldType = new ArrayList<SelectItem>();
		for (FormFieldTypes_ENUM r : FormFieldTypes_ENUM.values()) {
			fieldType.add(new SelectItem(r.getSqlType(), JSFUtils.getI18NMessage(r.getToken())));
		}

		formStatus = new ArrayList<SelectItem>();
		for (FormMetadataStatus_ENUM r : FormMetadataStatus_ENUM.values()) {
			formStatus.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}

		formRecordStatus = new ArrayList<SelectItem>();
		for (FormRecordStatus_ENUM r : FormRecordStatus_ENUM.values()) {
			formRecordStatus.add(new SelectItem(r.name(), JSFUtils.getI18NMessage(r.name())));
		}

		formCategories = new ArrayList<SelectItem>();
		for (FORM_CATEGORY_ENUM r : FORM_CATEGORY_ENUM.values()) {
			formCategories.add(new SelectItem(r.name().toLowerCase(), JSFUtils.getI18NMessage(r.name())));
		}

		trueOrFalse = new ArrayList<SelectItem>();
		trueOrFalse.add(new SelectItem(Boolean.FALSE, JSFUtils.getI18NMessage("false")));
		trueOrFalse.add(new SelectItem(Boolean.TRUE, JSFUtils.getI18NMessage("true")));
	}

	public List<SelectItem> getFieldType() {
		return fieldType;
	}

	public List<SelectItem> getFormStatus() {
		return formStatus;
	}

	public List<SelectItem> getFormRecordStatus() {
		return formRecordStatus;
	}

	public List<SelectItem> getTrueOrFalse() {
		return trueOrFalse;
	}

	public List<SelectItem> getFormCategories() {
		return formCategories;
	}
}
