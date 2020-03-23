package com.seller.panel.handler;

import com.github.javafaker.App;
import com.seller.panel.repository.UserRepository;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private AuthenticationTrustResolverImpl trustResolver = new AuthenticationTrustResolverImpl();
    private ApplicationContext context;

    public CustomMethodSecurityExpressionHandler(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        CustomMethodSecurityExpressionRoot expressionRoot = new CustomMethodSecurityExpressionRoot(authentication);
        expressionRoot.setTrustResolver(trustResolver);
        expressionRoot.setPermissionEvaluator(getPermissionEvaluator());
        expressionRoot.setRoleHierarchy(getRoleHierarchy());
        expressionRoot.setThis(invocation.getThis());
        expressionRoot.setUserRepository((UserRepository) context.getBean(UserRepository.class));
        return expressionRoot;
    }
}
