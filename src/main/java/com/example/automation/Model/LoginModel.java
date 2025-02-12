package com.example.automation.Model;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@NamedStoredProcedureQuery(
	      name = LoginModel.BotNamedQuery_BotNameInsertStoreProcedure,
	      procedureName = "BotNamedQuery_BotNameInsertStoreProcedure",
	    		  parameters = {
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "pbotNames", type = String.class)
	    	              })



@NamedStoredProcedureQuery(
	      name = LoginModel.DepartmentNamedQuery_NameInsertStoreProcedure,
	      procedureName = "DepartmentNamedQuery_DepartmentNameInsertStoreProcedure",
	    		  parameters = {
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "pdepartmentNames", type = String.class)
	    	              })


@NamedStoredProcedureQuery(
	      name = LoginModel.locationNamedQuery_NameInsertStoreProcedure,
	      procedureName = "locationNamedQuery_NameInsertStoreProcedure",
	    		  parameters = {
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "plocationNames", type = String.class)
	    	              })



@NamedStoredProcedureQuery(
	      name = LoginModel.NamedQuery_RoleStoreProcedure,
	      procedureName = "get_TblRolemst"
	      )


@NamedStoredProcedureQuery(
	      name = LoginModel.BotNamedQuery_BotNameUpdateStoreProcedure,
	      procedureName = "BotNamedQuery_BotNameUpdateStoreProcedure",
	    		  parameters = {
	    					@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class),

	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "pbotNames", type = String.class)
	    	              })

@NamedStoredProcedureQuery(
	      name = LoginModel.DepartmentNamedQuery_NameUpdateStoreProcedure,
	      procedureName = "DepartmentNamedQuery_DepartmentNameUpdateStoreProcedure",
	    		  parameters = {
	    					@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class) ,

	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "pdepartmentNames", type = String.class)
	    	              })

@NamedStoredProcedureQuery(
	      name = LoginModel.locationNamedQuery_NameUpdateStoreProcedure,
	      procedureName = "locationNamedQuery_NameUpdateStoreProcedure",
	    		  parameters = {
	    					@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class),

	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "plocationNames", type = String.class)
	    	              })

@NamedStoredProcedureQuery(
	      name = LoginModel.NamedQuery_LoginStoreProcedure,
	      procedureName = "Usp_LoginD",
	    		  parameters = {
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "pusername", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "ppassword", type = String.class)
	    	              })

@NamedStoredProcedureQuery(name = LoginModel.NamedQuery_UserstoreProcedure_UserNameModel, procedureName = "get_Tblloginmodel", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "iUserId", type = Integer.class) })


@NamedStoredProcedureQuery(
		name = LoginModel.NamedQuery_RoleInsertStoreProcedure,
	      procedureName = "Usp_Insert_UserRegister",
	    		  parameters = {
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "username", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "password", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "role_id", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "CreatedBy", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "UpdatedBy", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "retry_pass", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "ActionType", type = String.class) 
	    	              })

@NamedStoredProcedureQuery(
		name = LoginModel.NamedQuery_RoleUpdateStoreProcedure,
	      procedureName = "Usp_Update_UserRegister",
	    		  parameters = {
	    					@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "username", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "role_id", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "CreatedBy", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "UpdatedBy", type = String.class),
	    	              @StoredProcedureParameter(mode = ParameterMode.IN, name = "ActionType", type = String.class) 
	    	              })


//Added by 'Vaibhav'
@NamedStoredProcedureQuery(name = LoginModel.NamedQuery_UserUpdateStoreProcedure_UserNameModel, procedureName = "Usp_Update_login", parameters = {
	//	@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_BotName", type = String.class),

		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_UserName", type = String.class),
	//	@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Location", type = String.class),
	//	@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Department", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_CreatedBy", type = String.class),

		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_UpdatedBy", type = String.class),

		@StoredProcedureParameter(mode = ParameterMode.IN, name = "Role", type = String.class), 
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_id", type = Integer.class) })

@NamedStoredProcedureQuery(name = LoginModel.NamedQuery_UserDeleteStoreProcedure_UserNameModel, procedureName = "Usp_Delete_User", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_BotId", type = Integer.class) })
@NamedStoredProcedureQuery(
	      name = LoginModel.NamedQuery_UserListStoreProcedure,
	      procedureName = "Usp_UserDetail"
	      )

@NamedStoredProcedureQuery(name = LoginModel.NamedQuery_UserIdtoreProcedure_UserNameModel, procedureName = "Usp_get_UserData", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "iUserId", type = Integer.class) })

@Entity
@Table(name="login")
public class LoginModel {
	 public static final String NamedQuery_LoginStoreProcedure = "LoginStoreProcedure";
	 public static final String NamedQuery_RoleStoreProcedure = "RoleStoreProcedure";
	 public static final String NamedQuery_RoleInsertStoreProcedure = "RoleInsertStoreProcedure";
	 public static final String NamedQuery_UserListStoreProcedure = "UserListStoreProcedure";
	public static final String NamedQuery_UserstoreProcedure_UserNameModel = "VPROCEDURE";
	public static final String NamedQuery_UserDeleteStoreProcedure_UserNameModel = "UsergetStoreProcedure2";
	public static final String NamedQuery_UserUpdateStoreProcedure_UserNameModel = "VPROCEDURE2";
	public static final String BotNamedQuery_BotNameInsertStoreProcedure = "VPROCEDURE3";
	public static final String DepartmentNamedQuery_NameInsertStoreProcedure = "VPROCEDURE4";
	public static final String locationNamedQuery_NameInsertStoreProcedure = "locationNamedQuery_NameInsertStoreProcedure";
	public static final String BotNamedQuery_BotNameUpdateStoreProcedure = "VPROCEDURE5";
	public static final String locationNamedQuery_NameUpdateStoreProcedure = "VPROCEDURE6";
	public static final String DepartmentNamedQuery_NameUpdateStoreProcedure = "VPROCEDURE7";
	public static final String NamedQuery_RoleUpdateStoreProcedure = "VPROCEDURE8";
	public static final String NamedQuery_UserIdtoreProcedure_UserNameModel = "VPROCEDURE9";

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private int role_id;
    private String role;
    
    
    public LoginModel(Long id, String username, String password, int role_id, String role, List<String> botNames,
			List<String> departmentNames, String retry_pass) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role_id = role_id;
		this.role = role;
		this.botNames = botNames;
		this.departmentNames = departmentNames;
		this.retry_pass = retry_pass;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	@ElementCollection
    private List<String> botNames;

    @ElementCollection
    private List<String> departmentNames;

    private String retry_pass;    
    
    
	public String getRetry_pass() {
		return retry_pass;
	}

	public void setRetry_pass(String retry_pass) {
		this.retry_pass = retry_pass;
	}
	
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

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public LoginModel(){
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LoginModel [id=" + id + ", username=" + username + ", password=" + password + ", role_id=" + role_id
				+ ", role=" + role + ", botNames=" + botNames + ", departmentNames=" + departmentNames + ", retry_pass="
				+ retry_pass + "]";
	}


	
	
	
}
