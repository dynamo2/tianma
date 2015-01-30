package com.dynamo2.myerp.crm.tools;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.com.bytecode.opencsv.CSVReader;

import com.dynamo2.myerp.crm.dao.entities.Contact;
import com.dynamo2.myerp.crm.dao.entities.Customer;
import com.dynamo2.myerp.crm.dao.entities.CustomerRelationshipsGraph;
import com.dynamo2.myerp.crm.dao.entities.Person;
import com.dynamo2.myerp.crm.service.ContactService;
import com.dynamo2.myerp.crm.service.CustomerService;
import com.dynamo2.myerp.crm.service.constant.CONTACT_TYPES_ENUM;
import com.dynamo2.myerp.crm.service.constant.CUSTOMER_QUALITIES_ENUM;
import com.dynamo2.myerp.service.ServiceException;

public class CustomerImport {

	/**
	 * @param args
	 *            args[1] CSV file url
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:com/dynamo2/myerp/crm/tools/application-config.xml" });

		CustomerService cs = context.getBean("customerImportServiceImpl", CustomerService.class);

		ContactService cnt = context.getBean("contactImportServiceImpl", ContactService.class);

		List<Object[]> customers = loadCustomerFromCSV("/home/fwang/personal/HenShenTech/data/DataImport20130101/import_data.csv", cs);

		List<String> importedCustomerNameList = new ArrayList<String>(customers.size());
		for (Object[] data : customers) {
			Customer customer = (Customer) data[0];
			if (importedCustomerNameList.contains(customer.getName())) {
				System.out.println("Dumplicated Customoer -- " + customer.toString());
				continue;
			}

			Customer updateCustomer = null;
			Customer exsitCustomer = cs.findCustomerByName(customer.getName());
			if (exsitCustomer != null) {
				try {
					replaceField(exsitCustomer, customer);
					cs.newOrUpdate(exsitCustomer);
					updateCustomer = exsitCustomer;
					System.out.println("Update customer " + customer.getName());
				} catch (Exception e) {
					System.out.println("Update customer failed " + customer.getName());
					e.printStackTrace();
				}
			} else {
				try {
					cs.newOrUpdate(customer);
					updateCustomer = customer;
					System.out.println("Insert new customer [" + customer.getName() + "]");
				} catch (Exception e) {
					System.out
							.println("Insert Error[" + e.getMessage() + "] [" + customer.toString() + "] [" + e + "]");
				}
			}
			
			// Update contact
			if (data[1] != null) {
				Contact contact = (Contact)data[1];
				contact.setCustomerId(updateCustomer.getId());
				try {
					cnt.newOrUpdate(contact);
					System.out.println("Insert contact for [" + updateCustomer.getName() + "]");
				} catch (ServiceException e) {
					System.out.println("Insert contact for [" + updateCustomer.getName() + "] failed. " + e.getMessage());
				}
			}
			
			// Update connection with distributor
			if (data[2] != null) {
				CustomerRelationshipsGraph cr = (CustomerRelationshipsGraph) data[2];
				cr.setRightCustomerId(updateCustomer.getId());
				cs.saveCustomersRelationship(cr);
				System.out.println("Bind [" + updateCustomer.getName() + "] with " + cr.getLeftCustomerId());
			}
			
			importedCustomerNameList.add(customer.getName());
		}
	}
	
	public static void replaceField(Customer current, Customer to) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Field[] fields = Customer.class.getFields();
		for (Field field : fields) {
			if (!"id".equalsIgnoreCase(field.getName())) {
				String getter = "get" + StringUtils.capitalize(field.getName());
				String setter = "set" + StringUtils.capitalize(field.getName());
				
				Object value = MethodUtils.invokeMethod(to, getter, ArrayUtils.EMPTY_OBJECT_ARRAY);
				if (value != null) {
					MethodUtils.invokeMethod(current, setter, new Object[]{value});
				}
			}
		}
	}

	private static List<Object[]> loadCustomerFromCSV(String csvFile, CustomerService cs) throws IOException {

		FileReader file = new FileReader(csvFile);
		CSVReader reader = new CSVReader(file);

		List<Object[]> rst = new LinkedList<Object[]>();
		String[] columns = null;
		int i = 0;
		while ((columns = reader.readNext()) != null) {
			i++;
			rst.add(createCustomer(columns,cs));
		}

		return rst;
	}

	private static Object[] createCustomer(String[] columns, CustomerService cs) {		
		Customer c = new Customer();
		// 序号1 公司名称2 联系人3 性别4 电话5 手机6 邮件7 传真8 职务9 地址0
		// 客户来源1 网站2 所有人归属3 分级4 沟通记录5 合作年限6 任务安排7 报价单8 销售合同9 行业0
		// 集团公司1 年预计消耗量2 类型3 规模4 年产值5 账号创建人6 备注7 城市8 省份9 邮政编码0
		// 代理商1 区域2 代理商客户编号3
		c.setName(columns[1].trim());
		c.setAddress1(columns[9].trim());
		c.setAgentAccount(columns[30].trim());
		c.setCity(columns[27].trim());
		c.setProvince(columns[28].trim());
		c.setWebsite(columns[11].trim());
		c.setEmail(columns[6].trim());
		c.setPhone(columns[4].trim());
		c.setFax1(columns[7].trim());
		c.setIndustry(columns[19].trim());
		c.setType(columns[22].trim());
		c.setQuality(CUSTOMER_QUALITIES_ENUM.EXISTING_CUSTOMER.name());
		c.setArea(columns[31].trim());
		String distributorCustomerId = columns[32];

		StringBuilder notes = new StringBuilder();
		for (String col : columns) {
			notes.append("\"" + col + "\",");
		}
		c.setNotes("Import from \n" + notes.toString());

		Contact cnt = new Contact();
		cnt.setTitle(columns[8]);
		cnt.setType(CONTACT_TYPES_ENUM.CUSTOMER.name());
		Person person = new Person();
		person.setFirstName(columns[2]);
		if ("男".equals(columns[3].trim()))
			person.setGender("Male");
		else
			person.setGender("Femaile");
		person.setEmail(columns[6]);
		person.setMobile(columns[5]);
		person.setPhone(columns[4]);
		cnt.setPerson(person);
		
		CustomerRelationshipsGraph cr = null;
		Customer distributor = cs.loadByCustomerId(distributorCustomerId);
		if (distributor != null) {
			cr = new CustomerRelationshipsGraph();
			cr.setLeftCustomerId(distributor.getId());
			cr.setType(CustomerService.CUSTOMER_RELATIONSHIP_TYPE.DISTRIBUTOR_2_CUSTOMER.name());
		}
		else {
			System.out.println("Cannot find distributor with customer id [" + distributorCustomerId + "]");
		}

		return new Object[] { c, cnt, cr};
	}

}
