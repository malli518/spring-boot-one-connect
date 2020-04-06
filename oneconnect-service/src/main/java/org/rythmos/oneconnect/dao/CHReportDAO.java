package org.rythmos.oneconnect.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.rythmos.oneconnect.bean.ClientHealthJSONRequestBean;
import org.rythmos.oneconnect.bean.ClientHealthReportBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.entity.ClientHealthReport;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.json.response.ClientHealthReportJSONResponse;
import org.rythmos.oneconnect.json.response.CommentsJSONResponse;
import org.rythmos.oneconnect.json.response.FeedbackJSONResponse;
import org.rythmos.oneconnect.json.response.HealthIndicatorJSONResponse;
import org.rythmos.oneconnect.json.response.HealthIndicatorPointsJSONResponse;
import org.rythmos.oneconnect.repository.ClientHealthReportsRepository;
import org.rythmos.oneconnect.repository.HealthIndicatorTypeRepository;
import org.rythmos.oneconnect.response.ClientHealthReportResponse;
import org.rythmos.oneconnect.util.OneConnectDAOUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class CHReportDAO {

	public static Logger logger = LoggerFactory.getLogger(CHReportDAO.class);

	@Autowired
	private HealthIndicatorTypeRepository healthIndicatorTypeRepository;

	@Autowired
	private ClientHealthReportsRepository clientHealthReportsRepository;

	@Autowired
	private BaseResponse baseResponse;

	@Autowired
	private OneConnectDAOUtility oneConnectDAOUtility;

	public ClientHealthReportResponse<?> saveClientHealthReport(
			@RequestBody ClientHealthReportBean clientHealthReportBean) {

		logger.info("clientHealthReportBean is::{}", clientHealthReportBean);
		try {
			if (clientHealthReportBean != null) {
				logger.info(" Null check Pass :: ");
				ClientHealthReport clientHealthReport = convertToEntity(clientHealthReportBean);
				clientHealthReport.setHealthIndicator(healthIndicatorTypeRepository
						.findByHealthIndicatorType(clientHealthReportBean.getHealthIndicatorType().getType()));
				if (null != clientHealthReportsRepository.save(clientHealthReport)) {
					logger.info(" Client Health Report added successfully ");
					baseResponse.setMessage(" Client Health Report added successfully");
					return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to add Client Health Report! {}", clientHealthReportBean);
					baseResponse.setMessage("Unable to add Client Health Report!");
					return new ClientHealthReportResponse<BaseResponse>(baseResponse, null,
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Client Health Report not found! {}", clientHealthReportBean);
				baseResponse.setMessage("Client Health Report not found!");
				return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.info("Unable to add Client Health Report! {}", clientHealthReportBean);
			baseResponse.setMessage("Unable to add Client Health Report!");
			return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ClientHealthReport convertToEntity(ClientHealthReportBean clientHealthReportBean) {
		ClientHealthReport clientHealthReport = oneConnectDAOUtility.modelMapper.map(clientHealthReportBean,
				ClientHealthReport.class);
		return clientHealthReport;
	}

	public ClientHealthReportResponse<?> updateClientHealthReport(
			@RequestBody ClientHealthReportBean clientHealthReportBean) {

		logger.info("clientHealthReportBean is::{}", clientHealthReportBean);
		try {
			if (clientHealthReportBean != null) {
				logger.info(" Null check Pass :: ");
				ClientHealthReport clientHealthReport = convertToEntity(clientHealthReportBean);
				if (null != clientHealthReportsRepository.save(clientHealthReport)) {
					logger.info(" Client Health Report updated successfully ");
					baseResponse.setMessage("Client Health Report updated Successfully");
					return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);
				} else {
					logger.info("Unable to update Client Health Report! {}", clientHealthReportBean);
					baseResponse.setMessage("Unable to update Client Health Report!");
					return new ClientHealthReportResponse<BaseResponse>(baseResponse, null,
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.info("Client Health Report not found! {}", clientHealthReportBean);
				baseResponse.setMessage("Client Health Report not Found!");
				return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.info("Unable to update Client Health Report! {}", clientHealthReportBean);
			baseResponse.setMessage("Unable to update Client Health Report!");
			return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ClientHealthReportResponse<?> getClientHealthReport(
			@RequestBody ClientHealthJSONRequestBean clientHealthJSONRequestBean) {
		logger.info("clientHealthJSONRequestBean is::{}", clientHealthJSONRequestBean);

		if (clientHealthJSONRequestBean != null) {
			ClientHealthReportJSONResponse clientHealthReportJSONResponse = new ClientHealthReportJSONResponse();
			List<FeedbackJSONResponse> feedbackResponseList = new ArrayList<FeedbackJSONResponse>();

			if (clientHealthJSONRequestBean.getClientId() != null
					&& clientHealthJSONRequestBean.getPortfolioId() != null
					&& clientHealthJSONRequestBean.getProjectId() != null
					&& clientHealthJSONRequestBean.getMonthYear() != null) {
				List<ClientHealthReport> clienthealthReportList = clientHealthReportsRepository
						.findHealthReportByProject(clientHealthJSONRequestBean.getClientId(),
								clientHealthJSONRequestBean.getPortfolioId(),
								clientHealthJSONRequestBean.getProjectId(),
								new java.sql.Date(clientHealthJSONRequestBean.getMonthYear().getTime()));

				if (clienthealthReportList != null && clienthealthReportList.size() > 0) {
					for (ClientHealthReport healthReport : clienthealthReportList) {
						FeedbackJSONResponse feedbackJSONResponse = new FeedbackJSONResponse();
						CommentsJSONResponse commentsJSONResponse = new CommentsJSONResponse();

						logger.info("Health Indicator :: " + healthReport.getHealthIndicator());

						feedbackJSONResponse.setHealthIndicator(healthReport.getHealthIndicator().getValue());
						feedbackJSONResponse.setRating(String.valueOf(healthReport.getRating()));
						commentsJSONResponse.setStakeholderFeedback(healthReport.getFeedback());
						commentsJSONResponse.setRythmosAction(healthReport.getAction());
						feedbackJSONResponse.setComments(commentsJSONResponse);

						feedbackResponseList.add(feedbackJSONResponse);
					}
					clientHealthReportJSONResponse.setFeedbackData(feedbackResponseList);

				} else {

					logger.info("Client Health Report not found!");
					baseResponse.setMessage("Client Health Report not found!");
					return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.NOT_FOUND);
				}

				List<HealthIndicatorJSONResponse> radarChartDataList = new ArrayList<HealthIndicatorJSONResponse>();
				List<HealthIndicatorJSONResponse> healthIndicatorDataList = new ArrayList<HealthIndicatorJSONResponse>();
				HealthIndicatorJSONResponse radarChartData = null;
				Calendar calender = Calendar.getInstance();
				calender.setTime(clientHealthJSONRequestBean.getMonthYear());
				HealthIndicatorPointsJSONResponse healthIndicatorValuesJSONResponse;

				for (int index = 0; index < 3; index++) {

					String monthLabel = calender.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);

					radarChartData = new HealthIndicatorJSONResponse();
					radarChartData.setMonth(monthLabel);

					healthIndicatorValuesJSONResponse = new HealthIndicatorPointsJSONResponse();
					logger.info("Radar Data inputs Date :: " + " :: " + calender.getTime());
					List<ClientHealthReport> healthReportsList = clientHealthReportsRepository
							.findHealthReportByProject(clientHealthJSONRequestBean.getClientId(),
									clientHealthJSONRequestBean.getPortfolioId(),
									clientHealthJSONRequestBean.getProjectId(),
									new java.sql.Date(calender.getTime().getTime()));

					System.out.println("---size--->" + healthReportsList.size());

					if (null != healthReportsList) {

						for (ClientHealthReport healthReportEntity : healthReportsList) {

							String health_indicator = String.valueOf(healthReportEntity.getHealthIndicator().getType());
							if (OneConnectConstants.HI_VALUE_DELIVARY_VALUE.equals(health_indicator)) {
								healthIndicatorValuesJSONResponse
										.setDeliveryValue(String.valueOf(healthReportEntity.getRating()));
							}
							if (OneConnectConstants.HI_VALUE_CODE_HEALTH.equals(health_indicator)) {
								healthIndicatorValuesJSONResponse
										.setHealthOfCodebase(String.valueOf(healthReportEntity.getRating()));
							}
							if (OneConnectConstants.HI_VALUE_SPEED.equals(health_indicator)) {
								healthIndicatorValuesJSONResponse
										.setSpeed(String.valueOf(healthReportEntity.getRating()));

							}
							if (OneConnectConstants.HI_VALUE_CURRENT_PROCESS.equals(health_indicator)) {
								healthIndicatorValuesJSONResponse
										.setCurrentProcess(String.valueOf(healthReportEntity.getRating()));
							}
							if (OneConnectConstants.HI_VALUE_COMM_N_REPORT.equals(health_indicator)) {
								healthIndicatorValuesJSONResponse
										.setCommunicationAndReporting(String.valueOf(healthReportEntity.getRating()));

							}

						}
					}
					radarChartData.setHealthIndicatorPoints(healthIndicatorValuesJSONResponse);
					radarChartDataList.add(radarChartData);
					calender.add(Calendar.MONTH, -1);

				}

				for (HealthIndicatorJSONResponse healthIndicatorJSONResponse : radarChartDataList) {
					if (null == healthIndicatorJSONResponse.getHealthIndicatorPoints().getCommunicationAndReporting()) {
						healthIndicatorJSONResponse.getHealthIndicatorPoints()
								.setCommunicationAndReporting(OneConnectConstants.NUMBER_ZERO);
					}
					if (null == healthIndicatorJSONResponse.getHealthIndicatorPoints().getCurrentProcess()) {
						healthIndicatorJSONResponse.getHealthIndicatorPoints()
								.setCurrentProcess(OneConnectConstants.NUMBER_ZERO);
					}
					if (null == healthIndicatorJSONResponse.getHealthIndicatorPoints().getDeliveryValue()) {
						healthIndicatorJSONResponse.getHealthIndicatorPoints()
								.setDeliveryValue(OneConnectConstants.NUMBER_ZERO);
					}
					if (null == healthIndicatorJSONResponse.getHealthIndicatorPoints().getHealthOfCodebase()) {
						healthIndicatorJSONResponse.getHealthIndicatorPoints()
								.setHealthOfCodebase(OneConnectConstants.NUMBER_ZERO);
					}
					if (null == healthIndicatorJSONResponse.getHealthIndicatorPoints().getSpeed()) {
						healthIndicatorJSONResponse.getHealthIndicatorPoints()
								.setSpeed(OneConnectConstants.NUMBER_ZERO);
					}
					healthIndicatorDataList.add(healthIndicatorJSONResponse);

				}

				clientHealthReportJSONResponse.setHealthIndicatorData(radarChartDataList);
				logger.info("Exited :: ClientHealthJSONRequest");
				return new ClientHealthReportResponse<>(clientHealthReportJSONResponse, null, HttpStatus.OK);

			}

		}
		logger.info("Invalid Request");
		baseResponse.setMessage("Invalid Request ");
		return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);
	}
}
