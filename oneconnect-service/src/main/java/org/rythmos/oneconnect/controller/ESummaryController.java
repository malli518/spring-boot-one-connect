/**
 * 
 */
package org.rythmos.oneconnect.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.rythmos.oneconnect.exception.OneConnectDBException;
import org.rythmos.oneconnect.json.request.ClientDetailJSONRequest;
import org.rythmos.oneconnect.json.request.ESJSONRequest;
import org.rythmos.oneconnect.json.request.ExecutiveSummaryJSONRequest;
import org.rythmos.oneconnect.json.request.PortfolioJSONRequest;
import org.rythmos.oneconnect.json.request.ProjectDetailJSONRequest;
import org.rythmos.oneconnect.json.request.RiskAndMitigationJSONRequest;
import org.rythmos.oneconnect.json.response.RAGStatusJSONResponse;
import org.rythmos.oneconnect.json.response.RiskAndMitigationJSONResponse;
import org.rythmos.oneconnect.response.ESummaryResponse;
import org.rythmos.oneconnect.response.RiskResponse;
import org.rythmos.oneconnect.service.ESummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Prasanth
 *
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/esummary")
public class ESummaryController {
	@Autowired
	private ESummaryService eSummaryService;

	@PostMapping("/saveESummary")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public ESummaryResponse<?> saveESummary(@RequestBody ESJSONRequest esJSONRequest) throws OneConnectDBException {
		return eSummaryService.saveESummary(esJSONRequest);
	}

	@PutMapping("/updateESummary")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public ESummaryResponse<?> updateESummary(@RequestBody ESJSONRequest esJSONRequest) throws OneConnectDBException {
		return eSummaryService.updateESummary(esJSONRequest);
	}

	@DeleteMapping("/deleteESummary")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public ESummaryResponse<?> deleteESummary(@RequestBody ESJSONRequest esJSONRequest) {
		return eSummaryService.deleteESummary(esJSONRequest);
	}

	@PostMapping("/getESummary")
	public ESummaryResponse<?> getESummary(@RequestBody ExecutiveSummaryJSONRequest executiveSummaryJSONRequest)
			throws OneConnectDBException {
		return eSummaryService.getESummary(executiveSummaryJSONRequest);
	}

	@GetMapping("/getAllRisks")
	public List<RiskAndMitigationJSONResponse> getAllRisks() {
		return eSummaryService.getAllRisks();
	}

	@PutMapping("updateRiskStatus")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public RiskResponse<?> updateRiskStatus(@RequestBody RiskAndMitigationJSONRequest riskAndMitigationJSONRequest) {
		return eSummaryService.updateRiskStatus(riskAndMitigationJSONRequest);
	}

	@PostMapping("/getRisksByClient")
	public List<RiskAndMitigationJSONResponse> getRisksByClient(
			@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {
		return eSummaryService.getRisksByClient(clientDetailJSONRequest);
	}

	@PostMapping("/getRisksByPortfolio")
	public List<RiskAndMitigationJSONResponse> getRisksByPortfolio(
			@RequestBody PortfolioJSONRequest portfolioJSONRequest) {
		return eSummaryService.getRisksByPortfolio(portfolioJSONRequest);
	}

	@PostMapping("/getRisksByProject")
	public List<RiskAndMitigationJSONResponse> getRisksByProject(
			@RequestBody ProjectDetailJSONRequest projectDetailJSONRequest) {
		return eSummaryService.getRisksByProject(projectDetailJSONRequest);
	}

	@GetMapping("/getAllRAGStatus")
	public RAGStatusJSONResponse getAllRAGStatus() {
		return eSummaryService.getAllRAGStatus();
	}

	@PostMapping("/getRAGStatusByClient")
	public RAGStatusJSONResponse getRAGStatusByClient(@RequestBody ClientDetailJSONRequest clientDetailJSONRequest) {
		return eSummaryService.getRAGStatusByClient(clientDetailJSONRequest);
	}

	@PostMapping("/getRAGStatusByPortfolio")
	public RAGStatusJSONResponse getRAGStatusByPortfolio(@RequestBody PortfolioJSONRequest portfolioJSONRequest) {
		return eSummaryService.getRAGStatusByPortfolio(portfolioJSONRequest);
	}

}
