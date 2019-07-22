package com.ocean.cloudoauth.service.impl;

import com.ocean.cloudoauth.feign.UserServiceFeign;
import com.ocean.cloudoauth.vo.RoleVo;
import com.ocean.cloudoauth.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Security用户信息获取实现类
 *
 * @author liuyadu
 */
@Slf4j
@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserServiceFeign userServiceFeign;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
	    UserVo  userVo = userServiceFeign.getUserByUsername(username);
	    if(userVo==null){
		    System.out.println("账号或密码不对！");
	    	throw new UsernameNotFoundException("账号或密码不对！");
	    }
	    System.out.println("有账号");
	    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
	    List<RoleVo> roleVoList =  userVo.getRoleVos();
	    if(roleVoList!=null){
	    	roleVoList.stream().forEach(roleVo -> {
	    		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleVo.getRoleCode());
	    		grantedAuthorities.add(grantedAuthority);

		    });
	    }
	    User userDetails = new User(username,userVo.getPassword(),grantedAuthorities);


        return userDetails;
    }
}
