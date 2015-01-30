package com.dynamo2.myerp.crm.ui.controller.customer;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.dynamo2.myerp.crm.service.AccountService;
import com.dynamo2.myerp.dynamicform.dao.entities.EmailBox;
import com.dynamo2.myerp.dynamicform.service.MailSenderBBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.Contact;
import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.dao.entities.CustomerAccountRelationship;
import com.dynamo2.myerp.crm.dao.entities.CustomerRelationshipsGraph;
import com.dynamo2.myerp.crm.dao.entities.FileUpload;
import com.dynamo2.myerp.crm.dao.entities.Notes;
import com.dynamo2.myerp.crm.dao.entities.Person;
import com.dynamo2.myerp.crm.dao.entities.Todo;
import com.dynamo2.myerp.crm.service.CustomerService;
import com.dynamo2.myerp.crm.service.NOPermissionException;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;
import com.dynamo2.myerp.crm.ui.controller.AbstractManagementBean;
import com.dynamo2.myerp.service.PERMISSION_DEFINITION_ENUM;
import com.dynamo2.myerp.service.ServiceException;
import com.dynamo2.util.JSFUtils;

@ManagedBean(name = "customerForm")
@ViewScoped
@ViewRetained
@WindowDisposed
public class CustomerFormBean extends AbstractManagementBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Current customer
	 */
	private Customer customer;

	/*
	 * Connect to a customer. Current Customer --> Target Customer (Such as,
	 * Current customer is a distributor of target customer.)
	 */
	private CustomerRelationshipsGraph currentCustomerConnectAsLeft;

	/*
	 * Connect to a customer. Target Customer --> Current Customer (Such as,
	 * Target customer is a distributor of current customer.)
	 */
	private CustomerRelationshipsGraph currentCustomerConnectAsRight;

	/*
	 * Customer on current customer's left
	 */
	private List<CustomerRelationshipsGraph> onLeftCustomerList;

	/*
	 * Customer on current customer's right
	 */
	private List<CustomerRelationshipsGraph> onRightCustomerList;

	private List<FileUpload> attachedFiles;

	private Long customerId;

	private List<Contact> contactsList;

	private Contact newContact;

	private List<Notes> notesList;

	private List<Todo> todosList;

	private Notes newNote;

	private Todo newTodo;

	private List<String> boundAccountsList;

	private List<String> oldAccountsList;

	private Long currentTodoId;

	private List<Customer> distributorList;

//    Log log = LogFactory.getLog(CustomerFormBean.class);

	public void setBoundAccountsList(List<String> boundAccountsList) {
		this.boundAccountsList.clear();
		for (String str : boundAccountsList) {
			this.boundAccountsList.add(str);
		}
	}

	public List<String> getBoundAccountsList() {
		if (boundAccountsList == null) {
			boundAccountsList = customerService.listAllBoundAccounts(customerId);
			oldAccountsList = new LinkedList<String>();
			oldAccountsList.addAll(boundAccountsList);
		}
		return boundAccountsList;
	}

	public List<Todo> getTodosList() {
		if (todosList == null) {
			todosList = todoService.listAll(customerId, Todo.FOR_CUSTOMER);
			todoService.retrieveNotes(todosList);
		}
		return todosList;
	}

	public void setTodosList(List<Todo> todosList) {
		this.todosList = todosList;
	}

	public Todo getNewTodo() {
		if (newTodo == null) {
			newTodo = new Todo();
			newTodo.setAssigneerAccount(getCurrentAccount().getAccount());
			newTodo.setAssignerAccount(getCurrentAccount().getAccount());
		}
		return newTodo;
	}

	public void setNewTodo(Todo newTodo) {
		this.newTodo = newTodo;
	}

	public List<Notes> getNotesList() {
		if (notesList == null) {
			List<Notes> rst = notesServcie.listAll(customerId, Notes.FOR_CUSTOMER);
			List<String> roles = getCurrentAccount().getRoleList();
			notesList = new LinkedList<Notes>();
			for (Notes n : rst) {
				if (roles.contains(n.getForRoleName())) {
					notesList.add(n);
				}
			}
		}
		return notesList;
	}

	public void setNotesList(List<Notes> notesList) {
		this.notesList = notesList;
	}

	public Notes getNewNote() {
		if (newNote == null)
			newNote = new Notes();

		return newNote;
	}

	public void setNewNote(Notes newNote) {
		this.newNote = newNote;
	}

	public Customer getCustomer() throws NOPermissionException {
		if (customer == null) {
			if (customerId != null) {
				if (canReadCustomer(customerId)) {
					customer = customerService.loadById(customerId);
				} else {
					throw new NOPermissionException();
				}
			} else {
				if (canCreateCustomer()) {
					customer = new Customer();
					customer.setSalesAccount(appSessionParams.getCurrentAccount().getAccount());
				} else {
					throw new NOPermissionException();
				}
			}
		}

		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

    @Override
    public AccountService getAccountService() {
        return super.getAccountService();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setAccountService(AccountService accountService) {
        super.setAccountService(accountService);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public String save() {

		List<Object[]> rtn = customerService.duplicationCheck(customer);
		if (rtn != null && !rtn.isEmpty()) {
			if (rtn.get(0)[0] != null) {
				JSFUtils.addMessages(JSFUtils.getI18NMessage("duplicatedCustomerName") + ":" + rtn.get(0)[1]);
				return "";
			}
		}

		if (customer.getSalesAccount() == null || customer.getSalesAccount().isEmpty()) {
			customer.setSalesAccount(appSessionParams.getCurrentAccount().getAccount());
		}

		try {
			customerService.newOrUpdate(customer);
            sendChangeEmail();
		} catch (ServiceException e) {
			return ACCESS_DENIED_PAGE;
		}

		return "/customer/customer_list.xhtml?faces-redirect=true";
	}
	
	public String saveServiceStatus() {
		try {
			customerService.newOrUpdate(customer);
		} catch (ServiceException e) {
			return ACCESS_DENIED_PAGE;
		}
		
		return "";
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<Contact> getContactsList() {
		if (contactsList == null) {
			contactsList = contactService.listAll(customerId);
		}
		return contactsList;
	}

	public void setContactsList(List<Contact> contactsList) {
		this.contactsList = contactsList;
	}

	public Contact getNewContact() {
		if (newContact == null) {
			newContact = new Contact();
			newContact.setPerson(new Person());
			newContact.setCustomerId(customerId);
		}

		return newContact;
	}

	public void setNewContact(Contact newContact) {
		this.newContact = newContact;
	}

	public void saveContact() throws ServiceException {
		newContact.getPerson().setId(null);
		newContact.setCustomerId(customerId);
		contactService.newOrUpdate(newContact);
		if (contactsList == null) {
			contactsList = new LinkedList<Contact>();
			contactsList.add(newContact);
		} else {
			contactsList.add(0, newContact);
		}

		// Reset new contact for use next time
		newContact = null;

		JSFUtils.addMessagesToComponent("contactsForm:saveButton",
				JSFUtils.getI18NMessage("operationFinishedSuccessfully"));
	}

	public void saveNote() throws ServiceException {

		newNote.setForId(customerId);
		newNote.setForType(Notes.FOR_CUSTOMER);
		notesServcie.newOrUpdate(newNote);
		if (notesList == null) {
			notesList = new LinkedList<Notes>();
		}
		notesList.add(0, newNote);

		// Reset new contact for use next time
		newNote = null;

		JSFUtils.addMessagesToComponent("notesForm:notesNotes",
				JSFUtils.getI18NMessage("operationFinishedSuccessfully"));
	}

	public void saveTodo() throws ServiceException {

		newTodo.setForId(customerId);
		newTodo.setForType(Todo.FOR_CUSTOMER);
		newTodo.setAssigneerAccount(getCurrentAccount().getAccount());
		newTodo.setAssignerAccount(getCurrentAccount().getAccount());
		todoService.newOrUpdate(newTodo);
		if (todosList == null) {
			todosList = new LinkedList<Todo>();
		}
		todosList.add(0, newTodo);

		// Reset new contact for use next time
		newTodo = null;

		JSFUtils.addMessagesToComponent("todoForm:saveButton", JSFUtils.getI18NMessage("operationFinishedSuccessfully"));
	}

	public void saveAccountWithCustomer() {
		// Bind
		for (String account : boundAccountsList) {
			if (!oldAccountsList.contains(account)) {
				CustomerAccountRelationship car = new CustomerAccountRelationship();
				car.setCustomerId(customerId);
				car.setBoundAccount(account);
				customerService.bindAccountWithCustomerId(car);

				JSFUtils.addMessagesToComponent("salesManagementForm", "Bind [Account:" + account + "] successfully!");
			}
		}

		// Unbind
		for (String account : oldAccountsList) {
			if (!boundAccountsList.contains(account)) {
				customerService.unBindAccountWithCustomerId(account, customerId);
				JSFUtils.addMessagesToComponent("salesManagementForm", "Unbind [id:" + account + "] successfully!");
			}
		}

		// Reset bound account list
		oldAccountsList.clear();
		oldAccountsList.addAll(boundAccountsList);
	}

	public void delContactById() {
		String id = (String) JSFUtils.getHttpRequestParam("delContactId");
		long idLong = Long.parseLong(id);
		contactService.deleteById(idLong);

		Contact c = null;
		for (Iterator<Contact> iterator = contactsList.iterator(); iterator.hasNext();) {
			c = iterator.next();
			if (c.getId().equals(idLong)) {
				iterator.remove();
				break;
			}
		}
	}

	/**
	 * Create a system account according to contact information. The contact
	 * email address will be used as account.
	 * 
	 * @throws ServiceException
	 */
	public void addSystemAccountByContact() throws ServiceException {
		String id = (String) JSFUtils.getHttpRequestParam("contactId");
		Contact c = contactService.loadById(Long.parseLong(id));

		String email = c.getPerson().getEmail();
		if (email == null || email.isEmpty()) {
			// TODO Fan Error handling in here, should never run here.
		}

		Account account = accountService.findByAccount(email);
		if (account != null) {
			JSFUtils.addMessagesToComponent("contactsForm", JSFUtils.getI18NMessage("sameAccountAlreadyExist"));
		}

		// Create a new Account according to contact info.
		account = new Account();
		account.setAccount(email);
		account.setPerson(c.getPerson());
		account.setRoles(Roles_ENUM.ROLE_EMPTY.name());
		account.setNotes("Created from customer contact info.");
		account.setLanguage("zh");
		account.setPassword("111111");
		account.setCustomerId(c.getCustomerId());

		accountService.newOrUpdate(account);

		// Add message in from
		JSFUtils.addMessagesToComponent("contactsForm", JSFUtils.getI18NMessage("createAccountByContactFinished"));
	}

	public void markCurrentTodoId() {
		String id = (String) JSFUtils.getHttpRequestParam("todoId");
		currentTodoId = Long.parseLong(id);
	}

	public void saveTodoProgressNote() throws ServiceException {
		newNote.setForId(currentTodoId);
		newNote.setForType(Notes.FOR_TODO);
		notesServcie.newOrUpdate(newNote);

		// Reset todo list
		todosList = null;
	}

	public void todoStartNow() throws ServiceException {
		String id = (String) JSFUtils.getHttpRequestParam("todoId");
		long idLong = Long.parseLong(id);

		List<Todo> todoList = getTodosList();
		for (Todo todo : todoList) {
			if (todo.getId().equals(idLong)) {
				todo.setActualStart(new Date());
				todoService.newOrUpdate(todo);

				return;
			}
		}
	}

	public void todoEndNow() throws ServiceException {
		String id = (String) JSFUtils.getHttpRequestParam("todoId");
		long idLong = Long.parseLong(id);

		List<Todo> todoList = getTodosList();
		for (Todo todo : todoList) {
			if (todo.getId().equals(idLong)) {
				todo.setActualEnd(new Date());
				todoService.newOrUpdate(todo);

				return;
			}
		}
	}

	public List<Customer> getDistributorList() {
		if (distributorList == null) {
			distributorList = customerService.listDistributors();
		}

		return distributorList;
	}

	public CustomerRelationshipsGraph getCurrentCustomerConnectAsLeft() {
		if (null == currentCustomerConnectAsLeft) {
			currentCustomerConnectAsLeft = new CustomerRelationshipsGraph();
			currentCustomerConnectAsLeft.setLeftCustomerId(customerId);
		}

		return currentCustomerConnectAsLeft;
	}

	public void setCurrentCustomerConnectAsLeft(CustomerRelationshipsGraph currentCustomerConnectAsLeft) {
		this.currentCustomerConnectAsLeft = currentCustomerConnectAsLeft;
	}

	public CustomerRelationshipsGraph getCurrentCustomerConnectAsRight() {
		if (null == currentCustomerConnectAsRight) {
			currentCustomerConnectAsRight = new CustomerRelationshipsGraph();
			currentCustomerConnectAsRight.setRightCustomerId(customerId);
		}
		return currentCustomerConnectAsRight;
	}

	public void setCurrentCustomerConnectAsRight(CustomerRelationshipsGraph currentCustomerConnectAsRight) {
		this.currentCustomerConnectAsRight = currentCustomerConnectAsRight;
	}

	public void connectToDistributor() {
		currentCustomerConnectAsRight.setType(CustomerService.CUSTOMER_RELATIONSHIP_TYPE.DISTRIBUTOR_2_CUSTOMER.name());
		customerService.saveCustomersRelationship(currentCustomerConnectAsRight);

		onLeftCustomerList = null;
	}

	public void disconnect() {
		Long relationshipId = Long.parseLong(JSFUtils.getHttpRequestParam("relationshipId"));

		customerService.disconnectCustomerRelationship(relationshipId);

		onLeftCustomerList = null;
		onRightCustomerList = null;
	}

	public List<CustomerRelationshipsGraph> getOnLeftCustomerList() {
		if (onLeftCustomerList == null) {
			onLeftCustomerList = customerService.listCustomerOnLeft(customerId,
					CustomerService.CUSTOMER_RELATIONSHIP_TYPE.DISTRIBUTOR_2_CUSTOMER.name());
		}

		return onLeftCustomerList;
	}

	public List<CustomerRelationshipsGraph> getOnRightCustomerList() {
		if (onRightCustomerList == null) {
			onRightCustomerList = customerService.listCustomerOnRight(customerId,
					CustomerService.CUSTOMER_RELATIONSHIP_TYPE.DISTRIBUTOR_2_CUSTOMER.name());
		}

		return onRightCustomerList;
	}

	private boolean canReadCustomer(Long customerId) throws NOPermissionException {
		if (appSessionParams.hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ALL)) {
			return true;
		} else if (appSessionParams.hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_QUERY_ASSINGED_TO_ME)) {
			List<String> acctList = customerService.listAllBoundAccounts(customerId);
			if (!CollectionUtils.isEmpty(acctList)) {
				return acctList.contains(getCurrentAccount().getAccount());
			}
		}

		return false;
	}

	private boolean canCreateCustomer() throws NOPermissionException {
		if (appSessionParams.hasPermission(PERMISSION_DEFINITION_ENUM.CUSTOMER_NEW)) {
			return true;
		}

		return false;
	}

	private boolean canWriteCustomer(Customer c) {
		if (c.getId() == null) {
			if (appSessionParams.getPermissions().containsKey(PERMISSION_DEFINITION_ENUM.CUSTOMER_NEW.name())) {
				return true;
			}
		}

		if (appSessionParams.getPermissions().containsKey(PERMISSION_DEFINITION_ENUM.CUSTOMER_EDIT_ALL.name())) {
			return true;
		} else if (appSessionParams.getPermissions().containsKey(
				PERMISSION_DEFINITION_ENUM.CUSTOMER_EDIT_BY_SELF.name())) {
			String acct = getCurrentAccount().getAccount();
			return acct.equals(c.getSalesAccount());
		}

		return false;
	}

	public List<FileUpload> getAttachedFiles() throws NOPermissionException {
		if (attachedFiles == null && getCustomer() != null) {
			//attachedFiles = fileuploadService.findByField("customerId", getCustomer().getId());
            attachedFiles = fileuploadService.findFilesByCustomer(this.getCurrentAccount(), getCustomer().getId());
		}

		return attachedFiles;
	}

    //Send inform email to agent,sales,outsideSales and customerService if customer has changed.
    private void sendChangeEmail(){
        sendChangeEmailByAccount(customer.getAgentAccount());
        sendChangeEmailByAccount(customer.getSalesAccount());
        sendChangeEmailByAccount(customer.getOutsideSalesAccount());
        sendChangeEmailByAccount(customer.getCustomerServiceAccount());
    }

    private void sendChangeEmailByAccount(String accStr){
        if(accStr != null && !accStr.isEmpty()){
            Account agentAccount = accountService.findByAccount(accStr);
            if(agentAccount != null){
                EmailBox emailBox = generateChangeEmail(agentAccount.getPerson().getEmail());
                try {
                    mailBoxService.newOrUpdate(emailBox);
                } catch (ServiceException e) {
                }
            }
        }
    }

    private EmailBox generateChangeEmail(String mailTo) {
        String emailTitle = String.format("%s",JSFUtils.getI18NMessage("CUSTOMER_CHANGE_EMAIL_TITLE"));

        Account modifiedBy = accountService.findByAccount(customer.getLastModifiedBy());
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("customerName", customer.getName());
        params.put("modifyRealName", modifiedBy.getPerson().getRealName());
        params.put("modifyAccount", modifiedBy.getAccount());
        params.put("modifyTime", String.format("%1$tF %1$tT", customer.getLastModified()));

        String emailMsg = mailSenderBBean.buildVMMessage("vm/inform_email_change_customer.vm", params);

        EmailBox eb = new EmailBox();
        eb.setMailTo(mailTo);
        eb.setTitle(emailTitle);
        eb.setMessages(emailMsg);
        eb.setMailFrom(MailSenderBBean.SYSTEM_EMAIL);
        eb.setStatus(EmailBox.STATUS.WAITING.name());

        return eb;
    }
}
