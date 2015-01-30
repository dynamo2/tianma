package com.dynamo2.myerp.crm.dao.entities;

import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 3/26/13
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "nav_category")
public class NavCategory extends AbstractEntry {
    public enum DeleteFlag {
        YES(1),NO(0);

        private int flag = -1;
        private DeleteFlag(int f){
            flag = f;
        }

        public int value(){
            return flag;
        }
    }

    private String label;
    private Integer seqNumber;
    private String account;
    private Integer deleteFlag = DeleteFlag.NO.value();
    private List<FormMetadata> formMetadatas = new ArrayList<FormMetadata>();

    private Long id;
    private Date created;
    private String lastModifiedBy;
    private String createdBy;
    private Date lastModified;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", length = 19)
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "last_modified_by", length = 45)
    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Column(name = "created_by", length = 45)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified", nullable = false, length = 19)
    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(name = "seq_number")
    public Integer getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
    }

    @Column(name = "delete_flag")
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Transient
    public List<FormMetadata> getFormMetadatas() {
        Collections.sort(this.formMetadatas,new Comparator<FormMetadata>() {
            //@Override
            public int compare(FormMetadata o1, FormMetadata o2) {
                if(o1.getSeqNumber() == null){
                    if(o2.getSeqNumber() != null){
                        return -1;
                    }

                    return o1.getId().intValue()-o2.getId().intValue();
                }

                if(o2.getSeqNumber() == null){
                    return 1;
                }
                return o1.getSeqNumber()-o2.getSeqNumber();
            }
        });
        return this.formMetadatas;
    }

    public void addMatched(List<FormMetadata> forms){
        if(CollectionUtils.isEmpty(forms)){
            return;
        }

        for(FormMetadata f : forms){
            if(this.getId().equals(f.getNavCategoryId())){
                this.formMetadatas.add(f);
            }
        }
    }
}
