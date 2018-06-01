package com.iebm.pfds.commons.security.vo;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public abstract class AbstractUserDetails implements UserDetails, CredentialsContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8184115411320526751L;

	public abstract void setAuthorities(Set<GrantedAuthority> authorities);

}
