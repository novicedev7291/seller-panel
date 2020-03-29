package com.seller.panel.handler;

import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        Users user = userRepository.findByEmailAndCompanyId(
                (String)authentication.getPrincipal(), companyId);

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
