package org.rythmos.oneconnect.controller;

import org.rythmos.oneconnect.json.request.ReportDateJSONRequest;
import org.rythmos.oneconnect.response.ReportDateResponse;
import org.rythmos.oneconnect.service.ReportDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/date")
public class ReportDateController {
	
	@Autowired
	private ReportDateService dateService;

	@GetMapping("/getReportDates")
	public ReportDateResponse<?> getReportDates(
			@RequestBody ReportDateJSONRequest dateReportJSONRequest) {
				return dateService.getReportDates(dateReportJSONRequest);

	}
}
