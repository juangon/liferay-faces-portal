/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.demos.bean;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * @author  Philip White
 */
@ManagedBean(name = "testSetupBackingBean")
@RequestScoped
public class TestSetupBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(TestSetupBackingBean.class);

	// Injections
	@ManagedProperty(name = "usersModelBean", value = "#{usersModelBean}")
	private UsersModelBean usersModelBean;

	public void addUser(long creatorUserId, long groupId, long companyId, String firstName, String lastName)
		throws Exception {

		boolean autoPassword = false;
		String password1 = "test";
		String password2 = password1;
		boolean autoScreenName = false;
		String screenName = firstName.toLowerCase() + "." + lastName.toLowerCase();
		String emailAddress = screenName + "@" + "liferay.com";
		long facebookId = 0L;
		String openId = "";
		Locale locale = Locale.ENGLISH;
		String middleName = "";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = 1;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = "";
		long[] groupIds = new long[] { groupId };
		long[] organizationIds = new long[] {};
		long[] roleIds = new long[] {};
		long[] userGroupIds = new long[] {};
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();

		boolean addUser = false;

		try {
			User user = UserLocalServiceUtil.getUserByScreenName(companyId, screenName);

			if ("john.adams".equals(screenName)) {
				UserLocalServiceUtil.deleteUser(user);
				addUser = true;
			}
		}
		catch (NoSuchUserException e) {
			addUser = true;
		}

		if (addUser) {
			UserLocalServiceUtil.addUser(creatorUserId, companyId, autoPassword, password1, password2, autoScreenName,
				screenName, emailAddress, facebookId, openId, locale, firstName, middleName, lastName, prefixId,
				suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
				userGroupIds, sendEmail, serviceContext);
			logger.debug("Added user=[{0}]", screenName);
		}
	}

	public void resetUsers(ActionEvent actionEvent) throws Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			long companyId = LiferayPortletHelperUtil.getCompanyId(facesContext);
			long groupId = LiferayPortletHelperUtil.getLayout(facesContext).getGroupId();
			Company company = CompanyLocalServiceUtil.getCompanyById(companyId);
			User defaultUser = company.getDefaultUser();
			User testUser = UserLocalServiceUtil.getUserByEmailAddress(companyId, "test@liferay.com");
			UserLocalServiceUtil.addGroupUser(groupId, testUser);

			long userId = defaultUser.getUserId();
			setupNewUsers(companyId, groupId, userId);
			usersModelBean.forceListReload();
		}
		catch (NoSuchCompanyException e) {
			FacesContextHelperUtil.addGlobalErrorMessage(facesContext, "is-temporarily-unavailable", "Company service");
			logger.error(e);
		}
	}

	public void setUsersModelBean(UsersModelBean usersModelBean) {

		// Injected via ManagedProperty annotation
		this.usersModelBean = usersModelBean;
	}

	protected void setupNewUsers(long companyId, long groupId, long userId) throws Exception {
		addUser(userId, groupId, companyId, "John", "Adams");
		addUser(userId, groupId, companyId, "Samuel", "Adams");
		addUser(userId, groupId, companyId, "Josiah", "Bartlett");
		addUser(userId, groupId, companyId, "Carter", "Braxton");
		addUser(userId, groupId, companyId, "Charles", "Carroll");
		addUser(userId, groupId, companyId, "Benjamin", "Franklin");
		addUser(userId, groupId, companyId, "Elbridge", "Gerry");
		addUser(userId, groupId, companyId, "John", "Dickinson");
		addUser(userId, groupId, companyId, "John", "Hancock");
		addUser(userId, groupId, companyId, "Thomas", "Jefferson");
		addUser(userId, groupId, companyId, "Francis", "Lewis");
		addUser(userId, groupId, companyId, "Philip", "Livingston");
		addUser(userId, groupId, companyId, "Thomas", "McKean");
		addUser(userId, groupId, companyId, "Arthur", "Middleton");
		addUser(userId, groupId, companyId, "John", "Morton");
		addUser(userId, groupId, companyId, "John", "Penn");
		addUser(userId, groupId, companyId, "George", "Read");
		addUser(userId, groupId, companyId, "John", "Rutledge");
		addUser(userId, groupId, companyId, "Roger", "Sherman");
		addUser(userId, groupId, companyId, "Thomas", "Stone");
		addUser(userId, groupId, companyId, "George", "Taylor");
		addUser(userId, groupId, companyId, "George", "Washington");
		addUser(userId, groupId, companyId, "John", "Witherspoon");
		addUser(userId, groupId, companyId, "Oliver", "Wolcott");
		addUser(userId, groupId, companyId, "George", "Wythe");
	}
}
