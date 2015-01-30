package com.dynamo2.myerp.crm.ui.controller.system;

import com.dynamo2.myerp.crm.dao.entities.NavCategory;
import com.dynamo2.myerp.crm.dao.entities.NavForm;
import com.dynamo2.myerp.crm.ui.controller.AbstractManagementBean;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.dynamo2.myerp.dynamicform.service.FormMetadataStatus_ENUM;
import com.dynamo2.myerp.service.ServiceException;
import com.dynamo2.util.JSFUtils;
import com.sun.faces.util.CollectionsUtils;
import org.apache.commons.collections.CollectionUtils;
import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 3/25/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean(name = "navMgmt")
@ViewScoped
@ViewRetained
@WindowDisposed
public class NavigationManagementBean extends AbstractManagementBean {

    private static final long serialVersionUID = 7110201613709715706L;

    private List<FormMetadata> publishedFormMetadataList;

    private List<NavCategory> navCategories;

    private Long newNavCategoryId;

    private Long navCategoryId;

    private NavCategory editNavCategory;

    private List<FormMetadata> uncategorizedFormMetadatas;

    public List<FormMetadata> getPublishedFormMetadataList() {
        if (null == publishedFormMetadataList) {
            List<FormMetadata> forms = formMetadataService
                    .listAllFormMetadata();
            publishedFormMetadataList = new ArrayList<FormMetadata>(
                    forms.size());
            for (FormMetadata form : forms) {
                if (FormMetadataStatus_ENUM.PUBLISHED.name().equalsIgnoreCase(
                        form.getFormStatus())) {
                    publishedFormMetadataList.add(form);
                }
            }
        }
        return publishedFormMetadataList;
    }

    public Long getNewNavCategoryId() {
        return newNavCategoryId;
    }

    public NavCategory getEditNavCategory() {
        if(editNavCategory == null){
            this.editNavCategory = new NavCategory();
        }

        return editNavCategory;
    }

    public void setEditNavCategory(NavCategory editNavCategory) {
        this.editNavCategory = editNavCategory;
    }

    public Long getNavCategoryId() {
        return navCategoryId;
    }

    public void setNavCategoryId(Long navCategoryId) {
        this.navCategoryId = navCategoryId;
    }

    public void loadEditNavCategory(){
        if(this.navCategoryId == null){
            this.editNavCategory = null;
        }else {
            this.editNavCategory = this.navigationService.loadById(this.navCategoryId);
        }
    }

    public boolean isEditable(){
        boolean b = this.editNavCategory != null && this.editNavCategory.getId() != null;

        return b;
    }

    public boolean isCreatable(){
        boolean b = this.editNavCategory != null && this.editNavCategory.getId() == null;

        return b;
    }

    public void deleteNavCategory(){
        navigationService.deleteNavCategory(navCategoryId);
    }

    public void saveEditNavCategory(){
        if(this.editNavCategory != null){
            try {
                this.navigationService.newOrUpdate(this.editNavCategory);
                this.newNavCategoryId = this.editNavCategory.getId();

                this.navCategories = null;
                this.editNavCategory = null;
            } catch (ServiceException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void saveAll(){
        for(NavCategory nc:navCategories){
            try {
                navigationService.newOrUpdate(nc);
            } catch (ServiceException e) {
            }
        }

        for(FormMetadata fm:publishedFormMetadataList){
            formMetadataService.updateCategory(fm);
        }
    }

    public List<FormMetadata> getUncategorizedFormMetadatas() {
        if(uncategorizedFormMetadatas == null){
            getNavCategories();
        }
        return uncategorizedFormMetadatas;
    }

    public List<NavCategory> getNavCategories() {
        if(navCategories == null){
            this.navCategories = this.navigationService.findCategories();
            uncategorizedFormMetadatas = this.getPublishedFormMetadataList();

            if(!CollectionUtils.isEmpty(this.navCategories)){
                for(NavCategory nc : this.navCategories){
                    nc.addMatched(uncategorizedFormMetadatas);

                    uncategorizedFormMetadatas.removeAll(nc.getFormMetadatas());
                }
            }

            Collections.sort(this.navCategories,new Comparator<NavCategory>() {
                //@Override
                public int compare(NavCategory o1, NavCategory o2) {
                    if(o1.getSeqNumber() == null){
                        if(o2.getSeqNumber() != null){
                            return -1;
                        }

                        return o1.getId().intValue() - o2.getId().intValue();
                    }

                    if(o2.getSeqNumber() == null){
                        return 1;
                    }

                    return o1.getSeqNumber() - o2.getSeqNumber();
                }
            });
        }

        return navCategories;
    }
}
