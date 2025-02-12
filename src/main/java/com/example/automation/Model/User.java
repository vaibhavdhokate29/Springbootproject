package com.example.automation.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.List;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public List<String> getBotNames() {
		return botNames;
	}

	public void setBotNames(List<String> botNames) {
		this.botNames = botNames;
	}

	public List<String> getDepartmentNames() {
		return departmentNames;
	}

	public void setDepartmentNames(List<String> departmentNames) {
		this.departmentNames = departmentNames;
	}

	public List<String> getLocationNames() {
		return LocationNames;
	}

	public void setLocationNames(List<String> locationNames) {
		LocationNames = locationNames;
	}

	private int role_id;
    
    @ElementCollection
    private List<String> botNames;

    @ElementCollection
    private List<String> departmentNames;

    @ElementCollection
    private List<String> LocationNames;
	public User(Long id, String username, String password, String role, int role_id, List<String> botNames,
			List<String> departmentNames, List<String> locationNames) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.role_id = role_id;
		this.botNames = botNames;
		this.departmentNames = departmentNames;
		LocationNames = locationNames;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", role_id="
				+ role_id + ", botNames=" + botNames + ", departmentNames=" + departmentNames + ", LocationNames="
				+ LocationNames + "]";
	}

    
    
}