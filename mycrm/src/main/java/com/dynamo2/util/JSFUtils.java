package com.dynamo2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

import au.com.bytecode.opencsv.CSVWriter;

public class JSFUtils {
	public static ResourceBundle getResourcesBundles() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, "i18n");
	}

	public static String getI18NMessage(String token) {
		ResourceBundle i18n = getResourcesBundles();

		try {
			String label = i18n.getString(token);
			return label;
		} catch (MissingResourceException e) {
			return "??" + token + "??";
		}
	}

	public static void addMessagesToComponent(String componentId, String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent cmp = context.getViewRoot().findComponent(componentId);
		if (cmp != null) {
			context.addMessage(cmp.getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "info", msg));
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Error: Cannot add message for component [" + componentId + "]"));
		}
	}

	public static void addMessages(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", msg));
	}

	public static boolean isApplicationInvokePhase() {
		return PhaseId.INVOKE_APPLICATION.equals(FacesContext.getCurrentInstance().getCurrentPhaseId());
	}

	public static <T> T getSpringBean(String beanName, Class<T> clazs) {
		FacesContext cnt = FacesContext.getCurrentInstance();
		return cnt.getApplication().evaluateExpressionGet(cnt, "#{" + beanName + "}", clazs);
	}

	public static String getHttpRequestParam(String param) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(param);
	}

	public static UIComponent getJSFComponent(String componentId) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getViewRoot().findComponent(componentId);
	}

	public static <T> T getManagementBean(FacesContext ctx, Class<T> clazs) {
		ManagedBean annotation = clazs.getAnnotation(ManagedBean.class);
		if (annotation == null) {
			return null;
		}

		Object mgmtBean = ctx.getELContext().getELResolver().getValue(ctx.getELContext(), null, annotation.name());

		return clazs.cast(mgmtBean);
	}

	public static ValueExpression createValueExpression(String exp, Class clazs) {
		return FacesContext.getCurrentInstance().getApplication().getExpressionFactory()
				.createValueExpression(FacesContext.getCurrentInstance().getELContext(), exp, clazs);
	}

	public static String getWebRootPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}
	
	public static String getWebFolderRealPath(String webpath) {
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath(webpath);
	}

	public static void downloadFileCSVFileUtf8(String fileName, String[] csvHeader, List<String[]> csvData) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		response.reset(); 
		response.setContentType("text/csv;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".csv\""); 

		CSVWriter writer = null;
		try {
			// Write utf-8 header in output
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
			outputStream.flush();

			writer = new CSVWriter(new OutputStreamWriter(outputStream));
			// Write Header
			List<String[]> csvRst = new ArrayList<String[]>(csvData.size() + 1);
			csvRst.add(csvHeader);
			csvRst.addAll(csvData);
			writer.writeAll(csvRst);
			writer.flush();
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		facesContext.responseComplete(); // Important! Else JSF will attempt to
		// render the response which obviously
		// will fail since it's already written
		// with a file and closed.

	}
	
	public static void downloadFile(File file) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=\"" + file.getName() + "\"");

		ServletOutputStream outputStream = response.getOutputStream();
		FileCopyUtils.copy(new FileInputStream(file), outputStream);
		outputStream.flush();

		facesContext.responseComplete(); // Important! Else JSF will attempt to
		// render the response which obviously
		// will fail since it's already written
		// with a file and closed.

	}
}
