package com.dynamo2.myerp.dynamicform.ui.controller;

import com.dynamo2.myerp.crm.ui.controller.AbstractManagementBean;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormWorkflow;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormMDStatisticsEntry;
import com.dynamo2.myerp.dynamicform.service.FormMetadataStatus_ENUM;
import com.dynamo2.util.JSFUtils;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 2/22/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class OrderBean extends AbstractManagementBean implements Serializable {
    // Map of work flow steps of a form
    private Map<Long,List<FormWorkflow>> formWorkflowStepMap;

    private List<FormMetadata> publishedFormMetadataList;

    // Maintain with publishedFormMetadataList
    private List<FormMetadata> readableFormMetadataList;

    private List<FormMDStatisticsEntry> publishedFormMDStatisticsEntries;

    public List<FormMetadata> getReadableFormMetadataList() {
        if(this.readableFormMetadataList == null){
            this.initPublishedFormMetadataList();
        }

        return readableFormMetadataList;
    }

    protected List<FormMetadata> getPublishedFormMetadataList(){
        if(this.publishedFormMetadataList == null){
            this.initPublishedFormMetadataList();
        }

        return this.publishedFormMetadataList;
    }

    protected List<FormMDStatisticsEntry> getPublishedFormMDStatisticsEntries(){
        if(this.publishedFormMDStatisticsEntries == null){
            this.initPublishedFormMDStatisticsEntries();
        }

        return this.publishedFormMDStatisticsEntries;
    }

    protected List<FormWorkflow> getFormWorkflowSteps(Long fid){
        if(this.formWorkflowStepMap == null){
            this.formWorkflowStepMap = new HashMap<Long, List<FormWorkflow>>();
        }

        if(!this.formWorkflowStepMap.containsKey(fid)){
            this.formWorkflowStepMap.put(fid,formMetadataService.listWorkFlowSteps(fid));
        }

        return this.formWorkflowStepMap.get(fid);
    }

    protected void replaceStatusToLabel(List<FormListEntry> forms,Long fid) {
        List<FormWorkflow> flows = getFormWorkflowSteps(fid);

        for (FormListEntry f : forms) {
            FormWorkflow step = null;
            if(!CollectionUtils.isEmpty(flows)){
                for(FormWorkflow flow:flows){
                    if(flow.getStatusName().equals(f.getStatus())){
                        step = flow;
                        break;
                    }
                }
            }

            if (step != null) {
                f.setStatus(step.getStatusLabel());
            } else {
                f.setStatus(JSFUtils.getI18NMessage(f.getStatus()));
            }
        }
    }

    protected Long getIdFromHttpRequest(String paramName){
        try {
            return Long.parseLong(JSFUtils.getHttpRequestParam(paramName));
        }catch(Exception e){}

        return null;
    }

    protected boolean isReadable(FormMetadata fmd) {
        return fmd.isReadable(appSessionParams.getCurrentAccount().getAccount());
    }

    protected boolean isCreatable(FormMetadata fmd) {
        return fmd.isCreatable(appSessionParams.getCurrentAccount().getAccount());
    }

    protected boolean isPublished(FormMetadata fmd) {
        return FormMetadataStatus_ENUM.PUBLISHED.name().equalsIgnoreCase(fmd.getFormStatus());
    }

    protected FormMetadata getFormMetadata(Long fid){
        if(!CollectionUtils.isEmpty(this.getPublishedFormMetadataList())){
            for(FormMetadata fmd : this.getPublishedFormMetadataList()){
                if(fmd.getId() == fid){
                    return fmd;
                }
            }
        }

        return formMetadataService.loadById(fid);
    }

    private void initPublishedFormMetadataList() {
        List<FormMetadata> forms = formMetadataService.listAllFormMetadata();
        publishedFormMetadataList = new ArrayList<FormMetadata>(forms.size());
        readableFormMetadataList = new ArrayList<FormMetadata>(forms.size());
        for (FormMetadata form : forms) {
            if (isPublished(form)) {
                publishedFormMetadataList.add(form);

                if(this.isReadable(form)){
                    readableFormMetadataList.add(form);
                }
            }
        }
    }

    private void initPublishedFormMDStatisticsEntries(){
        publishedFormMDStatisticsEntries = formMetadataService.statisticsByStatus();
    }
}
