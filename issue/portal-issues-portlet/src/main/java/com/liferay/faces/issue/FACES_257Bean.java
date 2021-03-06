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
package com.liferay.faces.issue;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "FACES_257Bean")
@RequestScoped
public class FACES_257Bean {


	// Private Data Members
	private String alpha;
	private String beta;
	private String gamma;
	private String requestedURL;

	public FACES_257Bean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		this.alpha = portletRequest.getParameter("alpha");
		this.beta = portletRequest.getParameter("beta");
		this.gamma = portletRequest.getParameter("gamma");

		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String portalURL = PortalUtil.getPortalURL(portletRequest);
		this.requestedURL = portalURL + "/" + themeDisplay.getURLCurrent();
	}

	public String getAlpha() {
		return alpha;
	}

	public String getBeta() {
		return beta;
	}

	public String getGamma() {
		return gamma;
	}

	public String getRequestedURL() {
		return requestedURL;
	}
}
