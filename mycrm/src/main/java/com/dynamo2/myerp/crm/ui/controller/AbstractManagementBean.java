package com.dynamo2.myerp.crm.ui.controller;

import java.text.SimpleDateFormat;

import javax.faces.bean.ManagedProperty;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.service.*;
import com.dynamo2.myerp.dynamicform.service.FormMetadataService;
import com.dynamo2.myerp.dynamicform.service.MailBoxService;
import com.dynamo2.myerp.dynamicform.service.MailSenderBBean;

public abstract class AbstractManagementBean {
	@ManagedProperty(value = "#{appSessionParams}")
	protected AppSessionParams appSessionParams;

	@ManagedProperty(value = "#{customerService}")
	protected transient CustomerService customerService;

	@ManagedProperty(value = "#{contactService}")
	protected transient ContactService contactService;

	@ManagedProperty(value = "#{notesService}")
	protected transient NotesService notesServcie;

	@ManagedProperty(value = "#{todoService}")
	protected transient TodoService todoService;

	@ManagedProperty(value = "#{accountService}")
	protected transient AccountService accountService;

	@ManagedProperty(value = "#{formMetadataService}")
	protected transient FormMetadataService formMetadataService;
	
	@ManagedProperty(value = "#{roleToPermissionsService}")
	protected transient RoleToPermissionsService roleToPermissionsService;
	
	@ManagedProperty(value = "#{fileuploadService}")
	protected transient FileuploadService fileuploadService;
	
	@ManagedProperty(value = "#{enumListService}")
	protected transient EnumListService enumListService;
	
	@ManagedProperty(value = "#{mailBoxService}")
	protected transient MailBoxService mailBoxService;

    @ManagedProperty(value = "#{navigationService}")
    protected transient NavigationService navigationService;
	
	@ManagedProperty(value = "#{mailSenderBBean}")
	protected transient MailSenderBBean mailSenderBBean;
	
	protected final static String ACCESS_DENIED_PAGE = "/access_forbidden.xhtml?faces-redirect=true";
	
	protected final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

	public FormMetadataService getFormMetadataService() {
		return formMetadataService;
	}

	public void setFormMetadataService(FormMetadataService formMetadataService) {
		this.formMetadataService = formMetadataService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public AppSessionParams getAppSessionParams() {
		return appSessionParams;
	}

	public void setAppSessionParams(AppSessionParams appSessionParams) {
		this.appSessionParams = appSessionParams;
	}

	protected Account getCurrentAccount() {
		return appSessionParams.getCurrentAccount();
	}

	public EnumListService getEnumListService() {
		return enumListService;
	}

	public void setEnumListService(EnumListService enumListService) {
		this.enumListService = enumListService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public ContactService getContactService() {
		return contactService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public NotesService getNotesServcie() {
		return notesServcie;
	}

	public void setNotesServcie(NotesService notesServcie) {
		this.notesServcie = notesServcie;
	}

	public TodoService getTodoService() {
		return todoService;
	}

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
	}

	public RoleToPermissionsService getRoleToPermissionsService() {
		return roleToPermissionsService;
	}

	public void setRoleToPermissionsService(RoleToPermissionsService roleToPermissionsService) {
		this.roleToPermissionsService = roleToPermissionsService;
	}

	public FileuploadService getFileuploadService() {
		return fileuploadService;
	}

	public void setFileuploadService(FileuploadService fileuploadService) {
		this.fileuploadService = fileuploadService;
	}

	public MailBoxService getMailBoxService() {
		return mailBoxService;
	}

	public void setMailBoxService(MailBoxService mailBoxService) {
		this.mailBoxService = mailBoxService;
	}

	public MailSenderBBean getMailSenderBBean() {
		return mailSenderBBean;
	}

	public void setMailSenderBBean(MailSenderBBean mailSenderBBean) {
		this.mailSenderBBean = mailSenderBBean;
	}

    public NavigationService getNavigationService() {
        return navigationService;
    }

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    //	public void validate(javax.faces.context.FacesContext ctx, javax.faces.component.UIComponent cmp, java.lang.Object obj) {
//		FacesMessage msg = new FacesMessage(JSFUtils.getI18NMessage("duplicatedFormName"));
//		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//		throw new ValidatorException(msg);
//	}

}
