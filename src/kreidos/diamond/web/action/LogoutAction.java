/**
 * Created On 05-Jan-2014
 * Copyright 2010 by Primeleaf Consulting (P) Ltd.,
 * #29,784/785 Hendre Castle,
 * D.S.Babrekar Marg,
 * Gokhale Road(North),
 * Dadar,Mumbai 400 028
 * India
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Primeleaf Consulting (P) Ltd. ("Confidential Information").  
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Primeleaf Consulting (P) Ltd.
 */

package kreidos.diamond.web.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kreidos.diamond.constants.HTTPConstants;
import kreidos.diamond.model.AuditLogManager;
import kreidos.diamond.model.vo.AuditLogRecord;
import kreidos.diamond.model.vo.User;
import kreidos.diamond.web.view.LogoutView;
import kreidos.diamond.web.view.WebView;


/**
 * Author Rahul Kubadia
 */

public class LogoutAction implements Action {

	public WebView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
    	User loggedInUser = (User)session.getAttribute(HTTPConstants.SESSION_KRYSTAL);
    	if(loggedInUser != null)
			AuditLogManager.log(new AuditLogRecord(
					loggedInUser.getUserId(),
					AuditLogRecord.OBJECT_USER,
					AuditLogRecord.ACTION_LOGOUT,
					loggedInUser.getUserName(),
					request.getRemoteAddr(),
					AuditLogRecord.LEVEL_INFO,
					"",""));
		session.invalidate();
		Cookie[] cookies = request.getCookies();
		int cookieLength = cookies.length;
		for (int i = 0; i < cookieLength; i++) {
			Cookie cookie = cookies[i];
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return (new LogoutView(request, response));
	}
}

