package com.dynamo2.myerp.dynamicform.ui.controller;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.ui.controller.AbstractManagementBean;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefault;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.dao.entities.FormWorkflow;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormMDStatisticsEntry;
import com.dynamo2.myerp.dynamicform.service.FormMetadataStatus_ENUM;
import com.dynamo2.util.JSFUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 2/19/13
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "orderListBean")
@ViewScoped
@ViewRetained
@WindowDisposed
public class OrderListBean extends OrderBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long formMetadataId;

    private List<FormListEntry> formDataList;

    private Map<String,Object> criterias;

    private String searchStatus;

    private String neStatus;

    public List<FormListEntry> getFormDataList() {
        if (formDataList == null) {
            this.loadFormDatas();
        }

        return formDataList;
    }

    public Long getFormMetadataId() {
        return formMetadataId;
    }

    public void setFormMetadataId(Long formMetadataId) {
        this.formMetadataId = formMetadataId;
    }

    public String getNeStatus() {
        return neStatus;
    }

    public void setNeStatus(String neStatus) {
        this.neStatus = neStatus;
    }

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    public Map<String, Object> getCriterias() {
        if(criterias == null){
            initCriterias();
        }
        return criterias;
    }

    public void changeFormDatas(){
        this.criterias = null;
        this.loadFormDatas();
    }

    public void loadFormDatas(){
        if (formMetadataId == null || formMetadataId == 0) {
            formMetadataId = null;
            formDataList = null;
            return;
        }

        Map<String,String> params = generateSearchParamMap();
        Map<String,List<String>> neParams = generateNESearchParamMap();

        formDataList = formMetadataService.searchByCriteria(params,neParams, formMetadataId);
        replaceStatusToLabel(formDataList,this.formMetadataId);
    }

    public boolean getCreatable(){
        if(this.formMetadataId == null || this.formMetadataId == 0){
            return false;
        }

        return this.isCreatable(this.getFormMetadata(this.formMetadataId));
    }

    public List<SelectItem> getStatusList(){
        List<FormWorkflow> flows = this.getFormWorkflowSteps(this.formMetadataId);

        List<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem("NEW",JSFUtils.getI18NMessage("NEW")));

        if(!CollectionUtils.isEmpty(flows)){
            for(FormWorkflow flow : flows){
                items.add(new SelectItem(flow.getStatusName(),flow.getStatusLabel()));
            }
        }

        items.add(new SelectItem("END",JSFUtils.getI18NMessage("END")));

        return items;
    }

    private Map<String,String> generateSearchParamMap(){
        Map<String,String> params = new HashMap<String, String>(this.getCriterias().size());
        for(Map.Entry<String,Object> ety : this.criterias.entrySet()){
            if(ety.getKey().startsWith("ne_") || ety.getValue() == null
                    || StringUtils.isEmpty(ety.getValue().toString().trim())){
                continue;
            }

            params.put(ety.getKey(),ety.getValue().toString().trim());
        }

        return params;
    }

    private Map<String,List<String>> generateNESearchParamMap(){
        Map<String,List<String>> neParams = new HashMap<String, List<String>>(this.getCriterias().size());
        for(Map.Entry<String,Object> ety : this.criterias.entrySet()){
            if(!ety.getKey().startsWith("ne_") || ety.getValue() == null
                    || StringUtils.isEmpty(ety.getValue().toString().trim())){
                continue;
            }

            String value = ety.getValue().toString().trim();
            neParams.put(ety.getKey().substring(3),Arrays.asList(value.split(",")));
        }

        return neParams;
    }

    private void initCriterias(){
        criterias = new HashMap<String, Object>();
        criterias.put("summary","");
        criterias.put("status","");

        if(!StringUtils.isEmpty(this.getSearchStatus())){
            criterias.put("status",this.getSearchStatus());
        }

        if(!StringUtils.isEmpty(this.getNeStatus())){
            criterias.put("ne_status",this.getNeStatus());
        }

        this.searchStatus = null;
        this.neStatus = null;
    }
}
