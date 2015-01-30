package com.dynamo2.myerp.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.crm.dao.AccountDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;

@Service("loginService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LoginServiceImpl extends AbstractUserDetailsAuthenticationProvider {
	@Autowired
	private AccountDAO accountDAO;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO Do nothing here
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		Account account = accountDAO.findByAccount(username);
		if (account != null && account.getPassword().equals(authentication.getCredentials())) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for (String role : account.getRoleList()) {
				authorities.add(new SimpleGrantedAuthority(role));
			}

			authentication.setDetails(account);

			return new User(username, "", true, true, true, true, authorities);
		}

		throw new UsernameNotFoundException("Cannot find user or password is wrong.");
	}
}
