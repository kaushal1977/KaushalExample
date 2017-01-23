package org.kkaushal.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.action.ActionMapping;

public class StrutesActionInterceptor implements MethodInterceptor {
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		 System.out.println("inside interceptor");
			if (invocation.getArguments() != null && invocation.getArguments().length >= 2) {
				Object mappingObject = invocation.getArguments()[0];
				Object requestObject = invocation.getArguments()[2];
				if (mappingObject != null && mappingObject instanceof ActionMapping && 
						requestObject != null && requestObject instanceof HttpServletRequest) {
					ActionMapping mapping = (ActionMapping) mappingObject;
					HttpServletRequest request = (HttpServletRequest) requestObject;
					HttpSession session = request.getSession(false);
					boolean risk= true;
					if (risk) {
						return mapping.findForward("home");
					}
				}
			}
		
		return invocation.proceed();
	}
}
