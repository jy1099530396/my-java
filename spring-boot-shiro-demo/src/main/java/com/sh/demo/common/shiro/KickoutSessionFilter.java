package com.sh.demo.common.shiro;

import com.sh.demo.common.util.IpUtils;
import com.sh.demo.common.util.ShiroUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @Author Jy
 * @Date 2024-04-17 10:24
 */

//@Log4j2
public class KickoutSessionFilter  extends AccessControlFilter{

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Subject subject = SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
        boolean remembered = subject.isRemembered();
        //如果用户未登录，跳过此过程
        if(!authenticated) {
//        if(!authenticated && !remembered) {
            return true;
        }
        String ipAddr = IpUtils.getIpAddr(request);

        Object principal = subject.getPrincipal();
        System.out.println(principal);
        Serializable id = subject.getSession().getId();
        ShiroUtils.deleteCache(id,true);
        System.out.println(ipAddr);
        return true;
//        return false;
    }
}
