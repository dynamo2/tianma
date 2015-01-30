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
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;

import com.dynamo2.myerp.crm.dao.entities.FileUpload;
import com.dynamo2.myerp.service.ServiceException;
import com.dynamo2.util.JSFUtils;

/**
 * A typical simple backing bean, that is backed to
 * <code>helloWorld.xhtml</code>
 */
@ManagedBean(name = "fileuploadMgmtBean")
@ViewScoped
public class FileuploadMgmtBean extends AbstractManagementBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private FileUpload fileupload;

	private String returnUrl;

	@PostConstruct
	public void init() {
		fileupload = new FileUpload();
	}

	public FileUpload getFileupload() {
		return fileupload;
	}

	public void setFileupload(FileUpload fileupload) {
		this.fileupload = fileupload;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public void fileuploadListener(FileEntryEvent event) throws ServiceException {
		FileEntry fileEntry = (FileEntry) event.getSource();
		FileEntryResults results = fileEntry.getResults();
		for (FileEntryResults.FileInfo fileInfo : results.getFiles()) {
			if (fileInfo.isSaved()) {
				fileupload.setFilename(fileInfo.getFileName());
				if (null == fileupload.getFolders()) {
					fileupload.setFolders("/");
				}
				String weburl = "/upload_archive/" + yyyyMMddHHmmss.format(new Date()) + "_" + fileInfo.getFileName();
				fileupload.setAbsolutePath(JSFUtils.getWebFolderRealPath(weburl));
				fileupload.setFileDownloadUrl(weburl);
				fileuploadService.saveFile(fileInfo.getFile(), fileupload);
			}
		}
	}

	public void saveFile() {

	}
}
