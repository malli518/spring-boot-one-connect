package org.rythmos.oneconnect.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.rythmos.oneconnect.bean.ClientHealthJSONRequestBean;
import org.rythmos.oneconnect.bean.ClientHealthReportBean;
import org.rythmos.oneconnect.bean.HealthIndicatorBean;
import org.rythmos.oneconnect.bean.HealthIndicatorTypeBean;
import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.rythmos.oneconnect.dao.CHReportDAO;
import org.rythmos.oneconnect.entity.ClientHealthReport;
import org.rythmos.oneconnect.entity.HealthIndicatorType;
import org.rythmos.oneconnect.exception.OneConnectDBException;
import org.rythmos.oneconnect.json.request.ClientHealthJSONRequest;
import org.rythmos.oneconnect.json.request.ClientHealthReportJSONRequest;
import org.rythmos.oneconnect.json.response.BaseResponse;
import org.rythmos.oneconnect.repository.ClientDetailsRepository;
import org.rythmos.oneconnect.repository.ClientHealthReportsRepository;
import org.rythmos.oneconnect.repository.HealthIndicatorTypeRepository;
import org.rythmos.oneconnect.repository.HealthReportsRepository;
import org.rythmos.oneconnect.repository.PortfolioRepository;
import org.rythmos.oneconnect.repository.ProjectDetailRepository;
import org.rythmos.oneconnect.response.ClientHealthReportResponse;
import org.rythmos.oneconnect.util.DateUtil;
import org.rythmos.oneconnect.util.OneConnectDAOUtility;
import org.rythmos.oneconnect.util.OneConnectServiceUtility;
import org.rythmos.oneconnect.util.OneConnectUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ClientHealthReportService {

	private static Logger logger = LoggerFactory.getLogger(ClientHealthReportService.class);

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	ProjectDetailRepository projectDetailRepository;

	@Autowired
	HealthReportsRepository healthReportsRepository;

	@Autowired
	private CHReportDAO chReportDAO;

	@Autowired
	private HealthIndicatorTypeRepository healthIndicatorTypeRepository;

	@Autowired
	private ClientHealthReportsRepository clientHealthReportsRepository;

	@Autowired
	private OneConnectServiceUtility oneConnectServiceUtility;

	@Autowired
	private OneConnectDAOUtility oneConnectDAOUtility;

	@Autowired
	private BaseResponse baseResponse;

	public ClientHealthReportResponse<?> saveClientHealthReport(
			@RequestBody ClientHealthReportJSONRequest clientHealthReportJSONRequest) throws OneConnectDBException {
		logger.info("Save Request for clientHealthReport is::" + clientHealthReportJSONRequest);
		int count = 0;
		Long clientId = clientDetailsRepository.findClientId(clientHealthReportJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(clientHealthReportJSONRequest.getPortfolio().getId())
				.getId();
		Long projectId = projectDetailRepository.findProjectDetailId(clientHealthReportJSONRequest.getProject().getId())
				.getId();
		DateUtil dateUtil = new DateUtil();
		Date date = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				clientHealthReportJSONRequest.getMonthYear());
		logger.info("Date :: " + date);

		if (clientId != null && portfolioId != null && projectId != null && date != null) {
			logger.info("Null check is passed for client Health Reports" + projectId);
			if (oneConnectServiceUtility.checkProjectScope(projectId, date)) {
				if (clientHealthReportJSONRequest != null
						&& !OneConnectUtility.isNull(clientHealthReportJSONRequest.getHealthIndicators())) {

					ClientHealthReportBean clientHealthReportBean = new ClientHealthReportBean();

					clientHealthReportBean.setClientDetail(clientHealthReportJSONRequest.getClient());
					clientHealthReportBean.setPortfolio(clientHealthReportJSONRequest.getPortfolio());
					clientHealthReportBean.setProjectDetail(clientHealthReportJSONRequest.getProject());
					clientHealthReportBean.setDate(date);

					List<HealthIndicatorBean> healthIndicatorList = clientHealthReportJSONRequest.getHealthIndicators();

					for (HealthIndicatorBean healthIndicator : healthIndicatorList) {

						HealthIndicatorBean healthIndicatorBean = new HealthIndicatorBean();
						healthIndicatorBean.setType(healthIndicator.getType());
						healthIndicatorBean.setRating(healthIndicator.getRating());
						healthIndicatorBean.setFeedback(healthIndicator.getFeedback());
						healthIndicatorBean.setAction(healthIndicator.getAction());

						HealthIndicatorType healthIndicatorType = healthIndicatorTypeRepository
								.findByHealthIndicatorType(healthIndicatorBean.getType());
						HealthIndicatorTypeBean healthIndicatorTypeBean = convertToBean(healthIndicatorType);
						clientHealthReportBean.setHealthIndicatorType(healthIndicatorTypeBean);
						clientHealthReportBean.setRating(healthIndicatorBean.getRating());
						clientHealthReportBean.setFeedback(healthIndicatorBean.getFeedback());
						clientHealthReportBean.setAction(healthIndicatorBean.getAction());
						count++;
						chReportDAO.saveClientHealthReport(clientHealthReportBean);

					}
					try {
						if (count == 5) {
							logger.info("Client Health Report added successfully", clientHealthReportBean);
							baseResponse.setMessage("Client Health Report added successfully");
							return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

						} else {
							logger.info("Unable to add Client Health Report!", clientHealthReportBean);
							return new ClientHealthReportResponse<String>("Unable to add Client Health Report!", null,
									HttpStatus.INTERNAL_SERVER_ERROR);
						}
					} catch (Exception ex) {
						logger.info("Unable to add Client Health Report!", clientHealthReportBean);
						return new ClientHealthReportResponse<String>("Unable to add Client Health Report!", null,
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
			} else {
				logger.info("Client Health Report date is out of Project Scope!");
				baseResponse.setMessage("Client Health Report date is out of Project Scope!");
				return new ClientHealthReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}
		}
		logger.info("Invalid Request!::{}", clientHealthReportJSONRequest);
		baseResponse.setMessage("Invalid Request!");
		return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.BAD_REQUEST);

	}

	public ClientHealthReportResponse<?> updateClientHealthReport(
			@RequestBody ClientHealthReportJSONRequest clientHealthReportJSONRequest) throws OneConnectDBException {
		logger.info("Update Request for clientHealthReport is::" + clientHealthReportJSONRequest);
		Long clientId = clientDetailsRepository.findClientId(clientHealthReportJSONRequest.getClient().getId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(clientHealthReportJSONRequest.getPortfolio().getId())
				.getId();
		Long projectId = projectDetailRepository.findProjectDetailId(clientHealthReportJSONRequest.getProject().getId())
				.getId();
		DateUtil dateUtil = new DateUtil();
		Date date = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				clientHealthReportJSONRequest.getMonthYear());
		logger.info("Date :: " + date);
		if (clientId != null && portfolioId != null && projectId != null && date != null) {

			logger.info("Null check is passed for client Health Reports" + projectId);
			if (oneConnectServiceUtility.checkProjectScope(projectId, date)) {

				List<ClientHealthReport> clienthealthReportList = clientHealthReportsRepository
						.findHealthReportByProject(clientId, portfolioId, projectId, new java.sql.Date(date.getTime()));
				if (clienthealthReportList != null) {

					List<ClientHealthReportBean> clientHealthReportBeanList = new ArrayList<ClientHealthReportBean>();

					for (ClientHealthReport clientHealthReport : clienthealthReportList) {
						ClientHealthReportBean clientHealthReportBean = convertToBean(clientHealthReport);
						clientHealthReportBeanList.add(clientHealthReportBean);
					}

					List<HealthIndicatorBean> healthIndicatorList = clientHealthReportJSONRequest.getHealthIndicators();

					for (HealthIndicatorBean healthIndicator : healthIndicatorList) {

						HealthIndicatorBean healthIndicatorBean = new HealthIndicatorBean();
						healthIndicatorBean.setType(healthIndicator.getType());
						healthIndicatorBean.setRating(healthIndicator.getRating());
						healthIndicatorBean.setFeedback(healthIndicator.getFeedback());
						healthIndicatorBean.setAction(healthIndicator.getAction());

						ClientHealthReportBean clientHealthBean = clientHealthReportBeanList.stream()
								.filter(clientHealthReportBean -> (healthIndicator.getType())
										.equals(clientHealthReportBean.getHealthIndicatorType().getType()))
								.findAny().orElse(null);

						logger.info("clientHealthReportBeanList is ::{}", clientHealthReportBeanList);
						logger.info("clientHealthReportBeanUpdated is::{}", clientHealthBean);

						clientHealthBean.setRating(healthIndicatorBean.getRating());
						clientHealthBean.setFeedback(healthIndicatorBean.getFeedback());
						clientHealthBean.setAction(healthIndicatorBean.getAction());
						clientHealthBean.setLastModifiedDate(new Date());

						chReportDAO.updateClientHealthReport(clientHealthBean);

					}
				}
				logger.info("Client Health Report updated successfully");
				baseResponse.setMessage("Client Health Report updated successfully");
				return new ClientHealthReportResponse<BaseResponse>(baseResponse, null, HttpStatus.OK);

			} else {
				logger.info("Client Health Report date is out of Project Scope!");
				baseResponse.setMessage("Client Health Report date is out of Project Scope!");
				return new ClientHealthReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}

		}

		logger.info("Invalid Request!::{}", clientHealthReportJSONRequest);
		baseResponse.setMessage("Invalid Request!");
		return new ClientHealthReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);

	}

	public ClientHealthReportResponse<?> getClientHealthReport(
			@RequestBody ClientHealthJSONRequest clientHealthJSONRequest) throws OneConnectDBException {
		Long clientId = clientDetailsRepository.findClientId(clientHealthJSONRequest.getClientId()).getId();
		Long portfolioId = portfolioRepository.findPortfolioById(clientHealthJSONRequest.getPortfolioId()).getId();
		Long projectId = projectDetailRepository.findProjectDetailId(clientHealthJSONRequest.getProjectId()).getId();
		DateUtil dateUtil = new DateUtil();
		Date date = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT,
				clientHealthJSONRequest.getMonthYear());
		logger.info("Date :: " + date);

		if (clientId != null && portfolioId != null && projectId != null && date != null) {

			logger.info("Null check is passed for client Health Reports" + projectId);
			if (oneConnectServiceUtility.checkProjectScope(projectId, date)) {

				ClientHealthJSONRequestBean clientHealthJSONRequestBean = new ClientHealthJSONRequestBean();
				clientHealthJSONRequestBean.setClientId(clientId);
				clientHealthJSONRequestBean.setPortfolioId(portfolioId);
				clientHealthJSONRequestBean.setProjectId(projectId);
				clientHealthJSONRequestBean.setMonthYear(date);
				return chReportDAO.getClientHealthReport(clientHealthJSONRequestBean);
			} else {
				logger.info("Client Health Report date is out of Project Scope!");
				baseResponse.setMessage("Client Health Report date is out of Project Scope!");
				return new ClientHealthReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
			}

		} else {
			logger.info("Invalid Request!::{}", clientHealthJSONRequest);
			baseResponse.setMessage("Invalid Request!");
			return new ClientHealthReportResponse<>(baseResponse, null, HttpStatus.BAD_REQUEST);
		}
	}

	private HealthIndicatorTypeBean convertToBean(HealthIndicatorType healthIndicatorType) {
		HealthIndicatorTypeBean healthIndicatorTypeBean = oneConnectDAOUtility.modelMapper.map(healthIndicatorType,
				HealthIndicatorTypeBean.class);
		return healthIndicatorTypeBean;
	}

	private ClientHealthReportBean convertToBean(ClientHealthReport clientHealthReport) {
		ClientHealthReportBean clientHealthReportBean = oneConnectDAOUtility.modelMapper.map(clientHealthReport,
				ClientHealthReportBean.class);
		return clientHealthReportBean;
	}

}
