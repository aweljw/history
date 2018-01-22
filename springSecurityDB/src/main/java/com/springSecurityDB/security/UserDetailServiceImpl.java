package com.springSecurityDB.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springSecurityDB.domain.UserAccountVO;
import com.springSecurityDB.domain.UserAccountAuthVO;
import com.springSecurityDB.mapper.UserAccountMapper;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserAccountMapper userAccountMapper;

	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        UserAccountVO account = userAccountMapper.findByUserid(userid);

        return loadAdminUserDetails(account, userid);
	}

	private UserDetails loadAdminUserDetails(UserAccountVO account, String loginId) {
        if (account == null) {
            throw new UsernameNotFoundException(loginId);
        }

        List<GrantedAuthority> authorities = new ArrayList<>(1);
        authorities.add(new SimpleGrantedAuthority(account.getRole()));

        boolean accountExpired = false;
        boolean accountLocked = false;
        boolean accountEnabled = true;

        return new UserAccountAuthVO(account.getUserId(), account.getPassword(),
                authorities, accountExpired, accountLocked, accountEnabled);
    }

}
