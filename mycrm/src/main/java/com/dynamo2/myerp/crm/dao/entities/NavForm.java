package com.dynamo2.myerp.crm.dao.entities;

import com.dynamo2.myerp.crm.dao.entities.NavCategory;
import com.dynamo2.myerp.dynamicform.dao.FormMetadataDAO;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 3/26/13
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "nav_form")
public class NavForm extends AbstractEntry {
    public enum Type {
        FORM_METADATA(0),SYSTEM(1);

        Integer value = null;
        private Type(int v){
            this.value = v;
        }

        public int val(){
            return this.value;
        }
    }

    private Long id;

    private Long navCategoryId;
    private Long formMetadataId;
    private Long relatedNavFormId;
    private Integer seqNumber;
    private String account;
    private String label;
    private String url;
    private Integer  type;
    private NavForm relatedNavForm;
    private NavCategory navCategory;
    private FormMetadata formMetadata;
    private Date created;
    private String lastModifiedBy;
    private String createdBy;
    private Date lastModified;

    @ManyToOne(fetch= FetchType.EAGER, targetEntity=FormMetadata.class)
    @JoinColumn(name="form_metadata_id", insertable=false, updatable=false)
    public FormMetadata getFormMetadata() {
        return formMetadata;
    }

    public void setFormMetadata(FormMetadata formMetadata) {
        this.formMetadata = formMetadata;
    }

    @ManyToOne(fetch= FetchType.EAGER, targetEntity=NavCategory.class)
    @JoinColumn(name="nav_category_id", insertable=false, updatable=false)
    public NavCategory getNavCategory() {
        return navCategory;
    }

    public void setNavCategory(NavCategory navCategory) {
        this.navCategory = navCategory;
    }

    @Column(name = "seq_number")
    public Integer getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
    }

    @Column(name = "nav_category_id")
    public Long getNavCategoryId() {
        return navCategoryId;
    }

    public void setNavCategoryId(Long navCategoryId) {
        this.navCategoryId = navCategoryId;
    }

    @Column(name = "form_metadata_id")
    public Long getFormMetadataId() {
        return formMetadataId;
    }

    public void setFormMetadataId(Long formMetadataId) {
        this.formMetadataId = formMetadataId;
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

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne(fetch= FetchType.EAGER, targetEntity=NavForm.class)
    @JoinColumn(name="related_nav_form_id", insertable=false, updatable=false)
    public NavForm getRelatedNavForm() {
        return relatedNavForm;
    }

    public void setRelatedNavForm(NavForm relatedNavForm) {
        this.relatedNavForm = relatedNavForm;
    }

    @Column(name = "related_nav_form_id")
    public Long getRelatedNavFormId() {
        return relatedNavFormId;
    }

    public void setRelatedNavFormId(Long relatedNavFormId) {
        this.relatedNavFormId = relatedNavFormId;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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
}
