/**
 * 
 */
package org.rythmos.oneconnect.controller;

import javax.annotation.security.RolesAllowed;

import org.rythmos.oneconnect.exception.OneConnectDBException;
import org.rythmos.oneconnect.json.request.ClientHealthJSONRequest;
import org.rythmos.oneconnect.json.request.ClientHealthReportJSONRequest;
import org.rythmos.oneconnect.response.ClientHealthReportResponse;
import org.rythmos.oneconnect.service.ClientHealthReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahalakshmi
 *
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/healthReport")
public class ClientHealthReportController {

	@Autowired
	private ClientHealthReportService clientHealthReportService;

	@PostMapping("/saveClientHealthReport")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public ClientHealthReportResponse<?> saveClientHealthReport(
			@RequestBody ClientHealthReportJSONRequest clientHealthReportJSONRequest) throws OneConnectDBException {
		return clientHealthReportService.saveClientHealthReport(clientHealthReportJSONRequest);
	}

	@PutMapping("/updateClientHealthReport")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public ClientHealthReportResponse<?> updateClientHealthReport(
			@RequestBody ClientHealthReportJSONRequest clientHealthReportJSONRequest) throws OneConnectDBException {
		return clientHealthReportService.updateClientHealthReport(clientHealthReportJSONRequest);
	}

	@PostMapping("/getClientHealthReport")
	public ClientHealthReportResponse<?> getClientHealthReport(
			@RequestBody ClientHealthJSONRequest clientHealthJSONRequest) throws OneConnectDBException {
		return clientHealthReportService.getClientHealthReport(clientHealthJSONRequest);
	}

}
