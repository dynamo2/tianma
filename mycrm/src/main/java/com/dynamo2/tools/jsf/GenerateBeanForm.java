package com.dynamo2.tools.jsf;

import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.Contact;
import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.dao.entities.Notes;
import com.dynamo2.myerp.crm.dao.entities.Person;
import com.dynamo2.myerp.crm.dao.entities.Todo;

public class GenerateBeanForm {
	public static void main(String[] args) {
		Class clasz = Account.class;

		Method[] methods = clasz.getMethods();
		StringBuilder formString = new StringBuilder();
		StringBuilder i18nString = new StringBuilder();
		StringBuilder tableColumnsString = new StringBuilder();

		for (Method m : methods) {
			if (m.getName().startsWith("get")) {
				String property = StringUtils.uncapitalize(m.getName().substring(3));
				Class typeClasz = m.getReturnType();

				formString.append(generateInput(property, StringUtils.uncapitalize(clasz.getSimpleName()), typeClasz));
				formString.append(getNewLine());

				i18nString.append(generateI18NLabel(property));
				i18nString.append(getNewLine());

				tableColumnsString.append(generateColumn(property));
				i18nString.append(getNewLine());
			}
		}

		System.out.println(formString.toString());
		System.out.println(i18nString.toString());
		System.out.println(tableColumnsString.toString());
	}

	private static String generateColumn(String property) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<ace:column headerText=\"#{i18n." + property + "}\">");
		strBuilder.append(getNewLine());
		strBuilder.append("<h:outputText value=\"#{replaceWithBeanName." + property + "}\" />");
		strBuilder.append(getNewLine());
		strBuilder.append("</ace:column>");
		strBuilder.append(getNewLine());

		return strBuilder.toString();
	}

	private static String generateI18NLabel(String property) {
		return property + "=" + StringUtils.capitalize(property);
	}

	private static String generateInput(String property, String jsfMgmtBeanName, Class typeClasz) {
		StringBuilder strBuilder = new StringBuilder();
		if (property.equals("id")) {
			strBuilder.append("<ice:inputHidden id=\"" + jsfMgmtBeanName + "Id\" value=\"#{" + jsfMgmtBeanName
					+ ".id}\" />");
			strBuilder.append(getNewLine());
		} else if (!property.equals("class")) {
			String inputId = getInputId(jsfMgmtBeanName, property);
			String inputValue = jsfMgmtBeanName + "." + property;
			strBuilder.append("<ice:outputLabel value=\"#{i18n." + property + "}\" for=\"" + inputId + "\" />");
			strBuilder.append(getNewLine());
			if (typeClasz.equals(Date.class)) {
				strBuilder.append("<ace:dateTimeEntry id=\"" + inputId + "\" renderAsPopup=\"true\" value=\"#{"
						+ inputValue + "}\" pattern=\"yyyy-MM-dd\"/>");
			} else {
				strBuilder.append("<ice:inputText id=\"" + inputId + "\" value=\"#{" + inputValue
						+ "}\" readonly=\"#{editable eq 'false'}\" />");
			}
			strBuilder.append(getNewLine());
		}

		return strBuilder.toString();
	}

	private static String getNewLine() {
		String newline = System.getProperty("line.separator");
		if (newline == null) {
			return "\n";
		}

		return newline;
	}

	private static String getInputId(String jsfMgmtBeanName, String property) {
		return jsfMgmtBeanName + StringUtils.capitalize(property);
	}
}
