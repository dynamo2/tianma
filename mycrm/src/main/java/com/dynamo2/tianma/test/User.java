package com.dynamo2.tianma.test;

import com.dynamo2.tianma.model.MongodbModel;

public class User extends MongodbModel {
	private String name;
	private String password;
	private Integer age;
	private String mobile;
	private String email;
	private Integer point;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("id=").append(id).append("\n");
		sb.append("name=").append(name).append("\n");
		sb.append("password=").append(password).append("\n");
		sb.append("age=").append(age).append("\n");
		sb.append("mobile=").append(mobile).append("\n");
		sb.append("email=").append(email).append("\n");
		sb.append("point=").append(point).append("\n");
		
		return sb.toString();
	}
}