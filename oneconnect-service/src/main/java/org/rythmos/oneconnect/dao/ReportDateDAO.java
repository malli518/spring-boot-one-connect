package org.rythmos.oneconnect.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.rythmos.oneconnect.bean.ReportDateJSONBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.AgileAssessmentRepository;
import org.rythmos.oneconnect.repository.ClientDetailsRepository;
import org.rythmos.oneconnect.repository.ClientHealthReportsRepository;
import org.rythmos.oneconnect.repository.ESummaryRepository;
import org.rythmos.oneconnect.repository.PortfolioRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.rythmos.oneconnect.repository.WeeklyReportRepository;
import org.rythmos.oneconnect.response.ReportDateResponse;
import org.rythmos.oneconnect.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class ReportDateDAO {
	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private ProjectDetailRepository projectDetailRepository;

	@Autowired
	private ESummaryRepository eSummaryRepository;

	@Autowired
	private ClientHealthReportsRepository clientHealthReportsRepository;

	@Autowired
	private AgileAssessmentRepository agileAssessmentRepository;

	@Autowired
	private WeeklyReportRepository weeklyReportRepository;

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private DateUtil dateUtil;

	public ReportDateResponse<?> getReportDates(@RequestBody ReportDateJSONBean reportDateJSONBean) {
		Long clientId = clientDetailsRepository.findClientId(reportDateJSONBean.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(reportDateJSONBean.getPortfolio().getId()).getId();
		Long projectId = projectDetailRepository.findProjectDetailId(reportDateJSONBean.getProject().getId()).getId();

		switch (reportDateJSONBean.getReportType()) {
		case OneConnectConstants.EXECUTIVE_SUMMARY_REPORT:
			List<Date> eSummaryReportDates = eSummaryRepository.findReportDates(clientId, portfolioId, projectId);
			List<String> eSummaryReportDatesList = new ArrayList<String>();
			for (Date date : eSummaryReportDates) {
				String eSummaryDate = dateUtil.getFormattedDateString(date, OneConnectConstants.DEFAULT_DATE_FORMAT);
				eSummaryReportDatesList.add(eSummaryDate);
			}

			return new ReportDateResponse<>(eSummaryReportDatesList, null, HttpStatus.OK);
		case OneConnectConstants.CLIENT_HEALTH_REPORT:
			List<Date> chrReportDates = clientHealthReportsRepository.findReportDates(clientId, portfolioId, projectId);
			List<String> chrReportDatesList = new ArrayList<String>();
			for (Date date : chrReportDates) {
				String chrReportDate = dateUtil.getFormattedDateString(date, OneConnectConstants.DEFAULT_DATE_FORMAT);
				chrReportDatesList.add(chrReportDate);
			}

			return new ReportDateResponse<>(chrReportDatesList, null, HttpStatus.OK);
		case OneConnectConstants.AGILE_MATURITY_ASSESSMENT:
			List<Date> amaReportDates = agileAssessmentRepository.findReportDates(clientId, portfolioId, projectId);
			List<String> amaReportDatesList = new ArrayList<String>();
			for (Date date : amaReportDates) {
				String amaReportDate = dateUtil.getFormattedDateString(date, OneConnectConstants.DEFAULT_DATE_FORMAT);
				amaReportDatesList.add(amaReportDate);
			}

			return new ReportDateResponse<>(amaReportDatesList, null, HttpStatus.OK);

		case OneConnectConstants.WEEKLY_SUMMARY_REPORT:
			List<Date> weeklyReportDates = weeklyReportRepository.findReportDates(clientId, portfolioId, projectId);
			List<String> weeklyReportDatesList = new ArrayList<String>();
			for (Date date : weeklyReportDates) {
				String weeklyReportDate = dateUtil.getFormattedDateString(date,
						OneConnectConstants.DEFAULT_DATE_FORMAT);
				weeklyReportDatesList.add(weeklyReportDate);
			}

			return new ReportDateResponse<>(weeklyReportDatesList, null, HttpStatus.OK);

		default:
			break;
		}
		baseResponse.setMessage("No report dates found");
		return new ReportDateResponse<>(baseResponse, null, HttpStatus.NOT_FOUND);

	}
}
