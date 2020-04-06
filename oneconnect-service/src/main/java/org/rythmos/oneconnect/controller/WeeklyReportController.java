package org.rythmos.oneconnect.controller;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;

import org.rythmos.oneconnect.exception.OneConnectApplicationException;
import org.rythmos.oneconnect.json.request.IdJSONRequest;
import org.rythmos.oneconnect.json.request.WRApprovalJSONRequest;
import org.rythmos.oneconnect.json.request.WRImageJSONRequest;
import org.rythmos.oneconnect.json.request.WRJSONRequest;
import org.rythmos.oneconnect.json.request.WeeklyReportJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.response.WeeklyReportResponse;
import org.rythmos.oneconnect.service.WeeklyReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mahalakshmi
 *
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/weeklyReport")
public class WeeklyReportController {

	public static Logger logger = LoggerFactory.getLogger(WeeklyReportController.class);

	@Autowired
	private WeeklyReportService weeklyReportService;

	@Autowired
	private BaseResponse baseResponse;

	@PostMapping("/saveWeeklyReport")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public WeeklyReportResponse<?> saveWeeklyReport(@RequestBody WRJSONRequest wrJSONRequest) {
		try {
			return weeklyReportService.saveWeeklyReport(wrJSONRequest);
		} catch (OneConnectApplicationException oe) {
			baseResponse.setMessage(oe.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateWeeklyReport")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public WeeklyReportResponse<?> updateWeeklyReport(@RequestBody WRJSONRequest wrJSONRequest) {
		try {
			return weeklyReportService.updateWeeklyReport(wrJSONRequest);
		} catch (OneConnectApplicationException oe) {
			baseResponse.setMessage(oe.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteWeeklyReport")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public WeeklyReportResponse<?> deleteWeeklyReport(@RequestBody WRJSONRequest wrJSONRequest) {
		try {
			return weeklyReportService.deleteWeeklyReport(wrJSONRequest);
		} catch (OneConnectApplicationException oe) {
			baseResponse.setMessage(oe.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/getWeeklyReport")
	public WeeklyReportResponse<?> getWeeklyReport(@RequestBody WeeklyReportJSONRequest weeklyReportJSONRequest) {
		try {
			return weeklyReportService.getWeeklyReport(weeklyReportJSONRequest);
		} catch (OneConnectApplicationException oe) {
			baseResponse.setMessage(oe.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/getWeeklyReportImages")
	public WeeklyReportResponse<?> getWeeklyReportImages(@RequestParam("clientId") String clientId,
			@RequestParam("portfolioId") String portfolioId, @RequestParam("projectId") String projectId,
			@RequestParam("startDate") String startDate) {
		try {
			logger.info("Got Get All images Hit");
			WeeklyReportJSONRequest weeklyReportJSONRequest = new WeeklyReportJSONRequest();
			weeklyReportJSONRequest.setClientId(Long.parseLong(clientId));
			weeklyReportJSONRequest.setPortfolioId(Long.parseLong(portfolioId));
			weeklyReportJSONRequest.setProjectId(Long.parseLong(projectId));
			weeklyReportJSONRequest.setStartDate(startDate);
			return weeklyReportService.getWeeklyReportImages(weeklyReportJSONRequest);
		} catch (OneConnectApplicationException oe) {
			baseResponse.setMessage(oe.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/uploadWeeklyReportImage")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public WeeklyReportResponse<?> uploadWeeklyReportImage(@RequestParam("file") MultipartFile file,
			@RequestParam("clientId") String clientId, @RequestParam("portfolioId") String portfolioId,
			@RequestParam("projectId") String projectId, @RequestParam("startDate") String startDate)
			throws IOException {
		logger.info("multipart file is ::{}", file);
		try {
			return weeklyReportService.uploadWeeklyReportImage(file, clientId, portfolioId, projectId, startDate);
		} catch (OneConnectApplicationException oe) {
			baseResponse.setMessage(oe.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteWeeklyReportImage")
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_LEAD", "ROLE_SCRUMMASTER" })
	public WeeklyReportResponse<?> deleteWeeklyReportImage(@RequestBody WRImageJSONRequest wRImageJSONRequest) {
		try {
			logger.info("DeleteWeeklyReportImage Request ::{}", wRImageJSONRequest.getId());
			IdJSONRequest idJSONRequest = new IdJSONRequest();
			idJSONRequest.setId("" + wRImageJSONRequest.getId() + "");
			return weeklyReportService.deleteWeeklyReportImage(idJSONRequest);

		} catch (OneConnectApplicationException oe) {
			baseResponse.setMessage(oe.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/saveApprovalStatus")
	@RolesAllowed({ "ROLE_DELIVERYMANAGER", "ROLE_ADMIN" })
	public WeeklyReportResponse<?> saveApprovalStatus(@RequestBody WRApprovalJSONRequest wrApprovalJSONRequest) {
		try {
			return weeklyReportService.saveApprovalStatus(wrApprovalJSONRequest);

		} catch (OneConnectApplicationException oe) {
			baseResponse.setMessage(oe.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/getWeeklyReportApprovalStatus")
	public WeeklyReportResponse<?> getWeeklyReportApprovalStatus(
			@RequestBody WeeklyReportJSONRequest weeklyReportJSONRequest) {
		try {
			return weeklyReportService.getWeeklyReportApprovalStatus(weeklyReportJSONRequest);
		} catch (OneConnectApplicationException oe) {
			baseResponse.setMessage(oe.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			return new WeeklyReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

}
