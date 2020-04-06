package org.rythmos.oneconnect.controller;

import javax.annotation.security.RolesAllowed;

import org.rythmos.oneconnect.json.request.ClientDetailJSONRequest;
import org.rythmos.oneconnect.json.request.CountryJSONRequest;
import org.rythmos.oneconnect.json.request.EmailJSONRequest;
import org.rythmos.oneconnect.json.request.EmployeeJSONRequest;
import org.rythmos.oneconnect.json.request.IdJSONRequest;
import org.rythmos.oneconnect.json.request.PortfolioJSONRequest;
import org.rythmos.oneconnect.json.request.ProjectDetailJSONRequest;
import org.rythmos.oneconnect.json.request.StateJSONRequest;
import org.rythmos.oneconnect.response.CitiesResponse;
import org.rythmos.oneconnect.response.ClientDetailResponse;
import org.rythmos.oneconnect.response.CountriesResponse;
import org.rythmos.oneconnect.response.PortfolioResponse;
import org.rythmos.oneconnect.response.ProjectDetailResponse;
import org.rythmos.oneconnect.response.ProjectEndDateResponse;
import org.rythmos.oneconnect.response.ProjectRoleResponse;
import org.rythmos.oneconnect.response.ResourceResponse;
import org.rythmos.oneconnect.response.StatesResponse;
import org.rythmos.oneconnect.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Prasanth Kusumaraju
 *
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	public AdminService adminService;

	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@GetMapping("/getCountries")
	public CountriesResponse<?> getCountries() {
		return adminService.getCountries();
	}

	@PostMapping("/getStates")
	public StatesResponse<?> getStates(@RequestBody CountryJSONRequest countryJSONRequest) {
		return adminService.getStates(countryJSONRequest);
	}

	@PostMapping("/getCities")
	public CitiesResponse<?> getCities(@RequestBody StateJSONRequest stateJSONRequest) {
		return adminService.getCities(stateJSONRequest);
	}

	@PostMapping("/saveClient")
	@RolesAllowed("ROLE_ADMIN")
	public ClientDetailResponse<?> saveClient(@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {
		return adminService.saveClient(clientDetailJSONRequest);
	}

	@GetMapping("/getClientDetails")
	public ClientDetailResponse<?> getClientDetails() {
		return adminService.getClientDetails();
	}

	
	@GetMapping("/getAllClientDetails")
	@RolesAllowed({"ROLE_ADMIN","ROLE_EXECUTIVE"})
	public ClientDetailResponse<?> getAllClientDetails() {
		return adminService.getAllClientDetails();
	}

	@PutMapping("/updateClient")
	@RolesAllowed("ROLE_ADMIN")
	public ClientDetailResponse<?> updateClient(@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {
		return adminService.updateClient(clientDetailJSONRequest);
	}

	@DeleteMapping("/deleteClient")
	@RolesAllowed("ROLE_ADMIN")
	public ClientDetailResponse<?> deleteClient(@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {
		return adminService.deleteClient(clientDetailJSONRequest);
	}

	@PostMapping("/getPortfolios")
	public PortfolioResponse<?> getPortfolios(@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {
		return adminService.getPortfolios(clientDetailJSONRequest);
	}

	@PostMapping("/getProjectDetailsByPortfolio")
	public ProjectDetailResponse<?> getProjectDetailsByPortfolio(
			@RequestBody PortfolioJSONRequest portfolioJSONRequest) {
		return adminService.getProjectDetailsByPortfolio(portfolioJSONRequest);
	}

	@PostMapping("/savePortfolio")
	@RolesAllowed("ROLE_ADMIN")
	public PortfolioResponse<?> savePortfolio(@RequestBody PortfolioJSONRequest portfolioJSONRequest)
			throws IllegalAccessException {
		return adminService.savePortfolio(portfolioJSONRequest);
	}

	@GetMapping("/getPortfolioDetails")
	@RolesAllowed({"ROLE_ADMIN","ROLE_EXECUTIVE"})
	public PortfolioResponse<?> getPortfolioDetails() throws IllegalAccessException {
		return adminService.getPortfolioDetails();

	}

	@PutMapping("/updatePortfolio")
	@RolesAllowed("ROLE_ADMIN")
	public PortfolioResponse<?> updatePortfolio(@RequestBody PortfolioJSONRequest portfolioJSONRequest)
			throws IllegalAccessException {
		return adminService.updatePortfolio(portfolioJSONRequest);

	}

	@DeleteMapping("/deletePortfolio")
	@RolesAllowed("ROLE_ADMIN")
	public PortfolioResponse<?> deletePortfolio(@RequestBody PortfolioJSONRequest portfolioJSONRequest)
			throws IllegalAccessException {
		return adminService.deletePortfolio(portfolioJSONRequest);

	}

	@PostMapping("/saveProject")
	@RolesAllowed("ROLE_ADMIN")
	public ProjectDetailResponse<?> saveProject(@RequestBody ProjectDetailJSONRequest projectDetailJSONRequest) {
		return adminService.saveProject(projectDetailJSONRequest);
	}

	@GetMapping("/getProjectDetails")
	@RolesAllowed({"ROLE_ADMIN","ROLE_EXECUTIVE","ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER"})
	public ProjectDetailResponse<?> getProjectDetails() {
		return adminService.getProjectDetails();
	}

	@PutMapping("/updateProject")
	@RolesAllowed("ROLE_ADMIN")
	public ProjectDetailResponse<?> updateProject(@RequestBody ProjectDetailJSONRequest projectDetailJSONRequest) {
		return adminService.updateProject(projectDetailJSONRequest);
	}

	@DeleteMapping("/deleteProject")
	@RolesAllowed("ROLE_ADMIN")
	public ProjectDetailResponse<?> deleteProject(@RequestBody ProjectDetailJSONRequest projectDetailJSONRequest) {
		return adminService.deleteProject(projectDetailJSONRequest);
	}

	@GetMapping("/getAllProjectRoles")
	public ProjectRoleResponse<?> getAllProjectRoles() {
		return adminService.getAllProjectRoles();
	}

	@GetMapping("/getAllProjectResources")
	@RolesAllowed({"ROLE_ADMIN","ROLE_EXECUTIVE","ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER"})
	public ResourceResponse<?> getAllProjectResources() {
		return adminService.getAllProjectResources();
	}

	@PostMapping("/findAllResourcesByProject")
	public ResourceResponse<?> findAllResourcesByProject(@RequestBody IdJSONRequest idJSONRequest) {
		return adminService.findAllResourcesByProject(idJSONRequest);
	}

	@PostMapping("/addResource")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public ResourceResponse<?> addResource(@RequestBody EmployeeJSONRequest employeeJSONRequest) {
		return adminService.addResource(employeeJSONRequest);
	}

	@PutMapping("/updateResource")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public ResourceResponse<?> updateResource(@RequestBody EmployeeJSONRequest employeeJSONRequest) {
		return adminService.updateResource(employeeJSONRequest);
	}

	@DeleteMapping("/deleteResource")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER" })
	public ResourceResponse<?> deleteResource(@RequestBody EmployeeJSONRequest employeeJSONRequest) {
		return adminService.deleteResource(employeeJSONRequest);
	}

	@PostMapping("/addProjestsToExistingResource")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public ResourceResponse<?> addProjestsToExistingResource(@RequestBody EmployeeJSONRequest employeeJSONRequest) {
		return adminService.addProjestsToExistingResource(employeeJSONRequest);
	}

	@PutMapping("/updateProjectForExistingResource")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public ResourceResponse<?> updateProjectForExistingResource(@RequestBody EmployeeJSONRequest employeeJSONRequest) {
		return adminService.updateProjectForExistingResource(employeeJSONRequest);
	}

	@DeleteMapping("/deleteEmployeeProject")
	@RolesAllowed({ "ROLE_MANAGER", "ROLE_ADMIN" })
	public ResourceResponse<?> deleteEmployesProject(@RequestBody EmployeeJSONRequest employeeJSONRequest) {
		return adminService.deleteEmployesProject(employeeJSONRequest);
	}

	@PostMapping("/sendEmail")
	public ResourceResponse<?> sendEmail(@RequestBody EmailJSONRequest emailJSONRequest) {
		return adminService.sendEmail(emailJSONRequest);
	}
	
	@GetMapping("/getProjectEndDateAlert")
	public ProjectEndDateResponse<?> getProjectEndDateAlert(){
		return adminService.getProjectEndDateAlert();
	}

}
