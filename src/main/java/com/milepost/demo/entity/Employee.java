package com.milepost.demo.entity;

import java.util.Date;

public class Employee{

	private String id;
	private String lastName;
	private Integer age;
	private String email;
	private Date birth;
	private Date createTime;
	private String remark;
	private Department department;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(String id, String lastName, Integer age, String email, Date birth, Date createTime, String remark,
			Department department) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.birth = birth;
		this.createTime = createTime;
		this.remark = remark;
		this.department = department;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", age=" + age + ", email=" + email + ", birth="
				+ birth + ", createTime=" + createTime + ", remark=" + remark + ", department=" + department + "]";
	}
	
}
