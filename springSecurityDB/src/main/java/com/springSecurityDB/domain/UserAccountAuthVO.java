package com.springSecurityDB.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserAccountAuthVO implements UserDetails {
	
	/**
     * 유저 패스워드.
     */
	private String password;
	
	/**
     * 유저 id.
     */
	private String userId;

	/**
     * 유저 이름.
     */
	private String username;

	/**
     * 계정 삭제 여부.
     */
	private boolean accountExpired;

	/**
     * 계정 잠김 여부.
     */
	private boolean accountLocked;

	/**
     * 계정 활성화 여부.
     */
    private boolean accountEnabled;

    /**
     * 부여된 권한 목록.
     */
    private Collection<? extends GrantedAuthority> authorities;

    public UserAccountAuthVO(String userId, String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean accountExpired, boolean accountLocked, boolean accountEnabled) {
    	this.userId = userId;
        this.password = password;
        this.authorities = authorities;
        this.accountExpired = accountExpired;
        this.accountLocked = accountLocked;
        this.accountEnabled = accountEnabled;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return accountEnabled;
	}

	public String getuserId() {
		return userId;
	}

}