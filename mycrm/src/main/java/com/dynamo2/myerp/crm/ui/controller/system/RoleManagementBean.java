package com.dynamo2.myerp.crm.ui.controller.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import com.dynamo2.myerp.crm.dao.entities.RoleToPermissions;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;
import com.dynamo2.myerp.crm.ui.controller.AbstractManagementBean;
import com.dynamo2.myerp.service.ServiceException;
import com.dynamo2.util.JSFUtils;

@ManagedBean(name = "roleMgmt")
@ViewScoped
@ViewRetained
@WindowDisposed
public class RoleManagementBean extends AbstractManagementBean {

	private static final long serialVersionUID = 7110201613709715706L;

	private RoleToPermissions role;

	private List<RoleToPermissions> roles;

	private List<String> roleStringList;

	private Map<String, String> roleName2Label;

	private boolean roleEditorCollapsed;

	private boolean forEdit;

	@PostConstruct
	public void init() {
		forEdit = false;
		role = new RoleToPermissions();
		roles = roleToPermissionsService.listAll();
		
		// Donot show supervisor role in GUI
		if (!CollectionUtils.isEmpty(roles)) {
			Iterator<RoleToPermissions> iter = roles.iterator();
			while (iter.hasNext()) {
				if (Roles_ENUM.ROLE_SUPERVISOR.name().equalsIgnoreCase(iter.next().getRoleName())) {
					iter.remove();
					break;
				}
			}
		}
		
		roleStringList = new LinkedList<String>();
		roleName2Label = new HashMap<String, String>();
		for (RoleToPermissions r : roles) {
			roleStringList.add(r.getRoleName());
			String label = JSFUtils.getI18NMessage(r.getRoleName());
			if (StringUtils.isBlank(label) || label.startsWith("?")) {
				roleName2Label.put(r.getRoleName(), r.getRoleName());
			} else {
				roleName2Label.put(r.getRoleName(), JSFUtils.getI18NMessage(r.getRoleName()));
			}
		}
		roleEditorCollapsed = true;
	}

	public boolean isRoleEditorCollapsed() {
		return roleEditorCollapsed;
	}

	public RoleToPermissions getRole() {
		return role;
	}

	public void setRole(RoleToPermissions role) {
		this.role = role;
		roleEditorCollapsed = false;
	}

	public List<RoleToPermissions> getRoleList() {
		return roles;
	}

	public void save() throws ServiceException {
		roleToPermissionsService.newOrUpdate(role);
		if (forEdit) {
			forEdit = false;
		}
		init();
	}

	public void validateRoleName(FacesContext ctx, UIComponent cmp, Object obj) {
		if (forEdit) {
			return;
		}

		String roleName = obj.toString();
		for (RoleToPermissions role : roles) {
			if (role.getRoleName().equalsIgnoreCase(roleName)) {
				FacesMessage msg = new FacesMessage(JSFUtils.getI18NMessage("VALIDATOR_DUPLICATED_ROLE_NAME"));
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				// JSFUtils.addMessagesToComponent(cmp.getClientId(),
				// JSFUtils.getI18NMessage("VALIDATOR_DUPLICATED_ROLE_NAME"));
				roleEditorCollapsed = false;
				throw new ValidatorException(msg);
			}
		}
	}

	public List<String> getRoleStringList() {
		return roleStringList;
	}

	public void selectRole4Edit() {
		String roleName = JSFUtils.getHttpRequestParam("roleName");
		for (RoleToPermissions r : roles) {
			if (r.getRoleName().equals(roleName)) {
				role = r;
				roleEditorCollapsed = false;
				forEdit = true;
				break;
			}
		}
	}

	public void deleteRole() {
		String roleName = JSFUtils.getHttpRequestParam("roleName");
		for (RoleToPermissions r : roles) {
			if (r.getRoleName().equals(roleName)) {
				// TODO Fan: Need to check before remove
				roleToPermissionsService.deleteById(r.getId());
				init();
				break;
			}
		}
	}

	public void cancelEdit() {
		forEdit = false;
		role = new RoleToPermissions();
	}

	public boolean isForEdit() {
		return forEdit;
	}

	public Map<String, String> getRoleName2Label() {
		return roleName2Label;
	}
}
