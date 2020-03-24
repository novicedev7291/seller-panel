package com.seller.panel.handler;

import com.seller.panel.model.Permissions;
import com.seller.panel.model.Roles;
import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.ObjectError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object thisObj;
    private UserRepository userRepository;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isAuthorized(String verb, String link) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class c = getThis().getClass();
        Method m = c.getMethod("getCompanyId");
        Long companyId = (Long) m.invoke(getThis(), null);
        Users user = userRepository.findByEmailAndCompanyIdAndActive(
                (String)authentication.getPrincipal(), companyId, true);

        /*Set<Roles> roles = user.getRoles();
        Set<Permissions> permissions = new HashSet<>();
        for(Roles role: roles){
            permissions.addAll(role.getPermissions());
        }

        for(Permissions permission: permissions){
            if(permission.getVerb().equalsIgnoreCase(verb) && permission.getLink().equals(link)){
                return true;
            }
        }

        return false;*/

        return true;

    }

    @Override
    public void setFilterObject(Object o) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object o) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return thisObj;
    }

    public void setThis(Object that){
        this.thisObj = that;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
