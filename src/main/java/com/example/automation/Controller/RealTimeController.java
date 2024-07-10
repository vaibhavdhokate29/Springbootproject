package com.example.automation.Controller;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.automation.Model.LoginModel;
import com.example.automation.Model.RealTimeModel;
import com.example.automation.Repository.RealTimeInterface;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping(path = "/RealTimeDataValues")
public class RealTimeController {

	private static final Logger log = LogManager.getLogger(RealTimeController.class);

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("automation");

	@RequestMapping(value = "/AllRealTimeDataValue", method = RequestMethod.GET)
	public List<RealTimeModel> getRealtimes(Integer LocationId, Integer Departmentid, Integer BotId) {
		// return realtimedataRepos.getRealTime(1,1,1);
		log.info("Received request to fetch all real-time data values with LocationId: {}, DepartmentId: {}, BotId: {}",
				LocationId, Departmentid, BotId);

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		StoredProcedureQuery procedureQuery = entityManager
				.createNamedStoredProcedureQuery(RealTimeModel.NamedQuery_KhalidStoreProcedure);
		procedureQuery.setParameter("pLocationId", 1);
		procedureQuery.setParameter("pDepartmentId", 1);
		procedureQuery.setParameter("pBotId", 1);
		procedureQuery.execute();
		@SuppressWarnings("unchecked")
		List<RealTimeModel> resultList = procedureQuery.getResultList();
		return resultList;
	}

	@PostMapping(path = "/PostGetDashboardGrid", consumes = "application/json", produces = "application/json")
	public String PostDataGridResponse(@RequestBody String postData) throws Exception {
		log.info("Received request to fetch dashboard grid with postData: {}", postData);

		List<String> data1 = new ArrayList<String>();
		JSONArray ja1 = new JSONArray();
		try {

			String[] arrOfStr = postData.split(",");
			String LocationId = "" + "'" + arrOfStr[0].toString() + "'";
			LocationId = LocationId.replace("\"", "");
			// System.out.print(LocationId + "Location");
			String DepartmentId = "" + "'" + arrOfStr[1].toString() + "'";
			DepartmentId = DepartmentId.replace("\"", "");
			// System.out.print(DepartmentId + "Department");
			String BotId = "" + "'" + arrOfStr[2].toString() + "'";
			BotId = BotId.replace("\"", "");
			// System.out.print(BotId + "BotId");

			EntityManager entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(RealTimeModel.NamedQuery_KhalidStoreProcedure);
			LocationId = LocationId.replace("{LocationId:", "");
			DepartmentId = DepartmentId.replace("DepartmentId:", "");
			BotId = BotId.replace("BotId:", "").replace("}", "");

			LocationId = LocationId.replace("'", "");
			DepartmentId = DepartmentId.replace("'", "");
			BotId = BotId.replace("'", "");

			// Extract the SelectedDate field
			String selectedDate = "";
			String formattedStartTime = "";
			// Split the postData string by ","
			String[] parts = postData.split(",");

			// Iterate over each part to find the SelectedDate field
			for (String part : parts) {
				// Check if the part contains SelectedDate
				if (part.contains("\"SelectedDate\":")) {
					// Split the part by ":" to get the value
					String[] keyValue = part.split(":");
					// Get the value of SelectedDate
					selectedDate = keyValue[1].replace("\"", ""); // Remove surrounding quotes
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					formattedStartTime = dateFormat.format(selectedDate);
					break; // Stop iterating further
				}
			}

			procedureQuery.setParameter("pLocationId", Integer.parseInt(LocationId));
			procedureQuery.setParameter("pDepartmentId", Integer.parseInt(DepartmentId));
			procedureQuery.setParameter("pBotId", Integer.parseInt(BotId));
			procedureQuery.setParameter("pType", "Grid");
			procedureQuery.setParameter("pSearch", "");
			// procedureQuery.setParameter("pSelectedDate",formattedStartTime );
			procedureQuery.execute();
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			for (Object[] r : resultList) {
				// System.out.print(r[1]);

				// System.out.print(r[2]);
				JSONObject obj1 = new JSONObject();
				// obj1.put("total_Bot_count",r[0]);
				obj1.put("ProcessId", r[0]);
				obj1.put("BotName", r[1]);
				obj1.put("LocationName", r[2]);
				obj1.put("DepartmentName", r[3]);
				obj1.put("Process_Name", r[4]);
				obj1.put("Status", r[5]);
				obj1.put("Remarks", r[6]);
				obj1.put("CreatedBy", r[7]);

				// Check if r[8] is not null before putting it in JSON
				if (r[8] != null) {
					// Assuming r[8] is a Date object, you may want to format it
					// appropriately before adding it to the JSON object
					// For example, you can convert it to a string in ISO8601 format
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					String formattedStartTime2 = dateFormat.format(r[8]);
					obj1.put("StartTime", formattedStartTime2);
				} else {
					// Handle the case where r[8] is null
					obj1.put("StartTime", null);
				}

				ja1.add(obj1);
			}

			return ja1.toString();
			// }
		} catch (Exception e) {
			// TODO: handle exceptionY
			e.printStackTrace();

		}
		return null;
	}

	// -- 20 feb 2024 Vaibhav--/
	@GetMapping(path = "/PostGetDashboardGridToExcel2", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public void postDashboardGridToExcel2(HttpServletResponse response) throws IOException, Exception {
		log.info("Received request to export dashboard grid to Excel.");

		try (Workbook workbook = new XSSFWorkbook()) {

			// Set response headers for Excel file
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=DashboardData.xlsx");

			// Populate the workbook with data
			List<String> data = new ArrayList<String>();
			JSONArray ja1 = new JSONArray();
			ObjectMapper mapper = new ObjectMapper();
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(RealTimeModel.NamedQuery_VaibhavStoreProcedure);
			procedureQuery.execute();
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			for (Object[] r : resultList) {
				JSONObject obj1 = new JSONObject();
				obj1.put("ProcessId", r[0]);
				obj1.put("BotName", r[1]);
				obj1.put("LocationName", r[2]);
				obj1.put("DepartmentName", r[3]);
				obj1.put("Process_Name", r[4]);
				obj1.put("Status", r[5]);
				obj1.put("Remarks", r[6]);
				obj1.put("CreatedBy", r[7]);
				ja1.add(obj1);
			}

			try (OutputStream outputStream2 = response.getOutputStream()) {
				Sheet sheet = workbook.createSheet("BotData");

				// Create header row
				Row headerRow = sheet.createRow(0);
				headerRow.createCell(0).setCellValue("ProcessId");
				headerRow.createCell(1).setCellValue("BotName");
				headerRow.createCell(2).setCellValue("LocationName");
				headerRow.createCell(3).setCellValue("DepartmentName");
				headerRow.createCell(4).setCellValue("Process_Name");
				headerRow.createCell(5).setCellValue("Status");
				headerRow.createCell(6).setCellValue("Remarks");
				headerRow.createCell(7).setCellValue("CreatedBy");

				// Populate data rows
				int rowNum = 1; // Start from the second row for data
				for (Object json : ja1) {
					JSONObject jsonObject = (JSONObject) json;
					Row dataRow = sheet.createRow(rowNum++);
					dataRow.createCell(0).setCellValue(String.valueOf(jsonObject.get("ProcessId")));
					dataRow.createCell(1).setCellValue(String.valueOf(jsonObject.get("BotName")));
					dataRow.createCell(2).setCellValue(String.valueOf(jsonObject.get("LocationName")));
					dataRow.createCell(3).setCellValue(String.valueOf(jsonObject.get("DepartmentName")));
					dataRow.createCell(4).setCellValue(String.valueOf(jsonObject.get("Process_Name")));
					dataRow.createCell(5).setCellValue(String.valueOf(jsonObject.get("Status")));
					dataRow.createCell(6).setCellValue(String.valueOf(jsonObject.get("Remarks")));
					dataRow.createCell(7).setCellValue(String.valueOf(jsonObject.get("CreatedBy")));
				}

				// Write workbook to output stream
				workbook.write(outputStream2);
				log.info("Excel export completed successfully.");

			} catch (Exception e) {
				log.error("Error occurred during Excel export: {}", e.getMessage());

				e.printStackTrace();
				// Handle exception (e.g., log, show error message)
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Handle IO exception (e.g., log, show error message)
		}
	}

	@PostMapping("/importExcel")
	public String importExcel(@RequestParam("DashboardData") MultipartFile file, @RequestParam String botName,
			HttpServletRequest request) {

//JSONObject BotInfo=new JSONObject(botInfo);
		JSONObject ob = new JSONObject();
		JSONArray ja1 = new JSONArray();


		System.out.println("botName  : " + botName);

		try (InputStream inputStream = file.getInputStream()) {
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0); // Assuming there's only one sheet
			int nonEmptyCellCount = 0;

			// Iterate through each row
			boolean isFirstRow = true;

			for (Row row : sheet) {
				if (isFirstRow) {
					isFirstRow = false;
					continue; // Skip header row
				}
				Cell cell = row.getCell(0);
				if (cell != null && cell.getCellType() != CellType.BLANK) {
					nonEmptyCellCount++;
				}
			}
			System.out.println("Number of non-empty cells in the first column: " + nonEmptyCellCount);
			HttpSession session = request.getSession();
			session.setAttribute("botCount", nonEmptyCellCount);
			session.setAttribute("botName", botName);
			String botname = (String) session.getAttribute("botName");
			System.out.println("in session bot name : " + botname);
			int botCount = (int) session.getAttribute("botCount");
			System.out.println("in session bot count: " + botCount);
			// Close the workbook
			workbook.close();

			EntityManager entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();

			int processCount = 0;
			int unProcessCount = 0;
			int ProcessId = 0;
			int BotId = 0;
			int DepartmentId = 0;
			int LocationId = 0;
			int Transaction_ID = 0;
			String Process_Name = "";
			String Host_Name = "";
			String CreatedBy = "";
			String Status = "";
			String Remarks = "";
			String Bot_Name = "";
			String Department_Name = "";
			String Location_Name = "";
			isFirstRow = true;
			long total_processCount = processCount;
			System.out.println("total_processCount: " + total_processCount);
			long total_unProcessCount = unProcessCount;
			System.out.println("total_unProcessCount: " + total_unProcessCount);

			for (Row row : sheet) {
				if (isFirstRow) {
					isFirstRow = false;
					continue; // Skip header row
				}

				// Extract data from cells
				Cell cell = row.getCell(0); // Assuming it's the first column
				// System.out.println(cell.toString());
				ProcessId = 0;
				if (cell.getCellType() == CellType.NUMERIC) {
					ProcessId = (int) cell.getNumericCellValue();
				} else if (cell.getCellType() == CellType.STRING) {
					// Handle string value, maybe parse or convert it to numeric
					ProcessId = Integer.parseInt(cell.getStringCellValue());
				} else {
					// Handle other cell types if needed
				}
				Cell cell1 = row.getCell(1);
				BotId = 0;
				if (cell1.getCellType() == CellType.NUMERIC) {
					BotId = (int) cell1.getNumericCellValue();
					System.out.println(BotId);
				} else if (cell1.getCellType() == CellType.STRING) {
					// Handle string value, maybe parse or convert it to numeric
					BotId = Integer.parseInt(cell1.getStringCellValue());
				} else {
					// Handle other cell types if needed
				}
				Cell cell3 = row.getCell(2);
				DepartmentId = 0;
				if (cell3.getCellType() == CellType.NUMERIC) {
					DepartmentId = (int) cell3.getNumericCellValue();
				} else if (cell3.getCellType() == CellType.STRING) {
					// Handle string value, maybe parse or convert it to numeric
					DepartmentId = Integer.parseInt(cell3.getStringCellValue());
				} else {
					// Handle other cell types if needed
				}

				Cell cell4 = row.getCell(3);
				LocationId = 0;
				if (cell4.getCellType() == CellType.NUMERIC) {
					LocationId = (int) cell4.getNumericCellValue();
				} else if (cell4.getCellType() == CellType.STRING) {
					// Handle string value, maybe parse or convert it to numeric
					LocationId = Integer.parseInt(cell4.getStringCellValue());
				} else {
					// Handle other cell types if needed
				}
				Process_Name = row.getCell(4).getStringCellValue();
				Host_Name = row.getCell(5).getStringCellValue();

				Cell cell5 = row.getCell(6);
				Transaction_ID = 0;
				if (cell5.getCellType() == CellType.NUMERIC) {
					Transaction_ID = (int) cell5.getNumericCellValue();
				} else if (cell5.getCellType() == CellType.STRING) {
					// Handle string value, maybe parse or convert it to numeric
					Transaction_ID = Integer.parseInt(cell5.getStringCellValue());
				} else {
					// Handle other cell types if needed
				}
				Date StartTime = row.getCell(7).getDateCellValue();
				Date EndTime = row.getCell(8).getDateCellValue();
				Status = row.getCell(9).getStringCellValue();
				System.out.println("Status: " + Status);
				if (Status.equalsIgnoreCase("processed")) {
					processCount++;
				} else {
					unProcessCount++;
				}
				System.out.println("processCount: " + processCount + " " + "unProcessCount: " + unProcessCount);
				Remarks = row.getCell(10).getStringCellValue();
				Date CreatedDate = row.getCell(11).getDateCellValue();
				CreatedBy = row.getCell(12).getStringCellValue();
				Date UpdatedDate = row.getCell(13).getDateCellValue();
				String UpdatedBy = row.getCell(14).getStringCellValue();
				// String IsActive = row.getCell(15).getStringCellValue();
				Cell cell6 = row.getCell(15); // Assuming it's the first column
				int IsActive = 0;
				if (cell.getCellType() == CellType.NUMERIC) {
					IsActive = (int) cell.getNumericCellValue();
				} else if (cell.getCellType() == CellType.STRING) {
					// Handle string value, maybe parse or convert it to numeric
					IsActive = Integer.parseInt(cell.getStringCellValue());
				} else {
					// Handle other cell types if needed
				}
				Bot_Name = row.getCell(16).getStringCellValue();
				Department_Name = row.getCell(17).getStringCellValue();
				Location_Name = row.getCell(18).getStringCellValue();
                StoredProcedureQuery procedureQuery = entityManager.createNamedStoredProcedureQuery(RealTimeModel.NamedQuery_Vaibhav_InserExcel_StoreProcedure);

                procedureQuery.setParameter("p_ProcessId", ProcessId);
                procedureQuery.setParameter("p_BotId", BotId);
                procedureQuery.setParameter("p_DepartmentId", DepartmentId);
                procedureQuery.setParameter("p_LocationId", LocationId);
                procedureQuery.setParameter("p_Process_Name", Process_Name);
                procedureQuery.setParameter("p_Host_Name", Host_Name);
                procedureQuery.setParameter("p_Transaction_ID", Transaction_ID);
                procedureQuery.setParameter("p_Status", Status);
                procedureQuery.setParameter("p_Remarks", Remarks);
                procedureQuery.setParameter("p_CreatedBy", CreatedBy);
                procedureQuery.setParameter("p_UpdatedBy", UpdatedBy);
                procedureQuery.setParameter("p_IsActive", IsActive);
                procedureQuery.execute();
                

                // Retrieve data using the second stored procedure
                StoredProcedureQuery procedureQuery1 = entityManager.createNamedStoredProcedureQuery(RealTimeModel.NamedQuery_importBotSelect);

                procedureQuery1.setParameter("p_ProcessId1", ProcessId);
                procedureQuery1.setParameter("p_BotId1", Bot_Name);
                procedureQuery1.setParameter("p_DepartmentId1", Department_Name);
                procedureQuery1.setParameter("p_LocationId1", Location_Name);
                procedureQuery1.setParameter("p_Transaction_ID1", Transaction_ID);
                procedureQuery1.setParameter("p_Process_Name1", Process_Name);
                procedureQuery1.setParameter("p_Host_Name1", Host_Name);
                procedureQuery1.setParameter("p_CreatedBy1", CreatedBy);
                procedureQuery1.setParameter("p_Status1", Status);
                procedureQuery1.setParameter("p_Remarks1", Remarks);
                procedureQuery1.execute();

                List<Object[]> result1 = procedureQuery1.getResultList();
                
                 total_processCount = processCount;
    			System.out.println("total_processCount: "+total_processCount);
    			 total_unProcessCount = unProcessCount;
    			System.out.println("total_unProcessCount: "+total_unProcessCount);

                for (Object[] r : result1) {
                    JSONObject obj1 = new JSONObject();
                    obj1.put("ProcessId", r[0]);
                    obj1.put("BotId", r[1]);
                    obj1.put("DepartmentId", r[2]);
                    obj1.put("LocationId", r[3]);
                    obj1.put("Transaction_ID", r[4]);
                    obj1.put("Process_Name", r[5]);
                    obj1.put("Host_Name", r[6]);
                    obj1.put("CreatedBy", r[7]);
                    obj1.put("Status", r[8]);
                    obj1.put("Remarks", r[9]);
                    obj1.put("Data status", "Data imported successfully");
                    obj1.put("nonEmptyCellCount", nonEmptyCellCount);
                    obj1.put("total_processCount", total_processCount);
                    obj1.put("total_unProcessCount", total_unProcessCount);
                    ja1.add(obj1);

			}
			}
			
   System.out.println("jjj"+ja1);
			entityManager.getTransaction().commit();
			entityManager.close();
			workbook.close();

			return ja1.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "Error occurred during import: " + e.getMessage();
		}

	}

	// Helper methods to get cell values safely
	private int getIntCellValue(Cell cell) {
		if (cell == null) {
			return 0;
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			return (int) cell.getNumericCellValue();
		} else if (cell.getCellType() == CellType.STRING) {
			try {
				return Integer.parseInt(cell.getStringCellValue());
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return 0;
	}

	private String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf((int) cell.getNumericCellValue());
		}
		return "";
	}

	private Date getDateCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
			return cell.getDateCellValue();
		}
		return null;
	}

	@PostMapping(path = "/PostGetDashboardGridSearch", consumes = "application/json", produces = "application/json")
	public String PostDataGridSearchResponse(@RequestBody String postData) throws Exception {
		log.info("Received request to fetch dashboard grid with search data: {}", postData);

		List<String> data1 = new ArrayList<String>();
		JSONArray ja1 = new JSONArray();
		try {

			// System.out.print(postData);
			String[] arrOfStr = postData.split(",");
			String LocationId = "" + "'" + arrOfStr[0].toString() + "'";
			LocationId = LocationId.replace("\"", "");
			// System.out.print(LocationId + "Location");
			String DepartmentId = "" + "'" + arrOfStr[1].toString() + "'";
			DepartmentId = DepartmentId.replace("\"", "");
			// System.out.print(DepartmentId + "Department");
			String BotId = "" + "'" + arrOfStr[2].toString() + "'";
			BotId = BotId.replace("\"", "");
			// System.out.print(BotId + "BotId");
			String Searchd = "" + "'" + arrOfStr[3].toString() + "'";
			Searchd = Searchd.replace("\"", "");

			EntityManager entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(RealTimeModel.NamedQuery_KhalidStoreProcedure);
			LocationId = LocationId.replace("{LocationId:", "");
			DepartmentId = DepartmentId.replace("DepartmentId:", "");
			BotId = BotId.replace("BotId:", "").replace("}", "");
			Searchd = Searchd.replace("search:", "").replace("}", "");

			LocationId = LocationId.replace("'", "");
			DepartmentId = DepartmentId.replace("'", "");
			BotId = BotId.replace("'", "");
			Searchd = Searchd.replace("'", "");

			// System.out.print(LocationId);
			// System.out.print(DepartmentId);
			// System.out.print(BotId);
			// System.out.print(Searchd);

			procedureQuery.setParameter("pLocationId", Integer.parseInt(LocationId));
			procedureQuery.setParameter("pDepartmentId", Integer.parseInt(DepartmentId));
			procedureQuery.setParameter("pBotId", Integer.parseInt(BotId));
			procedureQuery.setParameter("pType", "Search");
			procedureQuery.setParameter("pSearch", Searchd);

			procedureQuery.execute();
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();

			System.out.print(resultList);
			for (Object[] r : resultList) {
				System.out.print(r);
				// System.out.print(r[2]);
				JSONObject obj1 = new JSONObject();
				// obj1.put("total_Bot_count",r[0]);
				obj1.put("ProcessId", r[0]);
				obj1.put("BotName", r[1]);
				obj1.put("LocationName", r[2]);
				obj1.put("DepartmentName", r[3]);
				obj1.put("Process_Name", r[4]);
				obj1.put("Status", r[5]);
				obj1.put("Remarks", r[6]);
				obj1.put("CreatedBy", r[7]);
				// obj1.put("StartTime", r[8]);
				ja1.add(obj1);
			}

			return ja1.toString();
			// }
		} catch (Exception e) {
			log.error("Error processing request to fetch dashboard grid data: {}", e.getMessage(), e);
			throw e;// TODO: handle exception
		}

	}

	// For Login(PostMethhod)
	@PostMapping(path = "/PostGetLoginData", consumes = "application/json", produces = "application/json")
	public String PostDataLoginResponse(@RequestBody String postData) throws Exception {
		List<String> data1 = new ArrayList<String>();
		JSONArray ja1 = new JSONArray();
		try {
			// System.out.println(postData);

			String[] arrOfStr = postData.split(",");
			String UserName = "" + "'" + arrOfStr[0].toString() + "'";
			UserName = UserName.replace("\"", "");

			// System.out.print(LocationId + "Location");
			String Password = "" + "'" + arrOfStr[1].toString() + "'";
			Password = Password.replace("\"", "");
			// System.out.print(DepartmentId + "Department");

			EntityManager entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(LoginModel.NamedQuery_LoginStoreProcedure);
			UserName = UserName.replace("{UserName:", "");
			Password = Password.replace("Password:", "");
			UserName = UserName.replace("'", "");
			Password = Password.replace("'", "");
			UserName = UserName.replace("{Username:", "");
			Password = Password.replace("}", "");
			// System.out.println(UserName);
			// System.out.println(Password);
			procedureQuery.setParameter("pusername", UserName);
			procedureQuery.setParameter("ppassword", Password);
			procedureQuery.execute();
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			// U code i will guide. Check the lenth or count of list with if.Daro mat main
			// hona leanr it and code
			if (resultList.size() > 0) {
				// System.out.print("listcount > 0");
				for (Object[] r : resultList) {
					JSONObject obj1 = new JSONObject();
					obj1.put("NotFound", "Found");
					ja1.add(obj1);
				}
			} else {
				// System.out.print("listcount =0");
				JSONObject obj1 = new JSONObject();
				obj1.put("NotFound", "Invalid Credential !!!");
				ja1.add(obj1);
			}
			return ja1.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	// Khalid
	// For Login(PostMethhod)
	@PostMapping(path = "/PostGetLoginData1", consumes = "application/json", produces = "application/json")
	public String PostDataLoginResponse1(@RequestBody String postData, HttpServletRequest request) throws Exception {
		log.info("Received login request with data: {}", postData);

		List<String> data1 = new ArrayList<String>();
		JSONArray ja1 = new JSONArray();
		try {
			// System.out.println(postData);

			String[] arrOfStr = postData.split(",");
			String UserName = "" + "'" + arrOfStr[0].toString() + "'";
			UserName = UserName.replace("\"", "");

			// System.out.print(LocationId + "Location");
			String Password = "" + "'" + arrOfStr[1].toString() + "'";
			Password = Password.replace("\"", "");
			// System.out.print(DepartmentId + "Department");

			EntityManager entityManager = entityManagerFactory.createEntityManager();
			StoredProcedureQuery procedureQuery = entityManager
					.createNamedStoredProcedureQuery(LoginModel.NamedQuery_LoginStoreProcedure);
			UserName = UserName.replace("{UserName:", "");
			Password = Password.replace("Password:", "");
			UserName = UserName.replace("'", "");
			Password = Password.replace("'", "");
			UserName = UserName.replace("{Username:", "");
			Password = Password.replace("}", "");
			System.out.println(UserName);
			System.out.println(Password);
			procedureQuery.setParameter("pusername", UserName);
			procedureQuery.setParameter("ppassword", Password);
			procedureQuery.execute();
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			// U code i will guide. Check the lenth or count of list with if.Daro mat main
			// hona leanr it and code
			if (resultList.size() > 0) {
				// Session

				List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
				if (messages == null) {
					messages = new ArrayList<>();
					request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
				}
				messages.add(UserName);
				request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);

				// Session

				// System.out.print("listcount > 0");
				for (Object[] r : resultList) {
					JSONObject obj1 = new JSONObject();
					obj1.put("NotFound", "Found");
					obj1.put("Name", r[1]);
					obj1.put("role_Id", r[3]);
					ja1.add(obj1);
				}
			} else {
				// System.out.print("listcount =0");
				JSONObject obj1 = new JSONObject();
				obj1.put("NotFound", "Invalid Credential !!!");
				obj1.put("Name", "");
				ja1.add(obj1);
			}
			return ja1.toString();

		} catch (Exception e) {
			log.error("Error processing login request: {}", e.getMessage(), e);
			throw e;
		}

	}
	// Seatle

	@RequestMapping(value = "/function-call", method = RequestMethod.GET)
	public String getExampleHTML(Model model) {
		return "Dashboard.html";
	}

}
