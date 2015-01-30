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
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.dynamo2.myerp.crm.dao.TodoCount;
import com.dynamo2.myerp.crm.dao.entities.resultmap.CustomerTodo;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;

/**
 * A typical simple backing bean, that is backed to
 * <code>helloWorld.xhtml</code>
 */
@ManagedBean(name = "helpContentMgmtBean")
@SessionScoped
public class HelpContentMgmtBean extends AbstractManagementBean implements Serializable {

	private static final long serialVersionUID = 1L;

}
