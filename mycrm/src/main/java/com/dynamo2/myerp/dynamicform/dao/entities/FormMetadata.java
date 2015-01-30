package com.dynamo2.myerp.dynamicform.dao.entities;

// Generated May 20, 2012 10:01:52 AM by Hibernate Tools 3.4.0.CR1

import com.dynamo2.myerp.crm.dao.entities.NavCategory;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormMDStatisticsEntry;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * FormMetadata generated by hbm2java
 */
@Entity
@Table(name = "form_metadata", uniqueConstraints = @UniqueConstraint(columnNames = "form_name"))
@NamedQueries(value = { @NamedQuery(name = "findFormMetadataByName", query = "from FormMetadata where formName=?"),
		@NamedQuery(name = "listAll", query = "from FormMetadata"),
        @NamedQuery(name = "listByParentForm", query = "from FormMetadata where id in (select referToFormMetadataId from FormFieldMetadata where formMetadataId=?)"),
		@NamedQuery(name = "listAllByCategory", query = "from FormMetadata where formCategory=?") })

@SqlResultSetMapping(name = "FormMDStatisticsEntryMapping", entities = @EntityResult(entityClass = FormMDStatisticsEntry.class))
@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "statisticsByStatus", query = "select ft.id as formMetadataId,nt.nc as newCount,et.ec as endCount,ot.oc as proceeingCount from (" +
                " form_metadata as ft left join" +
                " (select form_metadata_id as fid,count(id) as nc from dynamic_form_default where status='NEW' group by form_metadata_id) as nt" +
                " on ft.id = nt.fid left join" +
                " (select form_metadata_id as fid,count(id) as ec from dynamic_form_default where status='END' group by form_metadata_id) as et" +
                " on ft.id=et.fid left join" +
                " (select form_metadata_id as fid,count(id) as oc from dynamic_form_default where status<>'END' and status<>'NEW' group by form_metadata_id) as ot" +
                " on ft.id=ot.fid) where ft.form_status='PUBLISHED';", resultSetMapping = "FormMDStatisticsEntryMapping")})
public class FormMetadata implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String formName;
	private String formLabel;
	private String formLanguage;
	private String formStatus;
	private String formShortName;
	private String canReadAccountList;
	private String canWriteAccountList;
	private String canCreateAccountList;
	private String canRemoveAccountList;
	private String formCategory;
    private Integer seqNumber;
    private Long navCategoryId;

	private List<String> canReadAccounts;
	private List<String> canWriteAccounts;
	private List<String> canCreateAccounts;
	private List<String> canRemoveAccounts;

	public FormMetadata() {
	}

	public FormMetadata(Long id, String formName, String formLabel, String formLanguage, String formStatus) {
		this.id = id;
		this.formName = formName;
		this.formLabel = formLabel;
		this.formLanguage = formLanguage;
		this.formStatus = formStatus;
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

	@Column(name = "form_name", unique = true, nullable = false)
	public String getFormName() {
		return this.formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	@Column(name = "form_label", nullable = false)
	public String getFormLabel() {
		return this.formLabel;
	}

	public void setFormLabel(String formLabel) {
		this.formLabel = formLabel;
	}

	@Column(name = "form_language", nullable = false, length = 45)
	public String getFormLanguage() {
		return this.formLanguage;
	}

	public void setFormLanguage(String formLanguage) {
		this.formLanguage = formLanguage;
	}

	@Column(name = "form_status", nullable = false, length = 45)
	public String getFormStatus() {
		return this.formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}

	@Column(name = "form_short_name")
	public String getFormShortName() {
		return formShortName;
	}

	public void setFormShortName(String formShortName) {
		this.formShortName = formShortName;
	}

	@Column(name = "form_category")
	public String getFormCategory() {
		return formCategory;
	}

	public void setFormCategory(String formCategory) {
		this.formCategory = formCategory;
	}

	@Column(name = "can_read_account_list", length = 65535)
	public String getCanReadAccountList() {
		return this.canReadAccountList;
	}

	public void setCanReadAccountList(String canReadAccountList) {
		this.canReadAccountList = canReadAccountList;
	}

	@Column(name = "can_write_account_list", length = 65535)
	public String getCanWriteAccountList() {
		return this.canWriteAccountList;
	}

	public void setCanWriteAccountList(String canWriteAccountList) {
		this.canWriteAccountList = canWriteAccountList;
	}

	@Column(name = "can_create_account_list", length = 65535)
	public String getCanCreateAccountList() {
		return this.canCreateAccountList;
	}

	public void setCanCreateAccountList(String canCreateAccountList) {
		this.canCreateAccountList = canCreateAccountList;
	}

	@Column(name = "can_remove_account_list", length = 65535)
	public String getCanRemoveAccountList() {
		return this.canRemoveAccountList;
	}

	public void setCanRemoveAccountList(String canRemoveAccountList) {
		this.canRemoveAccountList = canRemoveAccountList;
	}

	public void setCanReadAccounts(List<String> canReadAccounts) {

		this.canReadAccountList = extractAccountListString(canReadAccounts);
	}

	public void setCanWriteAccounts(List<String> canWriteAccounts) {
		this.canWriteAccountList = extractAccountListString(canWriteAccounts);
	}

	public void setCanCreateAccounts(List<String> canCreateAccounts) {
		this.canCreateAccountList = extractAccountListString(canCreateAccounts);
	}

	public void setCanRemoveAccounts(List<String> canRemoveAccounts) {
		this.canRemoveAccountList = extractAccountListString(canRemoveAccounts);
	}

	@Transient
	public List<String> getCanReadAccounts() {
		if (canReadAccounts == null) {
			canReadAccounts = extractAccountList(canReadAccountList);
		}

		return canReadAccounts;
	}

	@Transient
	public List<String> getCanWriteAccounts() {
		if (canWriteAccounts == null) {
			canWriteAccounts = extractAccountList(canWriteAccountList);
		}

		return canWriteAccounts;
	}

	@Transient
	public List<String> getCanCreateAccounts() {
		if (canCreateAccounts == null) {
			canCreateAccounts = extractAccountList(canCreateAccountList);
		}

		return canCreateAccounts;
	}

	@Transient
	public List<String> getCanRemoveAccounts() {
		if (canRemoveAccounts == null) {
			canRemoveAccounts = extractAccountList(canRemoveAccountList);
		}

		return canRemoveAccounts;
	}

    @Transient
    public boolean isReadable(String account){
        return this.getCanReadAccounts().contains(account);
    }

    @Transient
    public boolean isCreatable(String account){
        return this.getCanCreateAccounts().contains(account);
    }

    @Transient
    public boolean isWritable(String account){
        return this.getCanWriteAccounts().contains(account);
    }

	private List<String> extractAccountList(String accountList) {

		if (accountList != null) {
			LinkedList<String> accounts = new LinkedList<String>();
			String[] account = accountList.split(",");
			for (String acct : account) {
				accounts.add(acct);
			}

			return accounts;
		}

		return new LinkedList<String>();
	}

	private String extractAccountListString(List<String> accounts) {
		StringBuilder builder = new StringBuilder();
		for (String account : accounts) {
			builder.append(account).append(",");
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormMetadata [id=").append(id).append(", formName=").append(formName).append(", formLabel=")
				.append(formLabel).append(", formLanguage=").append(formLanguage).append(", formStatus=")
				.append(formStatus).append(", formShortName=").append(formShortName).append(", canReadAccountList=")
				.append(canReadAccountList).append(", canWriteAccountList=").append(canWriteAccountList)
				.append(", canCreateAccountList=").append(canCreateAccountList).append(", canRemoveAccountList=")
				.append(canRemoveAccountList).append("]");
		return builder.toString();
	}

    @Column(name = "nav_category_id")
    public Long getNavCategoryId() {
        return navCategoryId;
    }

    public void setNavCategoryId(Long navCategoryId) {
        this.navCategoryId = navCategoryId;
    }

    @Column(name = "seq_number")
    public Integer getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
    }
}