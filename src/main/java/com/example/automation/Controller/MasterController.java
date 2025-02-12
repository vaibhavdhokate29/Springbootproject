package com.example.automation.Controller;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.automation.Model.BotNameModel;
import com.example.automation.Model.DepartmentModel;
import com.example.automation.Model.LocationModel;
import com.example.automation.Model.LoginModel;


@RestController
public class MasterController {

	private static final Logger log = LogManager.getLogger(MasterController.class);

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("automation");

	@RequestMapping(value = "/GetLocationProc", method = RequestMethod.GET)
	public List<LocationModel> getLocation() {
		log.info("GetLocationProc API called");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		StoredProcedureQuery procedureQuery = entityManager
				.createNamedStoredProcedureQuery(LocationModel.NamedQuery_LOCATIONStoreProcedure);
		procedureQuery.execute();
		// @SuppressWarnings("unchecked")
		@SuppressWarnings("unchecked")
		List<LocationModel> resultList = procedureQuery.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(path = "/GetDurationTime", consumes = "application/json", produces = "application/json")

	public String getDurationTimeString(@RequestBody String postData) throws Exception {
		log.info("GetDurationTime API called with data: {}", postData);
		JSONArray ja1 = new JSONArray();
		try {

			String[] arrOfStr = postData.split(",");

			String BotName = "" + "'" + arrOfStr[0].toString() + "'";

			BotName = BotName.replace("{", "").replace("\"", "").replace("'", "");

			BotName = BotName.replace("}", "").replace("\"", "").replace("'", "");
			System.out.println(BotName);
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(BotNameModel.NamedQuery_BotDurationStoreProcedure_BotNameModel);
			entityManager.getTransaction().begin();

			procedureQuery.setParameter("Botname", BotName);

			procedureQuery.execute();
			List<Object[]> resultList = procedureQuery.getResultList();
			for (Object[] r : resultList) {
				// System.out.print(r[0]);
				// System.out.print(r[1]);
				JSONObject obj1 = new JSONObject();
				obj1.put("ProcessId", r[0]);
				obj1.put("Duration", r[1]);
				obj1.put("BotName", r[2]);
				ja1.add(obj1);

			}

			// return resultList;
			return ja1.toString();
			// return "Developer";

		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	  @SuppressWarnings("unchecked")
	    @GetMapping(path = "/GetLocationlist", produces = "application/json")
	    public String getLocationString() {
	        log.info("GetLocationlist API called");
	        EntityManager entityManager = null;
	        JSONArray ja1 = new JSONArray();

	        try {
	            entityManager = entityManagerFactory.createEntityManager();
	            StoredProcedureQuery procedureQuery = entityManager
	                    .createNamedStoredProcedureQuery(LocationModel.NamedQuery_LOCATIONStoreProcedure);
	            procedureQuery.execute();
	            List<Object[]> resultList = procedureQuery.getResultList();

	            for (Object[] r : resultList) {
	                JSONObject obj1 = new JSONObject();
	                obj1.put("LocationId", r[0]);
	                obj1.put("LocationName", r[1]);
	                ja1.add(obj1);
	            }

	            return ja1.toString();

	        } catch (Exception e) {
	            log.error("Error fetching location list", e);
	            return "{\"error\": \"Internal Server Error\"}";

	        } finally {
	            if (entityManager != null && entityManager.isOpen()) {
	                entityManager.close();
	            }
	        }}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/GetDepartmentlist", produces = "application/json")
	public String getDepartmentString() {
		log.info("GetDepartmentlist API called");
		EntityManager entityManager = null;

		JSONArray ja1 = new JSONArray();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(DepartmentModel.NamedQuery_DepartmentNameStoreProcedure);
			procedureQuery.execute();
			List<Object[]> resultList = procedureQuery.getResultList();
			for (Object[] r : resultList) {
				// System.out.print(r[0]);
				// System.out.print(r[1]);
				JSONObject obj1 = new JSONObject();
				obj1.put("DepartmentId", r[0]);
				obj1.put("DepartmentName", r[1]);
				ja1.add(obj1);

			}

			// return resultList;
			return ja1.toString();
			// return "Developer";

		} catch (Exception e) {
			  log.error("Error fetching Department list", e);
	            return "{\"error\": \"Internal Server Error\"}";

		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/UserListData", produces = "application/json")
	public String getUserString() {
		log.info("UserListData API called");
		EntityManager entityManager = null;
		JSONArray ja1 = new JSONArray();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(LoginModel.NamedQuery_UserListStoreProcedure);
			procedureQuery.execute();
			List<Object[]> resultList = procedureQuery.getResultList();

			for (Object[] r : resultList) {

				JSONObject obj1 = new JSONObject();

				obj1.put("id", r[0]);
				obj1.put("username", r[1]);
				obj1.put("name", r[2]);
				obj1.put("CreatedDate", r[3]);
				obj1.put("CreatedBy", r[4]);
				obj1.put("UpdatedDate", r[5]);
				obj1.put("UpdatedBy", r[6]);

				ja1.add(obj1);
				// return "Developer";
			}
			return ja1.toString();
		} catch (Exception e) {
			  log.error("Error fetching User list", e);
	            return "{\"error\": \"Internal Server Error\"}";
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping(path = "/GetBotNamelist", produces = "application/json")
	public String getBotNameString() {
		log.info("GetBotNamelist API called");
		EntityManager entityManager = null;

		JSONArray ja1 = new JSONArray();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(BotNameModel.NamedQuery_BotNameStoreProcedure_BotNameModel);
			procedureQuery.execute();
			List<Object[]> resultList = procedureQuery.getResultList();
			for (Object[] r : resultList) {
				// System.out.print(r[0]);
				// System.out.print(r[1]);
				JSONObject obj1 = new JSONObject();
				obj1.put("BotId", r[0]);
				obj1.put("BotName", r[1]);

				ja1.add(obj1);

			}

			// return resultList;
			return ja1.toString();
			// return "Developer";

		} catch (Exception e) {
			  log.error("Error fetching Botname list", e);
	            return "{\"error\": \"Internal Server Error\"}";
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}

	// added by kiran
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/GetRoleList", produces = "application/json")
	public String getRoleString() {
		log.info("GetRoleList API called");
		EntityManager entityManager = null;

		JSONArray ja1 = new JSONArray();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(LoginModel.NamedQuery_RoleStoreProcedure);
			procedureQuery.execute();
			List<Object[]> resultList = procedureQuery.getResultList();
			for (Object[] r : resultList) {
				// System.out.print(r[0]);
				// System.out.print(r[1]);
				JSONObject obj1 = new JSONObject();
				obj1.put("role_id", r[0]);
				obj1.put("name", r[1]);
				ja1.add(obj1);

			}

			// return resultList;
			return ja1.toString();
			// return "Developer";

		} catch (Exception e) {
			  log.error("Error fetching Role list", e);
	            return "{\"error\": \"Internal Server Error\"}";
		}

		finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}

	// added by kiran
	//@SuppressWarnings("unused")
	@PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
	public String registerData(@RequestBody String postData) throws Exception {
		log.info("register API called with data: {}", postData);

		try {

			 // Remove curly braces and split the data
	        postData = postData.replace("{", "").replace("}", "").replace("\"", "");
	        String[] arrOfStr = postData.split(",");

	        // Extracting values manually
	        String username = arrOfStr[0].split(":")[1].trim();
	        String password = arrOfStr[1].split(":")[1].trim();
	       String retry_pass = arrOfStr[2].split(":")[1].trim();
	       String role_id = arrOfStr[3].split(":")[1].trim();
	        String botNames = arrOfStr[4].split(":")[1].trim();
	        String departmentNames = arrOfStr[5].split(":")[1].trim();
	        String locationNames = arrOfStr[6].split(":")[1].trim();
	        
	     
	        
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(LoginModel.NamedQuery_RoleInsertStoreProcedure);
			
			System.out.println("You are in Register Api");
			entityManager.getTransaction().begin();
		

			procedureQuery.setParameter("username", username);
			procedureQuery.setParameter("password", password);
			procedureQuery.setParameter("role_id", role_id);
			procedureQuery.setParameter("CreatedBy", username);
			procedureQuery.setParameter("UpdatedBy", username);
			procedureQuery.setParameter("retry_pass", retry_pass);
			procedureQuery.setParameter("ActionType", "Insert");
			procedureQuery.execute();

            // Second stored procedure
            if (!botNames.isEmpty()) {
                StoredProcedureQuery procedureQuery2 = entityManager.createNamedStoredProcedureQuery(LoginModel.BotNamedQuery_BotNameInsertStoreProcedure);
                procedureQuery2.setParameter("pbotNames", botNames);
                procedureQuery2.execute();
            }

            // Third stored procedure
            if (!departmentNames.isEmpty()) {
                StoredProcedureQuery procedureQuery3 = entityManager.createNamedStoredProcedureQuery(LoginModel.DepartmentNamedQuery_NameInsertStoreProcedure);
                procedureQuery3.setParameter("pdepartmentNames", departmentNames);
                procedureQuery3.execute();
            }

           // Fourth stored procedure
            if (!locationNames.isEmpty()) {
                StoredProcedureQuery procedureQuery4 = entityManager.createNamedStoredProcedureQuery(LoginModel.locationNamedQuery_NameInsertStoreProcedure);
                procedureQuery4.setParameter("plocationNames", locationNames);
                procedureQuery4.execute();
            }
	entityManager.getTransaction().commit();
			entityManager.close();
			// @SuppressWarnings("unchecked");
			System.out.print("inserted successfully user register");

			return "{    \r\n" + "  			\"RoleId\" : \"Inserted Sucessfully\"\r\n" + "  			}";

		} catch (Exception e) {
			return "Somthing went Wrong";
		}

	}

	// added by kiran
	@GetMapping("/UserList")
	public ModelAndView userList() {
		log.info("UserList API called");

		ModelAndView mav = new ModelAndView("UserList");
		return mav;
	}

}
