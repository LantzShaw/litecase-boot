package com.litecase.boot.web.config.service;

import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userService.findUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User user = userService.findUsername(username);

        System.out.println("=======userDetails=======" + user);

        return UserDetailsImpl.build(user);
    }
}


//import com.litecase.boot.web.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;

//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (username == null || username.isEmpty()) {
//            throw new UsernameNotFoundException("Username cannot be empty");
//        }
//
////        User user = userService.findUsername(username);
//
//        User user = null;
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
////        if (user != null) {
////            //获取该用户所拥有的权限
////            List<SysPermission> sysPermissions = sysPermissionService.selectListByUser(sysUser.getId());
////            // 声明用户授权
////            sysPermissions.forEach(sysPermission -> {
////                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
////                grantedAuthorities.add(grantedAuthority);
////            });
////        }
//
//        return new User(user.getUsername(), user.getPassword());
//    }
//}
