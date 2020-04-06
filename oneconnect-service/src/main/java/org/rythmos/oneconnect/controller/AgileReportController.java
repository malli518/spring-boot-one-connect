package org.rythmos.oneconnect.controller;

import javax.annotation.security.RolesAllowed;

import org.rythmos.oneconnect.exception.OneConnectDBException;
import org.rythmos.oneconnect.json.request.AgileMaturityJSONRequest;
import org.rythmos.oneconnect.response.AgileReportResponse;
import org.rythmos.oneconnect.service.AgileMaturityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/agileReport")
public class AgileReportController {

	@Autowired
	private AgileMaturityService agileMatgurityService;

	@PostMapping("/saveAgileReport")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public AgileReportResponse<?> saveAgileReport(@RequestBody AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws NumberFormatException, OneConnectDBException {
		return agileMatgurityService.saveAgileReport(agileMaturityJSONRequest);
	}

	@PostMapping("/findAgileReport")
	public AgileReportResponse<?> findAgileReport(@RequestBody AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws OneConnectDBException {
		return agileMatgurityService.findAgileReport(agileMaturityJSONRequest);
	}

	@PostMapping("/getAgileReport")
	public AgileReportResponse<?> getAgileReport(@RequestBody AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws OneConnectDBException {
		return agileMatgurityService.getAgileReport(agileMaturityJSONRequest);
	}

	@PutMapping("/updateAgileReport")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public AgileReportResponse<?> updateAgileReport(@RequestBody AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws NumberFormatException, OneConnectDBException {
		return agileMatgurityService.updateAgileReport(agileMaturityJSONRequest);
	}

	@DeleteMapping("/deleteAgileReport")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public AgileReportResponse<?> deleteAgileReport(@RequestBody AgileMaturityJSONRequest agileMaturityJSONRequest)
			throws OneConnectDBException {
		return agileMatgurityService.deleteAgileReport(agileMaturityJSONRequest);
	}

}
